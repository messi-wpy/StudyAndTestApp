package com.example.studyandtestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.studyandtestapp.CustomView.ItemLinearLayout;
import com.example.studyandtestapp.CustomView.LargeImageView;
import com.example.studyandtestapp.CustomView.MovableView;
import com.example.studyandtestapp.fragment.Mainfragment;

import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    private LargeImageView largeImageView;
    private SubsamplingScaleImageView imageView;
    private Button start_bt;
    private final static String TAG = "Main";
    private MovableView movableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);
        movableView = findViewById(R.id.move_view);
        movableView.setOnClickListener(v -> {
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

        });

    }
}
