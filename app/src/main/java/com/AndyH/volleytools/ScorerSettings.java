package com.AndyH.volleytools;

import android.app.Dialog;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class ScorerSettings extends DialogFragment {
    private ScorerSettingActionListener actionListener;
    private ImageButton reStartButton, saveGameButton;

    public interface ScorerSettingActionListener{
         void onReStartGame(Boolean isRestarting);
         void onSaveGame(Boolean isSaving);
    }

    public static ScorerSettings newInstance(String title) {
        ScorerSettings frag = new ScorerSettings();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onResume() {
        super.onResume();
        setDialogSize();

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

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setDialogSize();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View inflatedView =  inflater.inflate(R.layout.dialogfrag_scorer_settings,container);
        BindViewsAndListeners(inflatedView);

        return inflatedView;
    }
    private void BindViewsAndListeners(View view){
        reStartButton = view.findViewById(R.id.scorerSettings_ImgButton_Restart);
        saveGameButton = view.findViewById(R.id.scorerSettings_ImgButton_saveGame);

        reStartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        saveGameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }



}
