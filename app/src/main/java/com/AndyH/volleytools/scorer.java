package com.AndyH.volleytools;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;

public class scorer extends AppCompatActivity {
    Boolean is_game_on;
    Game current_game;
    Button goodpeople_score_button,badpeople_score_button,goodpeople_set_button,badpeople_set_button,button_leave,button_settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_scorer);
        Log.d("bundletag", "onCreate: "+savedInstanceState);
        // set initial screen orientation to landscape
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.setContentView(R.layout.activity_scorer);

        // if saveInstanceState has the key "is_game_on", then it is always true
        if(savedInstanceState != null){
            if(savedInstanceState.getBoolean("is_game_on")==false){
                //should never end up here
                throw new RuntimeException("key false state: is_game_on");
            }else{
                //load unfinished game
                is_game_on = savedInstanceState.getBoolean("is_game_on");
                current_game= savedInstanceState.getParcelable("ongoing_game");

            }
        }else{

            //create new game with default settings
            this.is_game_on = true;
            current_game = new Game(25,2,14,
                    13,0,0,null, null);
        }


        //only allows landscape and reverse landscape
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
                setContentView(R.layout.activity_scorer);
            }
        };

        if (orientationEventListener.canDetectOrientation() == true) {
            Log.v("orientation", "Can detect orientation");
            orientationEventListener.enable();
        } else {
            Log.v("orientation", "Cannot detect orientation");
            orientationEventListener.disable();
        }


//        goodpeople_score_button = (Button) this.findViewById(R.id.left_button_score);
//        goodpeople_score_button.setText(current_game.getGoodpeople_points());
//        goodpeople_score_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                current_game.goodpeople_gain_point();
//                goodpeople_score_button.setText(current_game.getGoodpeople_points());
//            }
//        });
//
//
//        badpeople_score_button = (Button) this.findViewById(R.id.right_button_score);
//        badpeople_score_button.setText(current_game.getBadpeople_points());
//        badpeople_score_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                current_game.badpeople_gain_point();
//                badpeople_score_button.setText(current_game.getBadpeople_points());
//            }
//        });
//
//        goodpeople_set_button = (Button) this.findViewById(R.id.right_button_set);
//        goodpeople_set_button.setText(current_game.getGoodpeople_sets());
//        goodpeople_set_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                current_game.goodpeople_gain_set();
//                goodpeople_set_button.setText(current_game.getGoodpeople_sets());
//            }
//        });
//
//        badpeople_set_button = (Button) this.findViewById(R.id.left_button_set);
//        badpeople_set_button.setText(current_game.getBadpeople_sets());
//        badpeople_set_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                current_game.badpeople_gain_set();
//                badpeople_set_button.setText(current_game.getBadpeople_sets());
//            }
//        });
//
//        button_leave = (Button)this.findViewById(R.id.left_button_leave);
//        button_leave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), MainActivity.class);
//                startActivity(intent);
//                savedInstanceState.putParcelable("ongoing_game",current_game);
//
//            }
//        });
//        button_settings =(Button) this.findViewById(R.id.right_button_Settings);




    }





    //    public Game resort_ongoing_game(Bundle bundle){
//        Game game = new Game();
//        if(bundle.containsKey("is_game_on")){
//
//        }else{
//            //should never get here
//            throw new RuntimeException("bundle key not found");
//        }
//
//        return game;
//    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        outState.putParcelable("ongoing_game",current_game);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        current_game = savedInstanceState.getParcelable("ongoing_game");
        super.onRestoreInstanceState(savedInstanceState);
    }
}