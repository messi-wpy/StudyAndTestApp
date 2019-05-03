package com.example.studyandtestapp.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MovableView extends View {

    private  int  lastX;
    private  int lastY;
    public MovableView(Context context) {
        super(context);
    }

    public MovableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MovableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MovableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x=(int) event.getX();
        int y=(int)event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                performClick();
                break;
            case MotionEvent.ACTION_DOWN:
                lastX=x;
                lastY=y;
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX=x-lastX;
                int moveY=y-lastY;
                //调用layout来调整位置
                //layout(getLeft()+moveX,getTop()+moveY,getRight()+moveX,getBottom()+moveY);


                // offsetLeftAndRight(moveX);
                //offsetTopAndBottom(moveY);
                super.scrollBy(-moveX,-moveX);
                break;
        }



        return true;
    }
}
