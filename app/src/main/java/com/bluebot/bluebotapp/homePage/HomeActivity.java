package com.bluebot.bluebotapp.homePage;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bluebot.bluebotapp.bluetooth.BluetoothCommons;
import com.bluebot.bluebotapp.R;
import com.bluebot.bluebotapp.pickBluetooth.PickBluetoothActivity;

import java.io.IOException;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity implements HomeActivityView {

    String address = null;
    private ProgressDialog progress;
    HomeActivityPresenter presenter;
    BluetoothAdapter bluetoothAdapter = null;
    BluetoothSocket bluetoothSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    Button disconnectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        presenter = new HomeActivityPresenter(this);
        disconnectButton = (Button) findViewById(R.id.disconnectButton);
        Intent pickBluetoothIntent = getIntent();
        address = pickBluetoothIntent.getStringExtra(PickBluetoothActivity.EXTRA_ADDRESS);
        progress = ProgressDialog.show(HomeActivity.this, "Connecting to bluebot", "hold on");
        ConnectBluetooth connectBluetooth=new ConnectBluetooth();
        connectBluetooth.execute();
        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BluetoothCommons.disconnectBot(bluetoothSocket);
            }
        });
    }

    @Override
    public void showProgress() {
        progress.show();
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideProgress() {
        progress.dismiss();
    }

    private class ConnectBluetooth extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            showProgress();  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (bluetoothSocket == null || !isBtConnected)
                {
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    bluetoothSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                makeToast("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                makeToast("Connected");
                isBtConnected = true;
            }
            hideProgress();
        }
    }
}
