package com.AndyH.volleytools;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SetsOnTouchListener implements OnTouchListener {

    private final GestureDetector gestureDetector;

    public SetsOnTouchListener(Context ctx) {
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }


    private static int LONGPRESS_THRESHOLD = 175;//is only considered a long press if duration exceeds this time
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        long duration = event.getEventTime() - event.getDownTime();
        if(event.getAction()==MotionEvent.ACTION_UP){
            if(duration>=LONGPRESS_THRESHOLD){
                onLongPressed();
            }else{
                onClick();
            }
        }
        return false;
    }

    private final class GestureListener extends SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            onLongPressed();
        }


    }

    public void onLongPressed() {

    }

    public void onClick(){

    }
}