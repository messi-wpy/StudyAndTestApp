package com.example.ccnuscores;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;

public class MyHttpLogInterceptor implements Interceptor {
    private final static String TAG="MyHttpLogInterceptor";
    private  final Charset UTF8 = Charset.forName("UTF-8");
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        String url=request.url().toString();
        String method=request.method();
        Buffer buffer=new Buffer();
        Log.i(TAG, "------> "+method+"  "+url);
        Headers headers=request.headers();

        for (int i = 0; i <headers.size() ; i++) {
            Log.i(TAG, headers.name(i)+" : "+headers.value(i));
        }

        if (request.body()!=null) {
            RequestBody requestBody=request.body();
            request.body().writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            Log.i(TAG, "request body\n"+buffer.readString(charset));
        }
        Log.i(TAG, "------> END "+method);
        Response response=chain.proceed(request);
        Log.i(TAG, "<-----"+response.code()+"  "+response.request().url().toString());
        headers=response.headers();
        for (int i = 0; i <headers.size() ; i++) {
            Log.i(TAG, headers.name(i)+" : "+headers.value(i));
        }

        if (!response.request().url().toString().contains("https://account.ccnu.edu.cn/cas/login")){
            Log.i(TAG, "<-----responseBody:\n"+response.body().string());


        }
        Log.i(TAG, "<----END "+method);
        return response;
    }
}
