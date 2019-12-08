package com.example.studyandtestapp.Net;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class AsyncTest extends AsyncTask<String,Integer,String> {
    public static final String TAG="AsyncTask";

    public AsyncTest(Looper looper){
        super();
    }
    @Override
    protected String doInBackground(String... strings) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publishProgress(2);

        return "finish";
    }

    @Override
    protected void onPreExecute() {
        Log.i(TAG, "onPreExecute: start");
    }

    @Override
    protected void onPostExecute(String s) {
        Log.i(TAG, "onPostExecute: "+s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.i(TAG, "onProgressUpdate: "+values[0]);
    }
}
