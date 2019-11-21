package com.example.studyandtestapp.CustomView.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MyButton extends View {
    public static final String TAG="MyButton";
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        Log.i(TAG, "dispatchTouchEvent: call");
        return super.dispatchTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev){
        Log.i(TAG, "onTouchEvent: call");
        return super.onTouchEvent(ev);

    }
}
