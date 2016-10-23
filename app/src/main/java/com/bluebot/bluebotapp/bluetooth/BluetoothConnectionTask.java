package com.bluebot.bluebotapp.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by aravind karthik on 10/24/2016.
 */
public class BluetoothConnectionTask extends AsyncTask<Void, Void, Void>{
    private final String address;
    private boolean ConnectSuccess = true; //if it's here, it's almost connected
    private BluetoothConnectionCallback bluetoothConnectionCallback;

    BluetoothAdapter bluetoothAdapter = null;
    BluetoothSocket bluetoothSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public BluetoothConnectionTask(BluetoothConnectionCallback connectionCallback, String address){
        this.bluetoothConnectionCallback=connectionCallback;
        this.address=address;
    }

    @Override
    protected void onPreExecute()
    {
        bluetoothConnectionCallback.onPreExecute();
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
            bluetoothConnectionCallback.onBluetoothConnectionFailed();
        }
        else
        {
            isBtConnected = true;
            bluetoothConnectionCallback.onBluetoothConnectionSuccess(bluetoothSocket);
        }
    }
}
