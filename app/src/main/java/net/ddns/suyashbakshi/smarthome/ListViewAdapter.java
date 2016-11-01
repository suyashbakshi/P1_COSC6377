package net.ddns.suyashbakshi.smarthome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by suyas on 10/27/2016.
 */

public class ListViewAdapter extends ArrayAdapter {

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String deviceString = (String) getItem(position);

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.device_list_item,null);
        }

        TextView deviceTextView = (TextView)convertView.findViewById(R.id.deviceListTextView);

        deviceTextView.setText(deviceString);


        return convertView;
    }

    public ListViewAdapter(Context context, List<String> deviceList){
        super(context,0,deviceList);
    }
}
