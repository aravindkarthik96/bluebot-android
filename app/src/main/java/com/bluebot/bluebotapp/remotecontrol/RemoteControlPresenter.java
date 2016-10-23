package com.bluebot.bluebotapp.remotecontrol;

import android.view.View;
import android.widget.Button;

import com.bluebot.bluebotapp.R;
import com.bluebot.bluebotapp.commons.BluetoothCommons;

/**
 * Created by aravind karthik on 10/24/2016.
 */
public class RemoteControlPresenter {
    RemoteControlView remoteControlView;

    RemoteControlPresenter(RemoteControlView remoteControlView){
        this.remoteControlView = remoteControlView;
    }

    public void onClickButtons(View view) {
        Button button = (Button) view;
        String action;
        switch(button.getId()){
            case R.id.up :
                action = BluetoothCommons.ACTION_STRAIGHT;
                break;
            case R.id.down :
                action = BluetoothCommons.ACTION_BACK;
                break;
            case R.id.left :
                action = BluetoothCommons.ACTION_LEFT;
                break;
            case R.id.right :
                action = BluetoothCommons.ACTION_RIGHT;
                break;
            case R.id.rRight :
                action = BluetoothCommons.ACTION_RRIGHT;
                break;
            case R.id.rLeft :
                action = BluetoothCommons.ACTION_RLEFT;
                break;
            case R.id.stop :
                action = BluetoothCommons.ACTION_STAND;
                break;
            default:
                action = "";
                break;
        }
        remoteControlView.setBluetoothAction(action);
    }
}
