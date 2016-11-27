package com.bluebot.bluebotapp.directionbot;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bluebot.bluebotapp.R;
import com.bluebot.bluebotapp.bluetooth.BluetoothConnectionCallback;
import com.bluebot.bluebotapp.bluetooth.BluetoothConnectionTask;
import com.bluebot.bluebotapp.commons.BluetoothCommons;
import com.bluebot.bluebotapp.commons.Utils;

public class DirectionActivity extends AppCompatActivity implements DirectionActivityView , BluetoothConnectionCallback, SensorEventListener{

    DirectionActivityPresenter presenter;
    String address;
    Button lockButton, disconnect , gobackButton;
    TextView degreeTextView;
    SensorManager sensorManager;
    private ProgressDialog progress;
    private Sensor compass;
    private BluetoothSocket bluetoothSocket;
    private float lastKnownDirection;
    private float lockedDirection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        initView();
        presenter = new DirectionActivityPresenter(this);
        address=getIntent().getStringExtra(BluetoothCommons.EXTRA_ADDRESS);
        BluetoothConnectionTask bluetoothConnectionTask = new BluetoothConnectionTask(this,address);
        bluetoothConnectionTask.execute();
    }

    private void initView() {
        lockButton = (Button) findViewById(R.id.lockButton);
        gobackButton = (Button) findViewById(R.id.goback);
        disconnect = (Button) findViewById(R.id.disconnect);
        degreeTextView = (TextView) findViewById(R.id.degreeTextView);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        compass = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        if(compass != null){
            sensorManager.registerListener(this, compass, SensorManager.SENSOR_DELAY_GAME,1);
        }

        lockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockedDirection = lastKnownDirection;
                BluetoothCommons.moveBot(BluetoothCommons.ACTION_STAND,bluetoothSocket,getApplicationContext());
            }
        });

        gobackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.goBackToDirection(lockedDirection);
            }
        });
    }

    @Override
    public void onBluetoothConnectionFailed() {
        progress.dismiss();
        Utils.showSnackBar("something went wrong",getApplicationContext());
        finish();
    }

    @Override
    public void onBluetoothConnectionSuccess(BluetoothSocket bluetoothSocket) {
        progress.dismiss();
        Utils.showSnackBar("connection established", getApplicationContext());
        this.bluetoothSocket=bluetoothSocket;
    }

    @Override
    public void onPreExecute() {
        progress.show();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        presenter.sensorChanged(sensorEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void setDegrees(float degree) {
        lastKnownDirection = degree;
        degreeTextView.setText(""+degree+"°");
    }

    @Override
    public void setActionToBot(String action) {
        BluetoothCommons.moveBot(action, bluetoothSocket, getApplicationContext());
    }
}
