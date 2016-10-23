package com.bluebot.bluebotapp.pickBluetooth;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by aravind karthik on 10/23/2016.
 */
public interface PickBluetoothView {
    void closeApp();

    void askToEnableBluetooth();

    Context getContext();

    void makeToast(String s);

    void setBluetoothList(ArrayList bluetoothList);
}
