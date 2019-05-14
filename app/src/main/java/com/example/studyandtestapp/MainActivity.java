package com.example.studyandtestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.ccnuscores.GetScorsePresenter;
import com.example.studyandtestapp.CustomView.ItemLinearLayout;
import com.example.studyandtestapp.CustomView.LargeImageView;
import com.example.studyandtestapp.CustomView.MovableView;
import com.example.studyandtestapp.fragment.Mainfragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import okhttp3.ResponseBody;
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
    private static GetScorsePresenter scorsePresenter;
    private Subscription loginSubscription;
    private Date date=new Date();
    private int time=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);
        movableView = findViewById(R.id.move_view);
        if (scorsePresenter==null)
            scorsePresenter=new GetScorsePresenter();
        if (!scorsePresenter.isLogined())
            loginSubscription=scorsePresenter.LoginJWC();
        movableView.setOnClickListener(v -> {
            Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    while (!loginSubscription.isUnsubscribed()){
                        Log.i(TAG, "call: wait");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    subscriber.onNext("start");
                }
            }).subscribeOn(Schedulers.newThread())
                    .flatMap(new Func1<String, Observable<ResponseBody>>() {
                        @Override
                        public Observable<ResponseBody> call(String s) {
                            return scorsePresenter.getClientWithRetrofit().getScores("2018","3",false,String.valueOf(date.getTime()),15,1,"","asc", time);
                        }
                    }).subscribe(new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {
                    Log.i(TAG, "onCompleted: getscores");
                }

                @Override
                public void onError(Throwable e) {
                    Log.i(TAG, "onError: getscores error");
                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    Log.i(TAG, "onNext: get");
                }
            });

        });

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
