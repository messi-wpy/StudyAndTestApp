package com.example.myretrofit;

public interface NetCallback<T> {

    void onFailure(Exception e);
    void onSuccess(T data);
}

