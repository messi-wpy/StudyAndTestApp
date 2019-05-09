package com.example.myretrofit;

import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import okhttp3.OkHttpClient;
import okhttp3.Request;


public class RestService {

    public final static String TAG="retrofit";
    private OkHttpClient okHttpClient;
    private String baseUrl;

    private RestService(Builder builder){
        if (builder.client==null){
            okHttpClient=new OkHttpClient.Builder().build();
        }
        baseUrl=builder.baseUrl;
    }

    public static class Builder{
        private OkHttpClient client;
        private String baseUrl;

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setClient(OkHttpClient client) {
            this.client = client;
            return this;
        }

        public RestService build(){
            return new RestService(this);

        }
    }

    @SuppressWarnings("unchecked")
    public <T> T createService(final Class<T> service){
        return (T) Proxy.newProxyInstance(service.getClassLoader(),new Class<?>[]{service},
        new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                final Annotation[]annotations=method.getAnnotations();
                Log.i(TAG, "invoke: method name :"+method.getName());
                for (int i = 0; i <annotations.length ; i++) {
                    if (annotations[i] instanceof GET){
                        final GET annotation=(GET) annotations[i];
                        String url=baseUrl==null?annotation.value():baseUrl+annotation.value();
                        final Request request=new Request.Builder()
                                .url(url)
                                .get()
                                .build();
                        return okHttpClient.newCall(request);
                    }
                }
                return null;
            }
        });


    }



}
