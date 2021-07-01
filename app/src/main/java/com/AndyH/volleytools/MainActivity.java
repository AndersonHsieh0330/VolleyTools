package com.AndyH.volleytools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button welcomepage_button_scorer = (Button)findViewById(R.id.button_launch_scorer);
        welcomepage_button_scorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoring_intent = new Intent(v.getContext(), scorer.class);
                v.getContext().startActivity(scoring_intent);



            }
        });


        Button welcomepage_button_history = (Button)findViewById(R.id.button_launch_history);
        welcomepage_button_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}