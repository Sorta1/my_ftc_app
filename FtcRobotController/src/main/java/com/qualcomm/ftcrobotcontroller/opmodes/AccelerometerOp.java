package com.qualcomm.ftcrobotcontroller.opmodes;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An Op Mode that returns the raw Accelerometer sensor values as telemetry
 */
public class AccelerometerOp extends OpMode implements SensorEventListener {
    private String startDate;
    private SensorManager mSensorManager;
    Sensor accelerometer;

    private float[] acceleration = {0.0f,0.0f,0.0f};    // SI units (m/s^2)
    //values[0]: Acceleration minus Gx on the x-axis
    //values[1]: Acceleration minus Gy on the y-axis
    //values[2]: Acceleration minus Gz on the z-axis

    private float[] mAccelerometer;       // latest sensor values
    // see http://developer.android.com/reference/android/hardware/SensorEvent.html#values
    // for example that ends up compensating for gravity.
    // Probably better to use TYPE_LINEAR_ACCELERATION instead, but maybe the raw values
    // could be useful.

    /*
    * Constructor
    */
    public AccelerometerOp() {

    }

    /*
    * Code to run when the op mode is first enabled goes here
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
    */
    @Override
    public void start() {
        startDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

        mSensorManager = (SensorManager) hardwareMap.appContext.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // delay value is SENSOR_DELAY_UI which is ok for telemetry, maybe not for actual robot use
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    /*
    * This method will be called repeatedly in a loop
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
    */
    @Override
    public void loop() {
        telemetry.addData("1 Start", "AccelerometerOp started at " + startDate);
        telemetry.addData("2 units", "values in SI units (m/s^2)");
        telemetry.addData("3 x-axis", "x-axis = " + acceleration[0]);
        telemetry.addData("4 y-axis", "y-axis = " + acceleration[1]);
        telemetry.addData("5 z-axis", "z-axis = " + acceleration[2]);
    }

    /*
    * Code to run when the op mode is first disabled goes here
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
    */
    @Override
    public void stop() {
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not sure if needed, placeholder just in case
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mAccelerometer = event.values;
            if (mAccelerometer != null) {
                acceleration[0] = mAccelerometer[0]; // Acceleration minus Gx on the x-axis
                acceleration[1] = mAccelerometer[1]; // Acceleration minus Gy on the y-axis
                acceleration[2] = mAccelerometer[2]; // Acceleration minus Gz on the z-axis
            }
        }
    }
}
