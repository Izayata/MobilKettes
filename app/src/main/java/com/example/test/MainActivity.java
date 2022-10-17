package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout cont;
    private Button b;
    public static Random r = new Random();
    private static final float SHAKE_THRESHOLD = 3.25f; // m/(s^2)
    private static final int MIN_TIME_BETWEEN_SHAKES_MILLISECS = 1000;
    private long mLastShakeTime;
    private SensorManager mSensorMgr;
    private static final String APP_NAME = "Teszt";

    private SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                long curTime = System.currentTimeMillis();
                if ((curTime - mLastShakeTime) > MIN_TIME_BETWEEN_SHAKES_MILLISECS) {
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];

                    if(x > y && Math.abs(x) > 2.5) {
                        cont.setBackgroundColor(Color.parseColor(generateWarmColour()));
                    } else if(y > x && Math.abs(y) > 2.5) {
                        cont.setBackgroundColor(Color.parseColor(generateColdColour()));
                    }

                    double acceleration = Math.sqrt(Math.pow(x, 2) +
                            Math.pow(y, 2) +
                            Math.pow(z, 2)) - SensorManager.GRAVITY_EARTH;
                    Log.d(APP_NAME, "Acceleration is " + acceleration + "m/s^2");
                    Log.d(APP_NAME, "X: " + x + "Y: " + y + "Z: " + z);
                    if (acceleration > SHAKE_THRESHOLD) {
                        mLastShakeTime = curTime;
                        Log.d(APP_NAME, "Shake, Rattle, and Roll");
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cont = findViewById(R.id.layout);

        // Get a sensor manager to listen for shakes
        mSensorMgr = (SensorManager)getSystemService(SENSOR_SERVICE);
        // Listen for shakes
        Sensor accelerometer = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            mSensorMgr.registerListener(sel, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        }
    }

    //komment commithoz
    public void generateWarmColourOnClick(View view) {
        cont.setBackgroundColor(Color.parseColor(generateWarmColour()));
    }

    public void generateColdColourOnClick(View view) {
        cont.setBackgroundColor(Color.parseColor(generateColdColour()));
    }

    private static String generateWarmColour() {
        int red = r.nextInt(127) + 128;
        int green = r.nextInt(128);
        int blue = r.nextInt(red);

        return String.format("#%02x%02x%02x", red, green, blue);
    }

    private static String generateColdColour() {
        int blue = r.nextInt(127) + 128;
        int green = r.nextInt(255);
        int red = r.nextInt(blue);
        return String.format("#%02x%02x%02x", red, green, blue);
    }
}