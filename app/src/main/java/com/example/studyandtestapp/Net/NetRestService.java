package com.example.studyandtestapp.Net;

import com.example.myretrofit.annotation.Body;
import com.example.myretrofit.annotation.GET;
import com.example.myretrofit.annotation.POST;
import com.example.studyandtestapp.data.Email;

import okhttp3.Call;

public interface  NetRestService {

    @GET("http://gank.io/api/today")
    public Call gettest();


    @POST("http://work.muxixyz.com/api/v1.0/auth/login/")
    public Call postEmail(@Body Email email);
}
