package com.example.studyandtestapp.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class ItemLinearLayout  extends LinearLayout {

    private int lastX,lastY;

    public ItemLinearLayout(Context context) {
        super(context);
    }

    public ItemLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action=ev.getAction();
        int currentX=(int)ev.getX();
        int currentY=(int)ev.getY();

        boolean intercept=false;
        switch (action){
            case MotionEvent.ACTION_DOWN:
                lastX=currentX;
                lastY=currentY;
                break;

            case MotionEvent.ACTION_MOVE:
                int moveX=currentX-lastX;
                int moveY=currentY-lastY;
                if (Math.abs(moveX)>Math.abs(moveY))
                    intercept=true;
                break;

        }




        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
