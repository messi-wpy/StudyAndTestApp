package com.example.studyandtestapp.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import java.security.MessageDigest;

public class RectView extends View {
    private  int  lastX;
    private  int lastY;
    private Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private Scroller mScroller;

    private int mColor= Color.RED;
    public RectView(Context context) {
        super(context);
        initDraw(context);
    }

    public RectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDraw(context);
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initDraw(Context context){
        mPaint.setColor(mColor);
        mScroller=new Scroller(context);
        mPaint.setStrokeWidth((float)1.5);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heighMode=MeasureSpec.getMode(heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heighSize=MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode==MeasureSpec.AT_MOST&&MeasureSpec.AT_MOST==heighMode){
            setMeasuredDimension(400,400);
        }else if (widthMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(400,heighSize);
        }else if (heighMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize,400);
        }

    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Log.i("test", "onDraw: ");
        int width=getWidth()-getPaddingLeft()-getPaddingRight();
        int height=getHeight()-getPaddingTop()-getPaddingBottom();

        canvas.drawRect(0+getPaddingLeft(),0+getPaddingTop(),width+getPaddingRight(),height+getPaddingBottom(),mPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x=(int) event.getX();
        int y=(int)event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                //smoothcrollTo(-500,-500);
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
        mScroller.startScroll(getScrollX(),getScrollY(),destX-getScrollX(),destY-getScrollY(),1000);
        invalidate();
    }
}
