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

public class scorer extends AppCompatActivity {
    Boolean is_game_on;
    Game current_game;
    Button goodpeople_score_button,badpeople_score_button,goodpeople_set_button,badpeople_set_button,button_leave,button_settings;
    FragmentManager fmanager=this.getSupportFragmentManager();
    String tag_game_saved = "ongoing_game";
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if(current_game!=null){
            outState.putParcelable(tag_game_saved,current_game);
        }
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        //onRestoredInstanceState is only called when saedInstanceState != null
        current_game = savedInstanceState.getParcelable(tag_game_saved);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_scorer);
        Log.d("mutage","oncreate called");
        // if saveInstanceState has the key "is_game_on", then it is always true
//        if(savedInstanceState != null){
//            if(savedInstanceState.getBoolean("is_game_on")==false){
//                //should never end up here
//                throw new RuntimeException("key false state: is_game_on");
//            }else{
//                //load unfinished game
//                is_game_on = savedInstanceState.getBoolean("is_game_on");
//                current_game= savedInstanceState.getParcelable("ongoing_game");
//
//            }
//        }else{
//
//            //create new game with default settings
//            this.is_game_on = true;
//            current_game = new Game(25,2,14,
//                    13,0,0,null, null);
//        }
        if(current_game==null) {
            current_game = new Game(25, 2, 0, 0, 0, 0, null, null);
        }else{
            current_game = savedInstanceState.getParcelable(tag_game_saved);
        }
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
//                savedInstanceState.putParcelable("ongoing_game",current_game);

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
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_scorer);
        this.initialize_buttons();

    }
}