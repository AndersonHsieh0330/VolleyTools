package com.AndyH.volleytools;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class scorer_settings extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

//        setWidthPercent(80);

        return inflater.inflate(R.layout.dialogfrag_scorer_setting,container, false);

    }

    private void   setWidthPercent(int percentage) {
        double percent = ((double) percentage) / 100;
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        int current_height= dm.heightPixels*percentage;
        int current_width = dm.widthPixels*percentage;

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = current_width;
        params.height = current_height ;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

}
