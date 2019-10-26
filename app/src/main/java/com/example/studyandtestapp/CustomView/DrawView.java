package com.example.studyandtestapp.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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
        paint.setStrokeWidth(30);

        canvas.drawRect(100,100,600,800,paint);

    }


}
