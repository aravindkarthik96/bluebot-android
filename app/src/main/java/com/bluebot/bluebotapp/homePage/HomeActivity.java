package com.bluebot.bluebotapp.homePage;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bluebot.bluebotapp.Utils;
import com.bluebot.bluebotapp.bluetooth.BluetoothCommons;
import com.bluebot.bluebotapp.R;
import com.bluebot.bluebotapp.bluetooth.BluetoothConnectionCallback;
import com.bluebot.bluebotapp.bluetooth.BluetoothConnectionTask;
import com.bluebot.bluebotapp.pickBluetooth.PickBluetoothActivity;

import java.io.IOException;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity implements HomeActivityView , BluetoothConnectionCallback {

    String address = null;
    private ProgressDialog progress;
    HomeActivityPresenter presenter;
    Button disconnectButton;
    BluetoothSocket bluetoothSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        presenter = new HomeActivityPresenter(this);
        initView();
        Intent pickBluetoothIntent = getIntent();
        address = pickBluetoothIntent.getStringExtra(PickBluetoothActivity.EXTRA_ADDRESS);
        BluetoothConnectionTask bluetoothConnectionTask = new BluetoothConnectionTask(this,address);
        bluetoothConnectionTask.execute();

    }

    private void initView(){
        progress = ProgressDialog.show(HomeActivity.this, "Connecting to bluebot", "hold on");
        disconnectButton = (Button) findViewById(R.id.disconnectButton);
        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BluetoothCommons.disconnectBot(bluetoothSocket , getApplicationContext());
                finish();
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
    public Context getContextFromActivity() {
        return getApplicationContext();
    }

    @Override
    public void hideProgress() {
        progress.dismiss();
    }

    @Override
    public void onBluetoothConnectionFailed() {
        hideProgress();
        Utils.showSnackBar("something went wrong",getApplicationContext());
    }

    @Override
    public void onBluetoothConnectionSuccess(BluetoothSocket bluetoothSocket) {
        hideProgress();
        Utils.showSnackBar("connection established",getApplicationContext());
    }

    @Override
    public void onPreExecute() {
        progress.show();
    }
}
