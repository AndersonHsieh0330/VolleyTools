package com.AndyH.volleytools;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class loginpage extends DialogFragment {
    ImageButton exit_imgbutton;
    Button login_button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v=   inflater.inflate(R.layout.google_login_page,container,false);

        this.button_initialization(v);

        return v;
    }

    private void button_initialization(View view){
        exit_imgbutton = view.findViewById(R.id.exit_imgbutton);
        exit_imgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fmanager= getParentFragmentManager();
                fmanager.beginTransaction().remove(fmanager.findFragmentByTag("login_page_dialfrag")).commit();
            }

        });

        login_button=view.findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
}
