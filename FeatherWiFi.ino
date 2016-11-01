#include <SPI.h>
#include <WiFi101.h>

char ssid[] = "WIFI_SSID";     //  your network SSID (name)
char pass[] = "WIFI_PASSWORD";  // your network password
int status = WL_IDLE_STATUS;     // the Wifi radio's status

WiFiClient client;              //act as a client to mobile api server for registration purpose
IPAddress server(172,25,89,224);    //IP address of mobile api server

WiFiServer mobileServer(80);      //act as web server which can handle requests for incoming requests from mobile application

void setup() {
  WiFi.setPins(8,7,4,2);        //configure the pins according to feather m0 wifi module
  pinMode(13,OUTPUT);           //set status of LED on wifi module as OUTPUT
  digitalWrite(13,HIGH);        //turn on LED on startup
  
  //Initialize serial and wait for port to open:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }

  // check for the presence of the shield:
  if (WiFi.status() == WL_NO_SHIELD) {
    Serial.println("WiFi shield not present");
    // don't continue:
    while (true);
  }

  // attempt to connect to Wifi network:
  while ( status != WL_CONNECTED) {
    Serial.print("Attempting to connect to WPA SSID: ");
    Serial.println(ssid);
    // Connect to WPA/WPA2 network:
    status = WiFi.begin(ssid,pass);

    // wait 10 seconds for connection:
    delay(10000);
  }

  // you're connected now, so print out the data:
  Serial.print("You're connected to the network");
  printCurrentNet();
  printWifiData();

  //connect to mobile API server and register yourself.
  if(client.connect(server,8080)){
    Serial.println("Connected to server");
    client.println("GET /reg?port=8184&uid=suyashbakshi HTTP/1.0");
    client.println();
  }

  //wait for a reply from mobile API server
  while(!client.available()){}

  //read the reply 
  while (client.available()) {
    char c = client.read();
    Serial.write(c);
  }

//if server has disconnected then stop
if(!client.connected()){
    client.stop();
  }

  //start the server mode and listen for requests from mobile clients
  mobileServer.begin();

}

void loop() {
  
  WiFiClient mobileClient = mobileServer.available();

  if(mobileClient){
    Serial.println("\n\nNew Client has Connected");

    while(mobileClient.connected()){
      if(mobileClient.available()){
        char c = mobileClient.read();
        Serial.write(c);    
    }

    //on receiving a request, turn the LED on/off 
    int val = digitalRead(13);
        if(val==0){
          digitalWrite(13,HIGH);
        }
        else{
          digitalWrite(13,LOW);
        }    
  }
  mobileClient.stop();
  Serial.println("Client Has Disconnected");
 }
}

void printWifiData() {
  // print your WiFi shield's IP address:
  IPAddress ip = WiFi.localIP();
  Serial.print("IP Address: ");
  Serial.println(ip);
}

void printCurrentNet() {
  // print the SSID of the network you're attached to:
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());
}
