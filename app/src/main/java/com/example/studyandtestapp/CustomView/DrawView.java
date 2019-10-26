package com.example.studyandtestapp.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawView extends View {
    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public void onDraw(Canvas canvas){
        Paint paint=new Paint();
        paint.setARGB(0xFF,0xFF,0,0);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        testCanvas(canvas);

    }

    //弧线
    private void drawArc(Canvas canvas,Paint paint){
        Path path=new Path();
        path.moveTo(100,100);
        RectF rect=new RectF(100,100,600,800);
        path.addArc(rect,0,180 );
        canvas.drawPath(path,paint);

    }
    private void drawRect(Canvas canvas,Paint paint){
        canvas.drawRect(100,100,600,800,paint);

    }

    private Paint generate(int color,Paint.Style style,int width){
        Paint paint=new Paint();
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(width);
        return paint;
    }

    //每次调用 canvas.draw****()都会产生全新的图层
    private void testCanvas(Canvas canvas){

        Paint paint1=generate(Color.GREEN, Paint.Style.STROKE,5);
        Paint paint2=generate(Color.BLACK, Paint.Style.STROKE,5);

        Rect rect=new Rect(0,0,400,200);
        canvas.drawRect(rect,paint1);


        canvas.translate(100,100);
        canvas.drawRect(rect,paint2);

    }


}
