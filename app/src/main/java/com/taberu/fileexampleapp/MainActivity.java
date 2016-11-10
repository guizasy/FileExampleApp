package com.taberu.fileexampleapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mPressure;
    private TextView txView;
    private float old_pressure;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyMMddhhmm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        old_pressure = 0.0f;

        txView = (TextView) findViewById(R.id.pressureText);
        txView.setText("Pressure:  0.0 hPa");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float pressure = event.values[0];

        if (old_pressure != pressure) {
            txView.setText("Pressure: " + Float.toString(pressure) + " hPa");
            old_pressure = pressure;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void saveFile(View view) {
        String filename = "Pressure.txt";
        String timestamp = sdf.format(new Date());
        String strRadio = timestamp + "; " + Float.toString(old_pressure);

        FileServices fs = new FileServices();
        fs.fileAppendExternalStorage(this, filename, strRadio);
    }
}
