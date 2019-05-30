package com.example.myretrofit;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import okhttp3.RequestBody;

public class GsonConverterFactory extends Converter.Factory{

    private final Gson gson;

    private GsonConverterFactory(Gson gson){
        if (gson==null)
            throw new NullPointerException("gosn == null");
        this.gson=gson;
    }

    public static GsonConverterFactory create(){
        return create(new Gson());
    }

    public static GsonConverterFactory create(Gson gson){
        return new GsonConverterFactory(gson);
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter() {

    }

    final static class GsonRequestBodyCOnverter<T> implements Converter<T,>
}
