package com.AndyH.volleytools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Scorer extends AppCompatActivity implements ScorerSettings.ScorerSettingActionListener{
    private SharedPreferences sp;
    private SharedPreferences.Editor speditor;
    private Game currentGame;
    private Button goodpeople_score_button,badpeople_score_button,goodpeople_set_button,badpeople_set_button;
    private ImageButton button_leave,button_Settings;
    private EditText leftBadTeam, rightGoodTeam;
    private FragmentManager fmanager=this.getSupportFragmentManager();
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference rootRef;
    private DatabaseReference currentUserRef;
    private DatabaseReference currentUserHistoryGameRef;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm aa");


    final public static String SHAREDPREFERENCE_KEY = "com.AndyH.VolleyTools";
    final public static String SP_BADSCORE_KEY = "badPeopleScore";
    final public static String SP_GOODSCORE_KEY = "goodPeopleScore";
    final public static String SP_BADSETS_KEY = "badPeopleSets";
    final public static String SP_GOODSETS_KEY = "goodPeopleSets";
    final public static String SP_BADTEAMNAME_KEY = "badPeopleTeamName";
    final public static String SP_GOODTEAMNAME_KEY = "goodPeopleTeamName";
    final public static String SCORERSETTINGS_DFBUDDLEKEY_ISLOGGEDIN = "scorerSettingsFragmentBundle_isLoggedIn";
    final public static String SCORERSETTING_FRAGMENT_TAG = "scorerSettings_tag";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_scorer);
        sp = this.getSharedPreferences(SHAREDPREFERENCE_KEY,Context.MODE_PRIVATE);
        speditor = sp.edit();

        initializeFirebaseAssociateReference();



        currentGame = initializeCurrentGame();
        BindViewsAndListeners();
        initializeViewContent();
        
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_scorer);
        BindViewsAndListeners();
        initializeViewContent();

    }

    @Override
    public void onReStartGame(Boolean isRestarting) {
        if(isRestarting){
            resetGame();
            Log.d("saveorrestart", "onReStartGame: isRestarting = "+isRestarting);
        }
    }

    @Override
    public void onSaveGame(Boolean isSaving) {
        if(isSaving&&(currentUser!= null)) {
            currentGame.setGameEndTime(simpleDateFormat.format(Calendar.getInstance().getTime()));
            saveGameToFireBase(currentGame);
            Log.d("saveorrestart", "onSaveGame: isSaving = " + isSaving);

        }
    }

    private Game initializeCurrentGame(){
        currentGame = new Game();
        if(sp.contains(SP_BADSCORE_KEY)){
            currentGame.setBadpeople_points(sp.getInt(SP_BADSCORE_KEY,99));
        }else{
            currentGame.setBadpeople_points(0);
            speditor.putInt(SP_BADSCORE_KEY,0).apply();
        }

        if(sp.contains(SP_GOODSCORE_KEY)){
            currentGame.setGoodpeople_points(sp.getInt(SP_GOODSCORE_KEY,99));
        }else{
            currentGame.setGoodpeople_points(0);
            speditor.putInt(SP_GOODSCORE_KEY,0).apply();

        }

        if(sp.contains(SP_BADSETS_KEY)){
            currentGame.setBadpeople_sets(sp.getInt(SP_BADSETS_KEY,99));
        }else{
            currentGame.setBadpeople_sets(0);
            speditor.putInt(SP_BADSETS_KEY,0).apply();

        }

        if(sp.contains(SP_GOODSETS_KEY)){
            currentGame.setGoodpeople_sets(sp.getInt(SP_GOODSETS_KEY,99));
        }else{
            currentGame.setGoodpeople_sets(0);
            speditor.putInt(SP_GOODSETS_KEY,0).apply();
        }

        if(sp.contains(SP_BADTEAMNAME_KEY)){
            currentGame.setBadpeople_teamname(sp.getString(SP_BADTEAMNAME_KEY,"Error"));
        }else{
            String defaultBadTN = this.getResources().getString(R.string.teams_badpeople);
            currentGame.setGoodpeople_teamname(defaultBadTN);
            speditor.putString(SP_BADTEAMNAME_KEY,defaultBadTN).apply();
        }

        if(sp.contains(SP_GOODTEAMNAME_KEY)){
            currentGame.setGoodpeople_teamname(sp.getString(SP_GOODTEAMNAME_KEY,"Error"));
        }else{
            String defaultGoodTN = this.getResources().getString(R.string.teams_goodpeople);
            currentGame.setGoodpeople_teamname(defaultGoodTN);
            speditor.putString(SP_GOODTEAMNAME_KEY,defaultGoodTN).apply();
        }

        return currentGame;
    }

    private void initializeViewContent(){
        goodpeople_score_button.setText(String.valueOf(sp.getInt(SP_GOODSCORE_KEY,99)));
        badpeople_score_button.setText(String.valueOf(sp.getInt(SP_BADSCORE_KEY,99)));
        goodpeople_set_button.setText(String.valueOf(sp.getInt(SP_GOODSETS_KEY,0)));
        badpeople_set_button.setText(String.valueOf(sp.getInt(SP_BADSETS_KEY,0)));
        leftBadTeam.setText(sp.getString(SP_BADTEAMNAME_KEY,"Error"));
        rightGoodTeam.setText(sp.getString(SP_GOODTEAMNAME_KEY,"Error"));
    }

    private void BindViewsAndListeners(){

        goodpeople_score_button =  findViewById(R.id.goodpeople_button_score);
        goodpeople_score_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentGame.goodpeople_gain_point();
                speditor.putInt(SP_GOODSCORE_KEY,currentGame.getGoodpeople_points());
                speditor.apply();
                goodpeople_score_button.setText(String.valueOf(sp.getInt(SP_GOODSCORE_KEY,99)));
            }
        });


        badpeople_score_button = findViewById(R.id.badpeople_button_score);
        badpeople_score_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentGame.badpeople_gain_point();
                speditor.putInt(SP_BADSCORE_KEY,currentGame.getBadpeople_points());
                speditor.apply();
                badpeople_score_button.setText(String.valueOf(sp.getInt(SP_BADSCORE_KEY,99)));
            }
        });

        goodpeople_set_button =  this.findViewById(R.id.goodpeople_button_set);
        goodpeople_set_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentGame.goodpeople_gain_set();
                speditor.putInt(SP_GOODSETS_KEY,currentGame.getGoodpeople_sets());
                speditor.apply();
                goodpeople_set_button.setText(String.valueOf(sp.getInt(SP_GOODSETS_KEY,0)));
            }
        });

        badpeople_set_button =  this.findViewById(R.id.badpeople_button_set);
        badpeople_set_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentGame.badpeople_gain_set();
                speditor.putInt(SP_BADSETS_KEY,currentGame.getBadpeople_sets());
                speditor.apply();
                badpeople_set_button.setText(String.valueOf(sp.getInt(SP_BADSETS_KEY,0)));
            }
        });

        button_leave = this.findViewById(R.id.goodpeople_button_leave);
        button_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);

            }
        });
        button_Settings =this.findViewById(R.id.badpeople_button_Settings);
        button_Settings.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ScorerSettings scorerSettingsClass = new ScorerSettings();
                Bundle bundle = new Bundle();
                bundle.putBoolean(SCORERSETTINGS_DFBUDDLEKEY_ISLOGGEDIN,(currentUser != null));
                scorerSettingsClass.setArguments(bundle);
                scorerSettingsClass.show(fmanager,SCORERSETTING_FRAGMENT_TAG);

            }
        });

        leftBadTeam = this.findViewById(R.id.badpeople_name);
        leftBadTeam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentGame.setBadpeople_teamname(s.toString());
                speditor.putString(SP_BADTEAMNAME_KEY,s.toString()).apply();

            }
        });

        rightGoodTeam = this.findViewById(R.id.goodpeople_name);
        rightGoodTeam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentGame.setGoodpeople_teamname(s.toString());
                speditor.putString(SP_GOODTEAMNAME_KEY,s.toString()).apply();
            }
        });

    }

    private void resetGame(){
        currentGame = new Game(0,0,0,0,"好人","壞人",null);

        speditor.putInt(SP_GOODSCORE_KEY,currentGame.getGoodpeople_points());
        speditor.putInt(SP_BADSCORE_KEY,currentGame.getBadpeople_points());
        speditor.putInt(SP_GOODSETS_KEY,currentGame.getGoodpeople_sets());
        speditor.putInt(SP_BADSETS_KEY,currentGame.getBadpeople_sets());
        speditor.putString(SP_BADTEAMNAME_KEY,currentGame.getBadpeople_teamname());
        speditor.putString(SP_GOODTEAMNAME_KEY,currentGame.getGoodpeople_teamname());
        speditor.apply();

        initializeViewContent();
    }

    private void initializeFirebaseAssociateReference(){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        firebaseDatabase= FirebaseDatabase.getInstance();
        rootRef = firebaseDatabase.getReference();
        if(currentUser!= null){
            currentUserRef = rootRef.child(currentUser.getUid().toString());
            currentUserHistoryGameRef = currentUserRef.child("historyGames");
            }
        else{
            Log.d("saveorrestart", "user not logged in");
        }
    }
    private void saveGameToFireBase(Game game){
        currentUserHistoryGameRef.push().setValue(game);
    }
}