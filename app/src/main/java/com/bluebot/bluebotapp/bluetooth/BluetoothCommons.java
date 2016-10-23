package com.bluebot.bluebotapp.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.content.Context;

import java.io.IOException;

/**
 * Created by aravind karthik on 10/23/2016.
 */
public class BluetoothCommons {

    public static final String ACTION_STRAIGHT="w";
    public static final String ACTION_RIGHT="d";
    public static final String ACTION_LEFT="a";
    public static final String ACTION_RRIGHT="e";
    public static final String ACTION_RLEFT="q";
    public static final String ACTION_BACK="s";
    public static final String ACTION_STAND="x";


    public static void moveBot(String action, BluetoothSocket socket ,Context context) {
        if (socket!=null)
        {
            try
            {
                socket.getOutputStream().write(action.getBytes());
            }
            catch (IOException e)
            {

            }
        }
    }

    public static void disconnectBot(BluetoothSocket socket){
        if (socket!=null) //If the btSocket is busy
        {
            try
            {
                socket.close(); //close connection
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
