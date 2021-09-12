package com.AndyH.volleytools;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ScoreOnSwipeTouchListener implements OnTouchListener{

    private final GestureDetector gestureDetector;

    public ScoreOnSwipeTouchListener(Context ctx){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
        }

    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 50;//minimum distance to be considered a swipe
        private static final int SWIPE_VELOCITY_THRESHOLD = 50;//minimum speed to be considered a swipe
        private static final int CLICK_POSITION_THRESHOLD = 5;
        private int startTime;
        private int endTime;
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            onLongPressed();
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
//            onClick();
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            Log.d("onflingtest", "onFling: ");
            Log.d("onflingtest", "onFling: ");
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            //logic for swipe right
                            onSwipeTop();
                        } else {
                            //logic for swip left
                            onSwipeTop();
                        }
                        result = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }
    public void onLongPressed(){

    }

    public void onClick(){

    }


}
