package com.bluebot.bluebotapp.pickBluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by aravind karthik on 10/23/2016.
 */
public class PickBluetoothPresenter {

    private PickBluetoothView pickBluetoothView;
    private BluetoothAdapter bluetoothAdapter = null;

    public PickBluetoothPresenter(PickBluetoothView pickBluetoothView) {
        this.pickBluetoothView=pickBluetoothView;
    }

    public void listBluetoothDevices() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            pickBluetoothView.makeToast("Bluetooth Device Not Available");
            pickBluetoothView.closeApp();
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                pickBluetoothView.askToEnableBluetooth();
            }
            else {
                pickBluetoothView.setBluetoothList(getBluetoothList());
            }
        }
    }

    public ArrayList getBluetoothList(){
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        ArrayList deviceList = new ArrayList();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice bt : pairedDevices) {
                deviceList.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
            }
            return deviceList;
        }
        pickBluetoothView.makeToast("No Paired Bluetooth Devices Found.");
        return null;
    }
}
