package net.ddns.suyashbakshi.smarthome;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DeviceActivityFragment extends Fragment {

    private ListViewAdapter listViewAdapter;
    public static ProgressBar pb;

    public DeviceActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_device, container, false);

        ListView deviceListView = (ListView)rootView.findViewById(R.id.deviceListView);
        pb = (ProgressBar)rootView.findViewById(R.id.progress);

        listViewAdapter = new ListViewAdapter(getContext(),new ArrayList<String>());
        deviceListView.setAdapter(listViewAdapter);

        Intent intent = getActivity().getIntent();
        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            String uid = intent.getStringExtra(Intent.EXTRA_TEXT);
            DataFetchService dataFetchService = new DataFetchService(getContext(),listViewAdapter);
            dataFetchService.execute(uid);
        }

        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeviceControlService deviceControlService = new DeviceControlService();
                deviceControlService.execute("1");
            }
        });


        return rootView;
    }
}
