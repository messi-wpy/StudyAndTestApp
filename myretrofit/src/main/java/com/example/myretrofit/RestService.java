package com.example.myretrofit;

import android.util.Log;

import com.example.myretrofit.annotation.Body;
import com.example.myretrofit.annotation.GET;
import com.example.myretrofit.annotation.POST;
import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class RestService {

    public final static String TAG="retrofit";
    private OkHttpClient okHttpClient;
    private String baseUrl;
    private RestService(Builder builder){
        if (builder.client==null){
            okHttpClient=new OkHttpClient.Builder()
                    .build();
        }else
            okHttpClient=builder.client;
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
                    else if(annotations[i] instanceof POST){
                        final POST annotation=(POST) annotations[i];
                        String url=baseUrl==null?annotation.value():baseUrl+annotation.value();
                        return post(method,args,url);
                    }
                }
                Log.e(TAG, "invoke: not find GET methods return null" );
                return null;
            }
        });


    }

    private Gson gson = new Gson();
    private static final MediaType MEDIA_TYPE = MediaType.get("application/json");

    public Call post(Method method,Object[] objects,String url){
        Log.i(TAG, "post: begin");
       Annotation[][] annotations=method.getParameterAnnotations();
       if (annotations.length==0)
           return null;
        for (int i = 0; i <annotations.length ; i++) {
            Annotation[] a=annotations[i];
            for (int j = 0; j <a.length ; j++) {
                if ( a[j] instanceof Body){
                    Log.i(TAG, "post: class name   "+method.getParameterTypes()[i].getSimpleName());
                    String json=gson.toJson(objects[i],method.getParameterTypes()[i]);
                    final  Request request=new Request.Builder()
                            .url(url)
                            .post(RequestBody.create(MEDIA_TYPE,json))
                            .build();

                    return okHttpClient.newCall(request);
                }
            }
        }
        return null;


    }




}
