package com.bluebot.bluebotapp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by aravind karthik on 10/24/2016.
 */
public class Utils {
    public static void showSnackBar(String message, Context context) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
