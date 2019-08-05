package com.example.studyandtestapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.ccnuscores.GetScorsePresenter;
import com.example.studyandtestapp.CustomView.ItemLinearLayout;
import com.example.studyandtestapp.CustomView.LargeImageView;
import com.example.studyandtestapp.CustomView.MovableView;
import com.example.studyandtestapp.data.Score;
import com.example.studyandtestapp.fragment.Mainfragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private LargeImageView largeImageView;
    private SubsamplingScaleImageView imageView;
    private Button start_bt;
    private final static String TAG = "Main";
    private MovableView movableView;
    private GetScorsePresenter scorsePresenter;
    private static final String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/BigView/";

    private Handler handler=new MainHandler(this) ;


    private static class  MainHandler extends Handler{

        WeakReference<Activity > mActivityReference;
        public MainHandler(Activity activity) {
            mActivityReference= new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ((MainActivity)mActivityReference.get()).setImage((String) msg.obj);

        }

    }

    public void setImage(String path){
        try {
            largeImageView.setFitXY(true).setImage(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        largeImageView=findViewById(R.id.large_image);
        start_bt=findViewById(R.id.load_1);
        start_bt.setOnClickListener(v -> {
           new Thread(()->{
               String path="";
               try {
                   path=saveFile(BitmapFactory.decodeStream(getAssets().open("test.png")));
               } catch (IOException e) {
                   e.printStackTrace();
               }
               Message msg=new Message();
               msg.obj=path;
            handler.sendMessage(msg);
           }).start();

        });
    }

    public static String saveFile(Bitmap bm) throws IOException {
        File dirFile = new File(ALBUM_PATH);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(ALBUM_PATH + SystemClock.currentThreadTimeMillis() +".jpeg");
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile.getAbsolutePath();
    }









    public void scoreTest(){
        movableView = findViewById(R.id.move_view);
        if (scorsePresenter==null)
            scorsePresenter=new GetScorsePresenter();
        //if (!scorsePresenter.isLogined())
        //   scorsePresenter.LoginJWC();
        movableView.setOnClickListener(v -> {
            Log.i(TAG, "onCreate: onclick");
            scorsePresenter.getScores(new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {
                    Log.i(TAG, "onCompleted: getscores");

                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, "onError: getscores error");
                    if (e instanceof HttpException){
                        Log.e(TAG, "onError: HttpException "+((HttpException)e).response().code());

                    }

                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    Log.i(TAG, "onNext: get");
                    scorsePresenter.addTime();
                    try {
                        List<Score> list=getScoreFromJson(responseBody.string());
                        for (int i = 0; i <list.size() ; i++) {
                            Log.i(TAG, "onNext: "+list.get(i).course+list.get(i).grade);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        });
    }
    public @Nullable
    List<Score> getScoreFromJson(String json) throws JSONException {
        List<Score> list = new ArrayList<>();
        JSONObject jsonRoot = new JSONObject(json);
        JSONArray items = jsonRoot.getJSONArray("items");
        if (items == null || items.length() == 0) {
            Log.i(TAG, "getScoreFromJson: item==null?" + (items == null));
            return null;
        }
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            Score score = new Score();
            score.course = item.getString("kcmc");
            if (score.course==null)
                score.course="  ";
            score.credit = item.getString("xf");
            if (score.credit==null)
                score.credit="0";
            score.grade=item.getString("cj");
            if (score.grade==null)
                score.grade="0";
            score.jxb_id=item.getString("jxb_id");
            if (score.jxb_id==null)
                score.jxb_id="  ";

            score.kcxzmc=item.getString("kcxzmc");
            list.add(score);
        }
        return list;
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        scorsePresenter.unsubscription();

    }
    public void rxjavaTest(){
        Observable.just("hello")
                .flatMap(new Func1<String, Observable<?>>() {
                    @Override
                    public Observable<?> call(String s) {
                        Log.i(TAG, "call: flatmap  -----1");
                        s = "hellp flapmap1";
                        if (s != null)
                            return Observable.error(new NullPointerException("null test"));
                        return Observable.just(s);
                    }
                }).flatMap(new Func1<Object, Observable<?>>() {


            @Override
            public Observable<?> call(Object o) {
                Log.i(TAG, "call: flatmap-----2");
                return Observable.just("33333");
            }

        }).subscribe(new Subscriber<Object>() {


            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: call");
                if (e instanceof NullPointerException)
                    Log.i(TAG, "onError: illegal   " + e.getMessage());
            }

            @Override
            public void onNext(Object o) {
                Log.i(TAG, "onNext: ");
            }
        });

    }


}
