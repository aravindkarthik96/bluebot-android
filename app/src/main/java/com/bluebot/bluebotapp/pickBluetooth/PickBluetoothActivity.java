package com.bluebot.bluebotapp.pickBluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluebot.bluebotapp.homePage.HomeActivity;
import com.bluebot.bluebotapp.R;

import java.util.ArrayList;

public class PickBluetoothActivity extends AppCompatActivity implements PickBluetoothView {

    public static final String EXTRA_ADDRESS = "EXTRA_ADDRESS" ;
    private PickBluetoothPresenter presenter = new PickBluetoothPresenter(this);
    public Button connectButton;
    public ListView bluetoothDeviceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_bluetooth);
        initViews();
    }

    private void initViews() {
        connectButton = (Button) findViewById(R.id.connectButton);
        bluetoothDeviceList = (ListView) findViewById(R.id.bluetoothList);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.listBluetoothDevices();
            }
        });
        presenter.listBluetoothDevices();
    }

    @Override
    public void closeApp() {
        finish();
    }

    @Override
    public void askToEnableBluetooth() {
        Intent turnOnBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(turnOnBluetoothIntent,1);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setBluetoothList(ArrayList bluetoothList) {
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, bluetoothList);
        bluetoothDeviceList.setAdapter(adapter);
        bluetoothDeviceList.setOnItemClickListener(bluetoothDeviceClickListener);
    }

    private AdapterView.OnItemClickListener bluetoothDeviceClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick (AdapterView adapterView, View view, int arg2, long arg3)
        {
            String info = ((TextView) view).getText().toString();
            String address = info.substring(info.length() - 17);
            Intent i = new Intent(PickBluetoothActivity.this, HomeActivity.class);
            i.putExtra(EXTRA_ADDRESS, address);
            startActivity(i);
        }
    };
}
