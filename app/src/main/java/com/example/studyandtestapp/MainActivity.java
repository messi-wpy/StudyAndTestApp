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

public class MainActivity extends AppCompatActivity {

    private LargeImageView largeImageView;
    private SubsamplingScaleImageView imageView;
    private Button start_bt;
    private final static String TAG="Main";
    private MovableView movableView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);
        movableView=findViewById(R.id.move);
        movableView.setOnClickListener(v->{
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content,new Mainfragment())
                    .commit();

        });

    }

}
