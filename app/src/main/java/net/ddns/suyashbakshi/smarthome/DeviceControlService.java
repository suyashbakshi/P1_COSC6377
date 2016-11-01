package net.ddns.suyashbakshi.smarthome;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by suyas on 10/30/2016.
 */

public class DeviceControlService extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... params) {
        HttpURLConnection urlConnection = null;

        try {
            final String BASE_URL = "http://172.25.42.138:80";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(params[0])
                    .build();
            URL url = new URL(builtUri.toString());
            Log.v("BUILT_URI_DEVICE : ", builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "text");
            urlConnection.connect();

            DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
            //out.write();

            out.close();

            urlConnection.disconnect();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
