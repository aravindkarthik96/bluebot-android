/*
package com.bluebot.bluebotapp.base;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bluebot.bluebotapp.bluetooth.BluetoothConnectionTask;
import com.bluebot.bluebotapp.commons.BluetoothCommons;
import com.bluebot.bluebotapp.directionbot.DirectionActivity;

*/
/**
 * Created by aravind karthik on 10/26/2016.
 *//*

public class BluetoothBaseActivity extends AppCompatActivity {
    String address;
    private BluetoothSocket bluetoothSocket;
    private ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initView();
        address=getIntent().getStringExtra(BluetoothCommons.EXTRA_ADDRESS);
        BluetoothConnectionTask bluetoothConnectionTask = new BluetoothConnectionTask(this,address);
        bluetoothConnectionTask.execute();
    }

    private void initView() {

        progress = ProgressDialog.show(BluetoothBaseActivity.this, "Connecting to bluebot", "hold on");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BluetoothCommons.disconnectBot(bluetoothSocket, getApplicationContext());
    }
}
*/
