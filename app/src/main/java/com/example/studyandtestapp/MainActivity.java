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


import com.example.myretrofit.NetCallback;
import com.example.myretrofit.RestService;
import com.example.studyandtestapp.CustomView.LargeImageView;
import com.example.studyandtestapp.Net.NetRestService;
import com.example.studyandtestapp.data.Email;

import com.example.studyandtestapp.fragment.Mainfragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;


import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends AppCompatActivity {

    private LargeImageView largeImageView;
    private SubsamplingScaleImageView imageView;
    private Button start_bt;
    private final static String TAG = "Main";
    private MovableView movableView;
    private GetScorsePresenter scorsePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);
        movableView = findViewById(R.id.move_view);
        movableView.setOnClickListener(v -> {
            if (scorsePresenter==null)
                scorsePresenter=new GetScorsePresenter();
            scorsePresenter.LoginJWC();

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

        Email email=new Email();
        email.setEmail("904315105@qq.com");
        restService.createService(NetRestService.class)
                .postEmail(email)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i(RestService.TAG, "onFailure: ");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i(RestService.TAG, "onResponse: post success");
                    }
                });

    }
}
