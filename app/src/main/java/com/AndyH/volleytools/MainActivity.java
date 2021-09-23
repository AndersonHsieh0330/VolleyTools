package com.AndyH.volleytools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    Button welcomepage_button_scorer, welcomepage_button_history;
    ImageButton hamburger_menu;
    TextView welcomText;
    FragmentManager fragmentManager;
    final public static String LOGIN_FRAGMENT_TAG = "login_page_dialfrag";
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.Mint_Green));
        fragmentManager = this.getSupportFragmentManager();
        setContentView(R.layout.activity_main);
        initializeFirebaseAssociateReference();
        BindViewsAndListeners();

    }


    private void BindViewsAndListeners(){

        hamburger_menu = findViewById(R.id.main_buttonHamburger);
        hamburger_menu.setOnClickListener(v -> {
            LoginPage loginpage_fragment = new LoginPage();
            loginpage_fragment.show(fragmentManager,LOGIN_FRAGMENT_TAG);
        });

        welcomepage_button_scorer = findViewById(R.id.button_launch_scorer);
        welcomepage_button_scorer.setOnClickListener(v -> {
            Intent scoring_intent = new Intent(v.getContext(), Scorer.class);
            v.getContext().startActivity(scoring_intent);
        });

        welcomepage_button_history = findViewById(R.id.button_launch_history);
        welcomepage_button_history.setOnClickListener(v -> {
            Intent matchHistory_intent = new Intent(v.getContext(), MatchHistory.class);
            if(mAuth.getCurrentUser() != null){
                v.getContext().startActivity(matchHistory_intent);
            }else{
                new AlertDialog.Builder(v.getContext())
                        .setTitle(R.string.unableToLaunchHMActivity_Title)
                        .setMessage(R.string.unableToLaunchHMActivity_Message)
                        .setPositiveButton(R.string.alertDialogOKButtonText, (dialog, which) -> {

                        }).show();

            }
        });

        welcomText = findViewById(R.id.welcome_text);
        if(mAuth.getCurrentUser()!= null){
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                welcomText.setText(String.format(getResources().getString(R.string.welcomeStatement),currentUser.getDisplayName()));
            }else{
                welcomText.setText(String.format(getResources().getString(R.string.welcomeStatement_LineBreak),currentUser.getDisplayName()));
            }
        }else{
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                welcomText.setText(String.format(getResources().getString(R.string.welcomeStatement),getResources().getString(R.string.volleyballer)));
            }else{
                welcomText.setText(String.format(getResources().getString(R.string.welcomeStatement_LineBreak),getResources().getString(R.string.volleyballer)));
            }

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.setContentView(R.layout.activity_main);
        this.BindViewsAndListeners();
    }

    private void initializeFirebaseAssociateReference(){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

}