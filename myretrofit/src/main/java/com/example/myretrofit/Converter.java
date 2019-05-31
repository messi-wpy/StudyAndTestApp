package com.example.myretrofit;

import androidx.annotation.Nullable;

import java.lang.reflect.Type;

import okhttp3.RequestBody;

public interface Converter<F,T> {

    @Nullable
    T convert(F value);

    abstract class Factory{
        public  @Nullable Converter<?, RequestBody>requestBodyConverter(Type type){
            return null;
        }

    }
}
