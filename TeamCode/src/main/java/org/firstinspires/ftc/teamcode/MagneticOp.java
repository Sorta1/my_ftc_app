package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An op mode that returns the raw magnetic field sensor values as telemetry
 */
@Autonomous(name = "MagneticOp", group = "Demo")
public class MagneticOp extends OpMode implements SensorEventListener {
    private String startDate;
    private SensorManager mSensorManager;
    private Sensor magnetometer;

    private float[] magneticValues = {0.0f, 0.0f, 0.0f};  // micro-Tesla (uT) units
    private float[] mGeomagnetic;   // latest sensor values, x y and z axis

    /*
    * Constructor
    */
    public MagneticOp() {

    }

    /*
    * Code to run when the op mode is first enabled goes here
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#init()
    */
    @Override
    public void init() {
        mSensorManager = (SensorManager) hardwareMap.appContext.getSystemService(Context.SENSOR_SERVICE);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        magneticValues[0] = 0.0f;
        magneticValues[1] = 0.0f;
        magneticValues[2] = 0.0f;
    }

    /*
* Code to run when the op mode is first enabled goes here
* @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
*/
    @Override
    public void start() {
        startDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

        // delay value is SENSOR_DELAY_UI which is ok for telemetry, maybe not for actual robot use
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    /*
    * This method will be called repeatedly in a loop
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
    */
    @Override
    public void loop() {
//        telemetry.addData("1 Start", "MagneticOp started at " + startDate);
//        telemetry.addData("2 units", "values in micro-Tesla (uT) units");
        if (mGeomagnetic != null) {
            telemetry.addData("magnetic x", magneticValues[0]);
            telemetry.addData("magnetic y", magneticValues[1]);
            telemetry.addData("magnetic z", magneticValues[2]);
        }
        else {
            telemetry.addData("note", "no default magnetic sensor on phone");
        }
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
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mGeomagnetic = event.values;
            if (mGeomagnetic != null) {
                magneticValues[0] = mGeomagnetic[0]; // x value
                magneticValues[1] = mGeomagnetic[1]; // y value
                magneticValues[2] = mGeomagnetic[2]; // z value
            }
        }

    }
}
