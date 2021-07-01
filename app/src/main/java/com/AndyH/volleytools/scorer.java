package com.AndyH.volleytools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.OrientationEventListener;

public class scorer extends AppCompatActivity {
    CountDownTimer orientation_delay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorer);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        OrientationEventListener orientationEventListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                Log.v("orientation", "Orientation changed to " + orientation);
                if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    return;
                }

                int degrees = -1;
                if (orientation < 45 || orientation > 315) {
                    Log.i("orientation", "Portrait "+degrees);
                } else if (orientation < 135) {
                    degrees = 90;
                    scorer.super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    Log.i("orientation", "Landscape "+degrees);    // This can be reverse landscape
                } else if (orientation < 225) {
                    degrees = 180;
                    Log.i("orientation", "Reverse Portrait "+degrees);
                } else {
                    degrees = 270;
                    Log.i("orientation", "Reverse Landscape "+degrees); // This can be landscape
                    scorer.super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        };

        if (orientationEventListener.canDetectOrientation() == true) {
            Log.v("orientation", "Can detect orientation");
            orientationEventListener.enable();
        } else {
            Log.v("orientation", "Cannot detect orientation");
            orientationEventListener.disable();
        }

    }

}