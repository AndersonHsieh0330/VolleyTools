package com.AndyH.volleytools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    Button welcomepage_button_scorer, welcomepage_button_history;
    ImageButton hamburger_menu;
    FragmentManager fragmentManager= this.getSupportFragmentManager();
    final public static String LOGIN_FRAGMENT_TAG = "login_page_dialfrag";
    final public static String SharedPreference_Key = "com.AndyH.VolleyTools";
    final public static String spBadScore_key = "badPeopleScore";
    final public static String spGoodScore_key = "goodPeopleScore";
    final public static String spBadSets_key = "badPeopleSets";
    final public static String spGoodSets_key = "goodPeopleSets";
    final public static String spBadTameName_key = "badPeopleTeamName";
    final public static String spGoodTeamName_key = "goodPeopleTeamName";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_initialization();

//        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference("message");
//        databaseReference.setValue("testing2 22222");
//
//        DatabaseReference databaseReference1 = firebaseDatabase.getReference("tick");
//        databaseReference1.setValue("ticktick");


    }


    private void button_initialization(){

        hamburger_menu = findViewById(R.id.hamburger_menu);
        hamburger_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginPage loginpage_fragment = new LoginPage();
                loginpage_fragment.show(fragmentManager,LOGIN_FRAGMENT_TAG);
            }
        });

        welcomepage_button_scorer = (Button)findViewById(R.id.button_launch_scorer);
        welcomepage_button_scorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoring_intent = new Intent(v.getContext(), Scorer.class);
                v.getContext().startActivity(scoring_intent);
            }
        });

        welcomepage_button_history = (Button)findViewById(R.id.button_launch_history);
        welcomepage_button_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent matchHistory_intent = new Intent(v.getContext(), MatchHistory.class);
                v.getContext().startActivity(matchHistory_intent);
                Log.d("recycle", "onClick: ");
            }
        });




    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.setContentView(R.layout.activity_main);
        this.button_initialization();
    }
}