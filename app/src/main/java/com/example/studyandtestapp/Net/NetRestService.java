package com.example.studyandtestapp.Net;

import com.example.myretrofit.GET;

import okhttp3.Call;

public interface  NetRestService {

    @GET("http://gank.io/api/today")
    public Call gettest();
}
