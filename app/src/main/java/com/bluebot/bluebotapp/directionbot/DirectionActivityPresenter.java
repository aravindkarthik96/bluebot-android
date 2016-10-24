package com.bluebot.bluebotapp.directionbot;

import android.hardware.SensorEvent;

import com.bluebot.bluebotapp.commons.BluetoothCommons;

/**
 * Created by aravind karthik on 10/24/2016.
 */
public class DirectionActivityPresenter {
    boolean shouldGoBackToDirection = false;
    float lockedDirection;
    DirectionActivityView directionActivityView;

    public DirectionActivityPresenter(DirectionActivityView directionActivityView) {
        this.directionActivityView=directionActivityView;
    }

    public void sensorChanged(SensorEvent sensorEvent) {
        float originalDirection = Math.round(sensorEvent.values[0]);
        float direction = originalDirection - originalDirection % 10;
        directionActivityView.setDegrees(direction);
        if (shouldGoBackToDirection){
            lockedDirection = lockedDirection - lockedDirection % 10;
            if (direction == lockedDirection){
                directionActivityView.setActionToBot(BluetoothCommons.ACTION_STAND);
                shouldGoBackToDirection = false;
            }
            else {
                if (direction + lockedDirection < 180) {
                    directionActivityView.setActionToBot(BluetoothCommons.ACTION_RRIGHT);
                }
                else {
                    directionActivityView.setActionToBot(BluetoothCommons.ACTION_RLEFT);
                }
            }
        }
    }

    public void goBackToDirection(float lockedDirection) {
        this.lockedDirection = lockedDirection;
        shouldGoBackToDirection = true;
        directionActivityView.setActionToBot(BluetoothCommons.ACTION_RLEFT);
    }
}
