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
    private float direction;

    public DirectionActivityPresenter(DirectionActivityView directionActivityView) {
        this.directionActivityView = directionActivityView;
    }

    public void sensorChanged(SensorEvent sensorEvent) {
        float originalDirection = Math.round(sensorEvent.values[0]);
        direction = calculateUsableDirection(originalDirection);
        directionActivityView.setDegrees(direction);
        if (shouldGoBackToDirection) {
            lockedDirection = calculateUsableDirection(lockedDirection);
            if (direction == lockedDirection) {
                directionActivityView.setActionToBot(BluetoothCommons.ACTION_STAND);
                shouldGoBackToDirection = false;
            }
        }
    }

    public void goBackToDirection(float lockedDirection) {
        this.lockedDirection = lockedDirection-50;
        shouldGoBackToDirection = true;
        calculateTuringDistance(lockedDirection,direction);
        if (calculateTuringDistance(lockedDirection,direction)) {
            directionActivityView.setActionToBot(BluetoothCommons.ACTION_CRLEFT);
        } else {
            directionActivityView.setActionToBot(BluetoothCommons.ACTION_CRRIGHT);
        }
    }

    private boolean calculateTuringDistance(float lockedDirection, float currentDirection) {
        lockedDirection = Math.abs(360-lockedDirection);
        currentDirection = Math.abs(360-currentDirection);
        if (lockedDirection - currentDirection > 0){
            return true;
        }
        return false;
    }

    public float calculateUsableDirection(float directionReceived) {
        return directionReceived - directionReceived % 10;
    }
}
