package com.AndyH.volleytools;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class scorer_settings extends DialogFragment {

    public static scorer_settings newInstance(String title) {
        scorer_settings frag = new scorer_settings();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onResume() {
        super.onResume();


            Window window = getDialog().getWindow();
            Point size = new Point();

            Display display = window.getWindowManager().getDefaultDisplay();
            display.getSize(size);

            int width = size.x;
            int height = size.y;
            //window.setLayout((int) (width * 0.75), WindowManager.LayoutParams.WRAP_CONTENT);
            window.setLayout((int) (width * 0.75), (int)(height*0.75));
            window.setGravity(Gravity.CENTER);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.dialogfrag_scorer_setting,container);

//        setWidthPercent(80);



    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
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
