package com.example.studyandtestapp.Net;

import android.os.Looper;

public class LooperThread extends Thread {

    public Looper myLooper;
    @Override
    public void run(){
        Looper.prepare();
        myLooper=Looper.myLooper();
        Looper.loop();
    }
}
