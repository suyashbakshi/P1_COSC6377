package net.ddns.suyashbakshi.smarthome;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.internal.bind.DateTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by suyas on 10/26/2016.
 */

public class DataFetchService extends AsyncTask<String, Void, String[]> {

    private ListViewAdapter mListViewAdapter;
    private Context mContext;

    public DataFetchService(Context context, ListViewAdapter listViewAdapter){
        mListViewAdapter = listViewAdapter;
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        DeviceActivityFragment.pb.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String[] strings) {

        if(strings != null) {
            mListViewAdapter.clear();
            for (String devices : strings) {
                Log.v("Device_Details", devices);
                mListViewAdapter.add(devices);
            }
        }
        mListViewAdapter.notifyDataSetChanged();
        DeviceActivityFragment.pb.setVisibility(View.GONE);
        super.onPostExecute(strings);
    }

    @Override
    protected String[] doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JsonString = null;
        //String apiKey = "suyash";
        String[] devices = null;

        try {
            final String BASE_URL = "http://172.25.89.224:8080/info?";
            final String UID_PARAM = "uid";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(UID_PARAM, params[0])
                    .build();
            URL url = new URL(builtUri.toString());
            Log.v("BUILT URI : ", builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();

            if (inputStream == null)
                JsonString = null;
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }

            if (stringBuffer.length() == 0) {
                return null;
            }

            JsonString = stringBuffer.toString();

            JSONArray root = new JSONArray(JsonString);

            devices = new String[root.length()];

            for (int i = 0; i < root.length(); i++) {

                JSONObject info = root.getJSONObject(i);

                String ip = info.getString("ip");
                String port = info.getString("port");
                String uid = info.getString("uid");

                devices[i] = ip + "/" + port + "/" + uid;
                Log.v("XYZ",devices[i]);
            }

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        urlConnection.disconnect();
        return devices;
    }
}
