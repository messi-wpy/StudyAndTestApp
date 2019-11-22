package com.example.studyandtestapp.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.security.MessageDigest;

public class RectView extends View {
    private Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mColor= Color.RED;
    public RectView(Context context) {
        super(context);
        initDraw();
    }

    public RectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDraw();
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initDraw(){
        mPaint.setColor(mColor);
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
        int width=getWidth()-getPaddingLeft()-getPaddingRight();
        int height=getHeight()-getPaddingTop()-getPaddingBottom();

        canvas.drawRect(0+getPaddingLeft(),0+getPaddingTop(),width+getPaddingRight(),height+getPaddingBottom(),mPaint);
    }
}
