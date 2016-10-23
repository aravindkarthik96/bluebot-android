package com.bluebot.bluebotapp.homePage;

import android.content.Context;

/**
 * Created by aravind karthik on 10/23/2016.
 */
public interface HomeActivityView {
    void showProgress();

    void hideProgress();

    void makeToast(String bluetoothAlreadyConnected);

    Context getContextFromActivity();
}
