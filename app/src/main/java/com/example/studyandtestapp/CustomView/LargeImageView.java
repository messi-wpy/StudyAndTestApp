package com.example.studyandtestapp.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;

public class LargeImageView  extends View {

    //该对象可以实现图片的局部显示
    private BitmapRegionDecoder mDecoder;
    private int ImageWidth,ImageHight;
    private Rect mRect;
    private static final BitmapFactory.Options option=new BitmapFactory.Options();
    private  boolean isFitXY;
    private float currentImageX,currentImageY;
    private float imageWidth,imageHeight;
    private float lastX,lastY;
    /**
     *
     * @param context
     * @param attrs 该set就是xml里声明的组件属性
     */
    public LargeImageView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        //设置为一个像素点3byte
        option.inPreferredConfig= Bitmap.Config.RGB_565;
        mRect=new Rect();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("TAG", "onDraw: called");
        if (mDecoder!=null) {
            Bitmap bmp = mDecoder.decodeRegion(mRect, option);
            Log.i("TAG", "onDraw: "+bmp.getByteCount());
            //局部图片占4.7M，缩小了一半内存!
            canvas.drawBitmap(bmp,0,0,null);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mRect.left=0;
        mRect.right=getMeasuredWidth();
        mRect.top=0;
        mRect.bottom=getMeasuredHeight();
        currentImageX=0;
        currentImageY=0;


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float moveX=event.getX()-lastX;
        float moveY=lastY-event.getY();
        int action=event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN: {
                lastX = event.getX();
                lastY = event.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //水平方向的移动
                boolean movedX, movedY;
                movedX = movedY = false;

                // TODO: 19-3-19 优化体验，判断滑动距离来优化 
                if (mRect.right+moveX <= imageWidth&&mRect.right+moveX>=0&&mRect.left+moveX>=0) {
                    currentImageX += moveX;
                    mRect.left = (int) currentImageX;
                    mRect.right+= (int)moveX;
                    movedX = true;
                }

                if (mRect.bottom+moveY <= imageHeight&& mRect.bottom+moveY>=0&& mRect.top+moveY>=0) {
                    currentImageY += moveY;
                    mRect.top= (int) currentImageY;
                    mRect.bottom+=(int)moveY;
                    movedY = true;
                }
                if (movedX || movedY) {
                    //call it to re draw
                    invalidate();
                }
                break;
            }

        }


        return true;
    }

    public LargeImageView setImage(InputStream image){
        try {
            mDecoder=BitmapRegionDecoder.newInstance(image,false);

            //这里不能用option来获取图片的宽和高，因为经过BitmapRegionDecoder类处理过后的inputstream不能在获取的其信息，自动返回-1
            imageHeight=mDecoder.getHeight();
            imageWidth=mDecoder.getWidth();
            // TODO: 19-3-19 添加铺满放大缩小选项
            if (isFitXY){



            }


            invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                image.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;

    }

    public LargeImageView setFitXY(boolean fitXY){
        isFitXY=fitXY;
        return this;
    }










}
