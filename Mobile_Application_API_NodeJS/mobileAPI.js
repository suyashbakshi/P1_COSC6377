var mysql = require('mysql');
var express = require('express');
var app = express();

var con = mysql.createConnection({
	host: "localhost",
	user: "root",
	password: "suyash",
	database: "test"
});

con.connect(function(err){
	if(err){
		console.log("Error connecting to database");
		return;
	}
	console.log("Connection to database successful.\nWaiting for connection...\n");
});

//to handle device registration messages of the form --- www.xyz.com/reg?port=8080&uid=abcdefghij
//"port" is the port number on which the device will be listening
//"uid" is the unique ID for a group of devices belonging to a single user
app.get('/reg',function(req,res){

	var ip = req.header('x-forwarded-for') || req.connection.remoteAddress;
	var port = req.query.port;
	var uid = req.query.uid;
	var colonIdx = ip.lastIndexOf(':');
    var ipf = ip.substring(colonIdx+1,ip.length);
	console.log("\nRegistration request from IP : " + ipf);

	con.query("insert into userinfo values('" + ipf + "','" + port + "','" + uid +"');", function(err,rows){
		if(err){
			console.error(err.stack);
			return;
		}
	});
	// res.send("Registration Complete : " + ipf + " " + port + " " + uid);
	res.send('1');
});

//to handle requests from mobile application querying the information about their home devices
//the function parses the UID for user and returns the list of home devices and their metadata
app.get('/info',function(req,res){
	var uid = req.query.uid;

	con.query("select * from userinfo where uid='"+uid+"';", function(err,rows){
		if(err){
			console.log("Error retrieving data\n");
			return;
		}
		console.log("Response for uid " + uid + " sent.")
		res.send(rows);
	});

});

var server = app.listen(8080, function(){
	console.log("Server started...")
});