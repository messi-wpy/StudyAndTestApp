package com.example.ccnuscores;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class MyCookieJar implements CookieJar {

    public static final String TAG="cookie";
    private List<Cookie>cookieStore=new ArrayList<>();
    private int lastIndex=-1;
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.addAll(cookies);


    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        for (int i = 0; i < cookieStore.size(); i++) {
            Log.i(TAG, "load: cookie"+cookieStore.get(i));

        }
        return cookieStore;
    }
}
