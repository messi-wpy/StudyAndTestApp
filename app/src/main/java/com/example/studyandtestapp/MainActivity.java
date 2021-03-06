package com.example.studyandtestapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.ccnuscores.GetScorsePresenter;
import com.example.studyandtestapp.CustomView.ItemLinearLayout;
import com.example.studyandtestapp.CustomView.LargeImageView;
import com.example.studyandtestapp.CustomView.MovableView;
import com.example.studyandtestapp.CustomView.TitleBar;
import com.example.studyandtestapp.Net.AsyncTest;
import com.example.studyandtestapp.Net.LooperThread;
import com.example.studyandtestapp.data.Score;
import com.example.studyandtestapp.fragment.Mainfragment;
import com.example.studyandtestapp.largeImage.LargeImageViewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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
    private ListView one;
    private ListView two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_view_test);
        TitleBar titleBar=findViewById(R.id.titlebar);
        new Thread(new Runnable() {
            @Override
            public void run() {
               AsyncTest test=new AsyncTest();
               test.execute("aaa");
            }
        }).start();


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
//        scorsePresenter.unsubscription();

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
