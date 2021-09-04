package com.AndyH.volleytools;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ScorerSettings extends DialogFragment {
    private SharedPreferences sp;
    private SharedPreferences.Editor speditor;
    private ScorerSettingActionListener actionListener;
    private ImageButton reStartButton, saveGameButton;

    public interface ScorerSettingActionListener{
         void onReStartGame(Boolean isRestarting);
         void onSaveGame(Boolean isSaving);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        sp = getContext().getSharedPreferences(Scorer.SHAREDPREFERENCE_KEY, Context.MODE_PRIVATE);
        speditor = sp.edit();

        View inflatedView =  inflater.inflate(R.layout.dialogfrag_scorer_settings,container);
        BindViewsAndListeners(inflatedView);

        return inflatedView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setDialogSize();
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setDialogSize();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ScorerSettingActionListener){
            actionListener = (ScorerSettingActionListener)context;
        }else{
            throw new RuntimeException(context.toString()
            +" must implement ScorerSettingActionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        actionListener = null;
    }

    private void BindViewsAndListeners(View view){
        reStartButton = view.findViewById(R.id.scorerSettings_ImgButton_Restart);
        saveGameButton = view.findViewById(R.id.scorerSettings_ImgButton_saveGame);

        reStartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                actionListener.onReStartGame(true);
                removeFragment();
            }
        });

        saveGameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(sp.getBoolean(Scorer.SP_LOGINSTATE,false)){
                    actionListener.onSaveGame(true);
                }else{
                    Toast.makeText(getContext(),R.string.saveGameRequestLoggingWaring,Toast.LENGTH_LONG).show();
                }
                removeFragment();
            }
        });

    }

    private void removeFragment(){
        FragmentManager currentFragManager = getParentFragmentManager();
        currentFragManager.beginTransaction().remove(currentFragManager.findFragmentByTag(Scorer.SCORERSETTING_FRAGMENT_TAG)).commit();
    }

    private void setDialogSize(){
        Window window = getDialog().getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;
        int height = size.y;
        //window.setLayout((int) (width * 0.75), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setLayout((int) (width * 0.4), (int) (height * 0.4));
        window.setGravity(Gravity.CENTER);
    }



}
