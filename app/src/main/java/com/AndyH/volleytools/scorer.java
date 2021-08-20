package com.AndyH.volleytools;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class scorer extends AppCompatActivity {
    Boolean is_game_on;
    Game current_game;
    Button goodpeople_score_button,badpeople_score_button,goodpeople_set_button,badpeople_set_button,button_leave,button_settings;
    FragmentManager fmanager=this.getSupportFragmentManager();
    String tag_game_saved = "ongoing_game";
    FirebaseDatabase rootNode;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_scorer);
        Log.d("mutage","oncreate called");

            current_game = new Game(25, 23, 0, 0, "好人", "壞人", Calendar.getInstance());

        this.initialize_buttons();




    }


private void initialize_buttons(){
    goodpeople_score_button =  findViewById(R.id.goodpeople_button_score);
    goodpeople_score_button.setText(String.valueOf(current_game.getGoodpeople_points()));
    goodpeople_score_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("buttontag","button: "+"socore");
            current_game.goodpeople_gain_point();
            goodpeople_score_button.setText(String.valueOf(current_game.getGoodpeople_points()));
        }
    });


    badpeople_score_button = findViewById(R.id.badpeople_button_score);
    badpeople_score_button.setText(String.valueOf(current_game.getBadpeople_points()));
    badpeople_score_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            current_game.badpeople_gain_point();
            badpeople_score_button.setText(String.valueOf(current_game.getBadpeople_points()));
        }
    });

    goodpeople_set_button =  this.findViewById(R.id.goodpeople_button_set);
    goodpeople_set_button.setText(String.valueOf(current_game.getGoodpeople_sets()));
    goodpeople_set_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            current_game.goodpeople_gain_set();
            goodpeople_set_button.setText(String.valueOf(current_game.getGoodpeople_sets()));
        }
    });

    badpeople_set_button =  this.findViewById(R.id.badpeople_button_set);
    badpeople_set_button.setText(String.valueOf(current_game.getBadpeople_sets()));
    badpeople_set_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            current_game.badpeople_gain_set();
            badpeople_set_button.setText(String.valueOf(current_game.getBadpeople_sets()));
        }
    });

    button_leave = (Button)this.findViewById(R.id.goodpeople_button_leave);
    button_leave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            startActivity(intent);
            Log.d("buttontag","button: "+"socore");

        }
    });
    button_settings =(Button) this.findViewById(R.id.badpeople_button_Settings);
    button_settings.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            scorer_settings scorer_settings_class = new scorer_settings();
            scorer_settings_class.show(fmanager,"scorer_settings_tag");
        }
    });
}



    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_scorer);
        this.initialize_buttons();

    }
}