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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class scorer extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor speditor;
    Boolean is_game_on;
    Game current_game;
    Button goodpeople_score_button,badpeople_score_button,goodpeople_set_button,badpeople_set_button,button_leave,button_saveGame;
    EditText leftBadTeam, rightGoodTeam;
    FragmentManager fmanager=this.getSupportFragmentManager();
    FirebaseDatabase rootNode;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_scorer);
        sp = this.getSharedPreferences(MainActivity.SharedPreference_Key,Context.MODE_PRIVATE);
        speditor = sp.edit();
        current_game = initializeCurrentGame();
        this.initialize_views();




    }

    private Game initializeCurrentGame(){
        current_game = new Game();
        if(sp.contains(MainActivity.spBadScore_key)){
            current_game.setBadpeople_points(sp.getInt(MainActivity.spBadScore_key,99));
        }else{
            current_game.setBadpeople_points(0);
            speditor.putInt(MainActivity.spBadScore_key,0).apply();
        }

        if(sp.contains(MainActivity.spGoodScore_key)){
            current_game.setGoodpeople_points(sp.getInt(MainActivity.spGoodScore_key,99));
        }else{
            current_game.setGoodpeople_points(0);
            speditor.putInt(MainActivity.spGoodScore_key,0).apply();

        }

        if(sp.contains(MainActivity.spBadSets_key)){
            current_game.setBadpeople_sets(sp.getInt(MainActivity.spBadSets_key,99));
        }else{
            current_game.setBadpeople_sets(0);
            speditor.putInt(MainActivity.spBadSets_key,0).apply();

        }

        if(sp.contains(MainActivity.spGoodSets_key)){
            current_game.setGoodpeople_sets(sp.getInt(MainActivity.spGoodSets_key,99));
        }else{
            current_game.setGoodpeople_sets(0);
            speditor.putInt(MainActivity.spGoodSets_key,0).apply();
        }

        if(sp.contains(MainActivity.spBadTameName_key)){
            current_game.setBadpeople_teamname(sp.getString(MainActivity.spBadTameName_key,"Error"));
        }else{
            String defaultBadTN = this.getResources().getString(R.string.teams_badpeople);
            current_game.setGoodpeople_teamname(defaultBadTN);
            speditor.putString(MainActivity.spBadTameName_key,defaultBadTN).apply();
        }

        if(sp.contains(MainActivity.spGoodTeamName_key)){
            current_game.setGoodpeople_teamname(sp.getString(MainActivity.spGoodTeamName_key,"Error"));
        }else{
            String defaultGoodTN = this.getResources().getString(R.string.teams_goodpeople);
            current_game.setGoodpeople_teamname(defaultGoodTN);
            speditor.putString(MainActivity.spGoodTeamName_key,defaultGoodTN).apply();
        }

        return current_game;
    }


    private void initialize_views(){

    goodpeople_score_button =  findViewById(R.id.goodpeople_button_score);
    goodpeople_score_button.setText(String.valueOf(sp.getInt(MainActivity.spGoodScore_key,99)));
    goodpeople_score_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            current_game.goodpeople_gain_point();
            speditor.putInt(MainActivity.spGoodScore_key,current_game.getGoodpeople_points());
            speditor.apply();
            goodpeople_score_button.setText(String.valueOf(sp.getInt(MainActivity.spGoodScore_key,99)));

        }
    });


    badpeople_score_button = findViewById(R.id.badpeople_button_score);
    badpeople_score_button.setText(String.valueOf(sp.getInt(MainActivity.spBadScore_key,99)));
    badpeople_score_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            current_game.badpeople_gain_point();
            speditor.putInt(MainActivity.spBadScore_key,current_game.getBadpeople_points());
            speditor.apply();
            badpeople_score_button.setText(String.valueOf(sp.getInt(MainActivity.spBadScore_key,99)));

        }
    });

    goodpeople_set_button =  this.findViewById(R.id.goodpeople_button_set);
    goodpeople_set_button.setText(String.valueOf(sp.getInt(MainActivity.spGoodSets_key,0)));
    goodpeople_set_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            current_game.goodpeople_gain_set();
            speditor.putInt(MainActivity.spGoodSets_key,current_game.getGoodpeople_sets());
            speditor.apply();
            goodpeople_set_button.setText(String.valueOf(sp.getInt(MainActivity.spGoodSets_key,0)));
        }
    });

    badpeople_set_button =  this.findViewById(R.id.badpeople_button_set);
    badpeople_set_button.setText(String.valueOf(sp.getInt(MainActivity.spBadSets_key,0)));
    badpeople_set_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            current_game.badpeople_gain_set();
            speditor.putInt(MainActivity.spBadSets_key,current_game.getBadpeople_sets());
            speditor.apply();
            badpeople_set_button.setText(String.valueOf(sp.getInt(MainActivity.spBadSets_key,0)));

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

    leftBadTeam = this.findViewById(R.id.badpeople_name);
    leftBadTeam.setText(sp.getString(MainActivity.spBadTameName_key,"Error"));
    leftBadTeam.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            current_game.setBadpeople_teamname(s.toString());
            speditor.putString(MainActivity.spBadTameName_key,s.toString()).apply();

        }
    });

    rightGoodTeam = this.findViewById(R.id.goodpeople_name);
    rightGoodTeam.setText(sp.getString(MainActivity.spGoodTeamName_key,"Error"));
    rightGoodTeam.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            current_game.setGoodpeople_teamname(s.toString());
            speditor.putString(MainActivity.spGoodTeamName_key,s.toString()).apply();
        }
    });

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_scorer);
        this.initialize_views();

    }
}