package com.example.ccnuscores;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingleRetrofit {

    private volatile static Retrofit client=null;
    public static Retrofit getClient(){
        if (client ==null){
            synchronized (SingleRetrofit.class) {
                if (client == null) {
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .cookieJar(new MyCookieJar())
                            .addInterceptor(interceptor)
                            .build();

                    client=new Retrofit.Builder()
                            .client(okHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }

        }

    return client;
    }
}
