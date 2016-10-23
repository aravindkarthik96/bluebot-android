package com.bluebot.bluebotapp.bluetooth;

import android.bluetooth.BluetoothSocket;

/**
 * Created by aravind karthik on 10/24/2016.
 */
public interface BluetoothConnectionCallback {
    void onBluetoothConnectionFailed();

    void onBluetoothConnectionSuccess(BluetoothSocket bluetoothSocket);

    void onPreExecute();
}
