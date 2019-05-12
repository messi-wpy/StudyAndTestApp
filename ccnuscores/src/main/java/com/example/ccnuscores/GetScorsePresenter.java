package com.example.ccnuscores;

import android.widget.Scroller;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GetScorsePresenter {

    private CcnuServices clientWithRetrofit;
    public GetScorsePresenter(){
            clientWithRetrofit=SingleRetrofit.getClient().create(CcnuServices.class);

    }

    //登录教务处获取cookie
    public void LoginJWC(){
        clientWithRetrofit.firstLogin()
                          .subscribeOn(Schedulers.io())
                          .flatMap(new Func1<ResponseBody, Observable<ResponseBody>>() {
                              @Override
                              public Observable<ResponseBody> call(ResponseBody responseBody) {

                                  return clientWithRetrofit.performCampusLogin();
                              }
                          })


    }

    public void getWordFromHtml(){}



}
