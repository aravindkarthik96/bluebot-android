package com.bluebot.bluebotapp;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.bluebot.bluebotapp.bluetooth.BluetoothCommons;
import com.bluebot.bluebotapp.bluetooth.BluetoothConnectionCallback;
import com.bluebot.bluebotapp.bluetooth.BluetoothConnectionTask;
import com.bluebot.bluebotapp.homePage.HomeActivity;

public class RemoteControl extends AppCompatActivity implements BluetoothConnectionCallback , RemoteControlView , View.OnClickListener{

    RemoteControlPresenter presenter;
    BluetoothSocket bluetoothSocket;
    String address;


    private ProgressDialog progress;
    private Button up,left,right,rright,rleft,down,stop,disconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);
        initViews();
        presenter = new RemoteControlPresenter(this);
        address=getIntent().getStringExtra(BluetoothCommons.EXTRA_ADDRESS);
        BluetoothConnectionTask bluetoothConnectionTask = new BluetoothConnectionTask(this,address);
        bluetoothConnectionTask.execute();
    }

    private void initViews() {
        progress = ProgressDialog.show(RemoteControl.this, "Connecting to bluebot", "hold on");
        up = (Button) findViewById(R.id.up);
        down = (Button) findViewById(R.id.down);
        left = (Button) findViewById(R.id.left);
        right = (Button) findViewById(R.id.right);
        rright = (Button) findViewById(R.id.rRight);
        rleft = (Button) findViewById(R.id.rLeft);
        stop = (Button) findViewById(R.id.stop);
        disconnect = (Button) findViewById(R.id.disconnect);


        up.setOnClickListener(this);
        down.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        rright.setOnClickListener(this);
        rleft.setOnClickListener(this);
        stop.setOnClickListener(this);
        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BluetoothCommons.disconnectBot(bluetoothSocket,getApplicationContext());
            }
        });
    }

    @Override
    public void onBluetoothConnectionFailed() {
        progress.dismiss();
        Utils.showSnackBar("something went wrong",getApplicationContext());
    }

    @Override
    public void onBluetoothConnectionSuccess(BluetoothSocket bluetoothSocket) {
        progress.dismiss();
        Utils.showSnackBar("connection established", getApplicationContext());
        this.bluetoothSocket=bluetoothSocket;
    }

    @Override
    public void onPreExecute() {
        progress.show();
    }

    @Override
    public void onClick(View view) {
        presenter.onClickButtons(view);
    }

    @Override
    public void setBluetoothAction(String action) {
        BluetoothCommons.moveBot(action,bluetoothSocket,getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        BluetoothCommons.disconnectBot(bluetoothSocket,getApplicationContext());
    }
}
