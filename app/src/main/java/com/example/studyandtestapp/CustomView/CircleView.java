package com.example.studyandtestapp.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.studyandtestapp.R;

public class CircleView extends View {

    Paint mPaint = new Paint();
    int mColor = Color.BLACK;
    int mCenterX, mCenterY;
    int mRadius;
    //在activity或其他中直接创建 new时，调用该构造方法，之传入context
    public CircleView(Context context) {
        super(context);
    }

    /**
     * 声明在xml文件中，系统在解析这个XML布局的时候调用
     * @param context
     * @param attrs
     * super(context,attrs):
     *
     *            public View(Context context, @Nullable AttributeSet attrs) {
     *               this(context, attrs, 0);
     *           }
     *
     */
    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs, R.attr.SimpleCircleStyle);
    }

    /**
     * 这个一般是在第二个构造函数中调用，
     * 目的是让这个style与主题关联，不同的主题有不同的style
     * @param context
     * @param attrs
     * @param defStyleAttr 这个在第二个构造函数中传入,是一个主题them的一个style的resource id
     *                     其默认传入0；
     */
    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.CircleView,defStyleAttr,0);
        mColor=array.getColor(R.styleable.CircleView_circleColor,Color.YELLOW);
        mPaint.setColor(mColor);
        array.recycle();
    }


    //这个构造函数则是在第三个构造函数里调用，defStyleRes也是从上一个，也就是第三个构造函数传入,也是指向一个style资源id
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = w / 2;
        mCenterY = h / 2;
        mRadius = w < h ? w / 4 : h / 4;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
    }


}
