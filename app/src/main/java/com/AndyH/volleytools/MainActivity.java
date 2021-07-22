package com.AndyH.volleytools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {
    Button welcomepage_button_scorer, welcomepage_button_history;
    ImageButton hamburger_menu;
    FragmentManager fragmentManager= this.getSupportFragmentManager();
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
                loginpage loginpage_fragment = new loginpage();
                loginpage_fragment.show(fragmentManager,"login_page_dialfrag");
            }
        });
        welcomepage_button_scorer = (Button)findViewById(R.id.button_launch_scorer);
        welcomepage_button_scorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoring_intent = new Intent(v.getContext(), scorer.class);
                v.getContext().startActivity(scoring_intent);
            }
        });




//       if(getResources().getConfiguration().orientation== ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE ||getResources().getConfiguration().orientation== ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
//           welcomepage_button_history = (Button)findViewById(R.id.button_launch_history_flippedtext);
//           welcomepage_button_history.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });}
//       if(getResources().getConfiguration().orientation== ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
//           welcomepage_button_history = (Button)findViewById(R.id.button_launch_history);
//           welcomepage_button_history.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//
//               }
//           });
//        }


    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.setContentView(R.layout.activity_main);
        this.button_initialization();
    }
}