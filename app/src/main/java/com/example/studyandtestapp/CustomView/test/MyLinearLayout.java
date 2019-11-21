package com.example.studyandtestapp.CustomView.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MyLinearLayout extends LinearLayout {
    public static final String TAG="myLinearLayout";

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        Log.i(TAG, "dispatchTouchEvent: call");
        return super.dispatchTouchEvent(ev);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        Log.i(TAG, "onInterceptTouchEvent: call");
        return super.onInterceptTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev){
        Log.i(TAG, "onTouchEvent: call");
        return super.onTouchEvent(ev);

    }
}
