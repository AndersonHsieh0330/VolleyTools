package com.AndyH.volleytools;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ScoreCustomButton extends androidx.appcompat.widget.AppCompatButton {
    public ScoreCustomButton(@NonNull Context context) {
        super(context);
    }

    public ScoreCustomButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScoreCustomButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        //this is overritten to resolve "performClick" warning
        //this method will be executed for both normal touch events and for when the system calls this using Accessibility
        return super.performClick();
    }
}
