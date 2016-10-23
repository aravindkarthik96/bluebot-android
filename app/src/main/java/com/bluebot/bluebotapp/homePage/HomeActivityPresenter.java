package com.bluebot.bluebotapp.homePage;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by aravind karthik on 10/23/2016.
 */
public class HomeActivityPresenter {
    HomeActivityView homeActivityView;
    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;
    Boolean isBtConnected = false;

    public HomeActivityPresenter(HomeActivityView homeActivityView) {
        this.homeActivityView=homeActivityView;
    }

    public void connectBluetooth(String address) {
        homeActivityView.showProgress();

        try {
            if (bluetoothSocket == null || !isBtConnected)
            {

                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);//connects to the device's address and checks if it's available
                bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(address));//create a RFCOMM (SPP) connection
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                bluetoothSocket.connect();//start connection

            }
            else {
                homeActivityView.makeToast("bluetoothAlreadyConnected");
            }
            isBtConnected = true;
        }
        catch (Exception e){
            e.printStackTrace();
            isBtConnected = false;
        }

        homeActivityView.hideProgress();
    }
}
