package com.example.studyandtestapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.myretrofit.NetCallback;
import com.example.myretrofit.RestService;
import com.example.studyandtestapp.CustomView.LargeImageView;
import com.example.studyandtestapp.Net.NetRestService;
import com.example.studyandtestapp.fragment.Mainfragment;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private LargeImageView largeImageView;
    private SubsamplingScaleImageView imageView;
    private Button start_bt;
    private RestService restService;
    private static Gson gson=new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_bt=findViewById(R.id.load_1);
        start_bt.setText("get");
        start_bt.setOnClickListener(v->{
            getRequestTest();
        });

    }

    public void getRequestTest(){
        if (restService==null)
            restService=new RestService.Builder().build();

        restService.createService(NetRestService.class)
                .gettest()
                .enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(RestService.TAG, "onResponse: success");
                //...gson解析数据
            }
        });

    }

}
