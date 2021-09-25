package com.AndyH.volleytools;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Objects;


public class ScorerSettings extends DialogFragment {
    private ScorerSettingActionListener actionListener;
    private boolean isLoggedIn;

    public interface ScorerSettingActionListener{
         void onReStartGame(Boolean isRestarting);
         void onSaveGame(Boolean isSaving);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isLoggedIn = bundle.getBoolean(Scorer.SCORERSETTINGS_DFBUDDLEKEY_ISLOGGEDIN);
        }else{
            Log.d("log", "onCreateView: ScorerSettings, no bundle ");
        }
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
        ImageButton reStartButton = view.findViewById(R.id.scorerSettings_ImgButton_Restart);
        ImageButton saveGameButton = view.findViewById(R.id.scorerSettings_ImgButton_saveGame);

        reStartButton.setOnClickListener(v -> {
            actionListener.onReStartGame(true);
            removeFragment();
        });

        saveGameButton.setOnClickListener(v -> {
            if(isLoggedIn){
                actionListener.onSaveGame(true);
            }else{
                new AlertDialog.Builder(v.getContext())
                        .setTitle(R.string.unableToSaveGameH_Title)
                        .setMessage(R.string.saveGameRequestLoggingWaring)
                        .setPositiveButton(R.string.alertDialogOKButtonText, (dialog, which) -> {
                            //alertDialog gets closed automatically
                        }).show();

            }
            removeFragment();
        });

    }

    private void removeFragment(){
        FragmentManager currentFragManager = getParentFragmentManager();
        Fragment scorerSettingsDF = currentFragManager.findFragmentByTag(Scorer.SCORERSETTING_FRAGMENT_TAG);
        if(scorerSettingsDF==null){
            Log.d("log", "removeFragment: can't find fragment while removing scorerSettings");
        }else {
            currentFragManager.beginTransaction().remove(scorerSettingsDF).commit();
        }
    }

    private void setDialogSize(){
        //get the size of the screen
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;
        int height = size.y;

        //set the fragment size by ratio of the height/width of the screen
        window.setLayout((int) (width * 0.4), (int) (height * 0.4));
        window.setGravity(Gravity.CENTER);

    }



}
