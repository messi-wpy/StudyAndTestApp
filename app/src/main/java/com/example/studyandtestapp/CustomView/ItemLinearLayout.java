package com.example.studyandtestapp.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import com.example.studyandtestapp.MainActivity;

public class ItemLinearLayout  extends LinearLayout {

    private int lastX,lastY;
    private boolean intercept=false;
    private int width,height;
    private int moveSpec=0;
    private final String TAG="as";
    private int moved=0;
    private VelocityTracker tracker;
    private Scroller scroller;
    public ItemLinearLayout(Context context) {
        super(context);
        tracker=VelocityTracker.obtain();
        scroller=new Scroller(getContext());
    }

    public ItemLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        tracker=VelocityTracker.obtain();
        scroller=new Scroller(getContext());

    }

    public ItemLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        tracker=VelocityTracker.obtain();
        scroller=new Scroller(getContext());

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("as", "onInterceptTouchEvent: ");
        int action=ev.getAction();
        int currentX=(int)ev.getX();
        int currentY=(int)ev.getY();

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


        if (intercept)
            return true;
        else
            return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        init();
    }

    private void init(){
        width=getWidth();
        height=getHeight();
        Log.i(TAG, "init: "+width+"  "+height);
        int num=getChildCount();
        for (int i = 1; i <num ; i++) {
            moveSpec+=getChildAt(i).getWidth();
        }
        getChildAt(0).setClickable(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        tracker.addMovement(event);
        Log.i("as", "onTouchEvent: ");
        if (intercept){
            Log.i("as", "onTouchEvent: intercept ");
            int x=(int)event.getX();
            int y=(int)event.getY();
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    lastX=x;
                    lastY=y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int moveX=x-lastX;
                    int move=-moveX;
                    moved+=move;
                    if (moved>=0&&moved<=moveSpec) {
                        scrollBy(move, 0);
                        Log.i("scroll", "onTouchEvent: scroll");
                        Log.i(TAG, "onTouchEvent: left"+getLeft()+"right   "+getRight());
                    }else {
                        if (moved<0) {
                            moved -= move;
                            scrollBy(-moved,0);
                            moved=0;
                        }else {
                            moved-=move;
                            scrollBy(moveSpec-moved,0);
                            moved=moveSpec;
                        }
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    Log.i(TAG, "onTouchEvent: -----------up");
                    intercept=false;
                    /*
                    tracker.computeCurrentVelocity(1000);
                    float v=tracker.getXVelocity();
                    Log.i(TAG, "onTouchEvent: "+getTop()+"--------------------v   "+v);
                    if (Math.abs(v)>50) {
                        if (v>50) {
                            smoothscrollTo(0, 0);
                            moved = 0;
                        }
                        else{
                            smoothscrollTo(moveSpec, 0);
                            moved = moveSpec;
                        }
                    }
                    */
            }
            lastX=x;
            return true;
        }
        else
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll(){
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
        }

    }

    public void smoothscrollTo(int destX,int destY){
        scroller.startScroll(getScrollX(),getScrollY(),destX-getScrollX(),destY-getScrollY(),1000);
        invalidate();


    }
}
