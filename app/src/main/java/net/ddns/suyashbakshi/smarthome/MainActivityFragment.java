package net.ddns.suyashbakshi.smarthome;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public Button loginBtn;
    public EditText usernameEt;
    public EditText passwordEt;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        loginBtn = (Button)rootView.findViewById(R.id.loginBtn);
        usernameEt = (EditText)rootView.findViewById(R.id.usernameEt);
        passwordEt = (EditText)rootView.findViewById(R.id.passwordEt);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uid = usernameEt.getText().toString() +""+ passwordEt.getText().toString();
                Intent intent = new Intent(getContext(),DeviceActivity.class).putExtra(Intent.EXTRA_TEXT,uid);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
