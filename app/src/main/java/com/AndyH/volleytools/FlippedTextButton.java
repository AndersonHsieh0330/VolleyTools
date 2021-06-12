package com.AndyH.volleytools;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;

public class FlippedTextButton extends androidx.appcompat.widget.AppCompatButton{
    public FlippedTextButton(Context context) {
        super(context);
    }

    public FlippedTextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlippedTextButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

        float y = this.getHeight() / 2.0f;
        float x = this.getWidth() / 2.0f;
        canvas.rotate(180, x, y);

        super.onDraw(canvas);
        canvas.restore();
    }}