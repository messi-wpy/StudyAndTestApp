package com.example.studyandtestapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.studyandtestapp.CustomView.LargeImageView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private LargeImageView largeImageView;
    private SubsamplingScaleImageView imageView;
    private Button start_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //largeImageView=findViewById(R.id.large_image);
       largeImageView=findViewById(R.id.large_image);
        start_bt=findViewById(R.id.load_1);
       /* start_bt.setOnClickListener(v -> {
        imageView.setImage(ImageSource.asset("calander.png"));

        });*/


        start_bt.setOnClickListener(v->{
            try {
                String[] files=getAssets().list("");
                InputStream in=getAssets().open(files[0]);
                largeImageView.setFitXY(true)
                                .setImage(in);
                //Bitmap bmp=BitmapFactory.decodeStream(in);
                //9.4M 全部加载,也证明了图片占用内存= 分辨率*一个像素点占的内存（ARGB--4,RGB---3）
                //imageView.setImageBitmap(bmp);
                //Log.i("TAG", "onCreate: "+bmp.getByteCount());
            } catch (IOException e) {
                e.printStackTrace();
            }


        });
    }
}
