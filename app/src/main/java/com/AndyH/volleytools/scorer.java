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
    SharedPreferences sp;
    SharedPreferences.Editor speditor;
    Boolean is_game_on;
    Game current_game;
    Button goodpeople_score_button,badpeople_score_button,goodpeople_set_button,badpeople_set_button,button_leave,button_saveGame;
    FragmentManager fmanager=this.getSupportFragmentManager();
    FirebaseDatabase rootNode;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_scorer);
        sp = this.getSharedPreferences(MainActivity.SharedPreference_Key,Context.MODE_PRIVATE);
        speditor = sp.edit();

        current_game = new Game(25, 23, 0, 0, "好人", "壞人", Calendar.getInstance());

        this.initialize_buttons();




    }


private void initialize_buttons(){
    goodpeople_score_button =  findViewById(R.id.goodpeople_button_score);
    goodpeople_score_button.setText(String.valueOf(current_game.getGoodpeople_points()));
    goodpeople_score_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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

        }
    });
    button_saveGame =(Button) this.findViewById(R.id.badpeople_button_saveGame);
    button_saveGame.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            scorer_saveGame scorer_saveGame_class = new scorer_saveGame();
            scorer_saveGame_class.show(fmanager,"scorer_saveGame_tag");
        }
    });
}

    private void saveGameToSharedPreference(){
        speditor.putInt(MainActivity.spBadScore_key,current_game.getBadpeople_points());
        speditor.putInt(MainActivity.spGoodScore_key,current_game.getGoodpeople_points());
        speditor.putInt(MainActivity.spBadSets_key,current_game.getBadpeople_sets());
        speditor.putInt(MainActivity.spGoodSets_key,current_game.getGoodpeople_sets());
        speditor.putString(MainActivity.spBadTameName_key,current_game.getBadpeople_teamname());
        speditor.putString(MainActivity.spGoodTeamName_key,current_game.getGoodpeople_teamname());


    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_scorer);
        this.initialize_buttons();

    }
}