package com.example.studyandtestapp.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MovableView extends View {

    private  int  lastX;
    private  int lastY;
    private Scroller mScroller;
    public MovableView(Context context) {
        super(context);
    }

    public MovableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller=new Scroller(context);
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
        Log.i("ondraw", "draw: ");
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
                if (Math.abs(x-lastX)<=3&&Math.abs(y-lastY)<=3)
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
                layout(getLeft()+moveX,getTop()+moveY,getRight()+moveX,getBottom()+moveY);

                Log.i("tag", "onTouchEvent: "+x+"   "+y+"  "+lastX+"  "+lastY);
                // offsetLeftAndRight(moveX);
                //offsetTopAndBottom(moveY);
                //((View)getParent()).scrollBy(-moveX,-moveY);
                break;
        }


        return true;
    }
    @Override
    public void computeScroll(){
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            ((View)getParent()).scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }

    }

    public void smoothcrollTo(int destX,int destY){
        int scrollX=getScrollX();
        int delta=destX-scrollX;
        mScroller.startScroll(scrollX,0,delta,0,2000);
        invalidate();

    }
}
