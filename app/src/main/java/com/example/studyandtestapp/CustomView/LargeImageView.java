package com.example.studyandtestapp.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;

public class LargeImageView  extends View implements View.OnTouchListener {

    //该对象可以实现图片的局部显示
    private BitmapRegionDecoder mDecoder;
    private int width,hight;
    private Rect mRect;
    private static final BitmapFactory.Options option=new BitmapFactory.Options();
    private  boolean isFitXY;
    private float currentImageX,currentImageY;
    private float imageWidth,imageHeight;
    private float lastX,lastY;
    private float scale=1;
    Bitmap bmp;
    private Matrix matrix=new Matrix();
    private final static String TAG="TAG";

    private Scroller mScroller;
    private GestureDetector mGestureDetector;

    public LargeImageView(Context context){
        this(context,null);
    }
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
        mScroller=new Scroller(context);
        mGestureDetector=new GestureDetector(context,new GestureImpl());
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("TAG", "onDraw: called");
        if (mDecoder!=null) {
            option.inBitmap=bmp;
           if( isFitXY){
                matrix.setScale(scale,scale);
                bmp = mDecoder.decodeRegion(mRect, option);
               Log.i("TAG", "onDraw: "+bmp.getByteCount());
               //局部图片占4.7M，缩小了一半内存!
               canvas.drawBitmap(bmp,matrix,null);
            }else {
               bmp = mDecoder.decodeRegion(mRect, option);
               Log.i("TAG", "onDraw: " + bmp.getByteCount());
               //局部图片占4.7M，缩小了一半内存!
               canvas.drawBitmap(bmp, 0, 0, null);
           }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mRect.left=0;
        width=mRect.right=getMeasuredWidth();
        mRect.top=0;
        hight=mRect.bottom=getMeasuredHeight();
        currentImageX=0;
        currentImageY=0;


    }


    @Override
    public boolean onTouch(View v,MotionEvent event) {


        //改为让gestureDetector来处理触摸事件
        return mGestureDetector.onTouchEvent(event);
    }

    public LargeImageView setImage(InputStream image){
        try {

            mDecoder=BitmapRegionDecoder.newInstance(image,false);

            //这里不能用option来获取图片的宽和高，因为经过BitmapRegionDecoder类处理过后的inputstream不能在获取的其信息，自动返回-1
            imageHeight=mDecoder.getHeight();
            imageWidth=mDecoder.getWidth();

            //先压缩图片
            int insamplesize=1;
            while (imageWidth>1.6*width) {
               imageWidth /= 2;
               insamplesize*=2;
            }
            option.inMutable=true;
            option.inPreferredConfig = Bitmap.Config.RGB_565;
            option.inSampleSize=insamplesize;
            Log.i(TAG, "setImage: ------------"+imageWidth+"   "+imageHeight);
            // TODO: 19-3-19 添加铺满放大缩小选项
            if (isFitXY){
                scale=width/imageWidth;
                imageHeight+=hight-(hight/scale);
                Log.i(TAG, "setImage: ----------"+imageHeight);

            }

            requestLayout();
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

    @Override
    public void computeScroll(){
        if (mScroller.isFinished()){
            return;
        }
        if (mScroller.computeScrollOffset()){
            mRect.top=mScroller.getCurrY();
            mRect.bottom=mRect.top+hight;
            invalidate();
        }

    }

  private class GestureImpl implements GestureDetector.OnGestureListener{


      @Override
      public boolean onDown(MotionEvent e) {
          if (!mScroller.isFinished()){
              mScroller.forceFinished(true);
          }
          return true;
      }

      @Override
      public void onShowPress(MotionEvent e) {

      }

      @Override
      public boolean onSingleTapUp(MotionEvent e) {
          return false;
      }

      /**
       * 这个方法关系到滑动时是否感觉卡顿，不流畅
       * @param e1  是down的事件
       * @param e2   是滑动期间的这次事件
       * @param distanceX   这次滑动和上一次last滑动之间的距离
       * @param distanceY    这次滑动和上一次last滑动之间的距离
       * @return   这个事件是否被consumed
       */

      @Override
      public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
          mRect.offset(0,(int)distanceY);

          //限制边界
          if (mRect.bottom>imageHeight){
              mRect.bottom=(int)imageHeight;
              mRect.top=mRect.bottom-hight;

          }
          if (mRect.top<0){
              mRect.top=0;
              mRect.bottom=hight;
          }
          invalidate();
          return false;
      }

      @Override
      public void onLongPress(MotionEvent e) {

      }

      //速度
      @Override
      public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            mScroller.fling(0,mRect.top,0,(int)-velocityY,0,0,0,(int)imageHeight-hight);

          return false;
      }
  }






}
