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
        //只保存一个JSESSIONID
        int indexOfJSESSIONID=-1;

        for (int i = 0; i < cookies.size(); i++) {
            Log.i(TAG, "saveFromResponse: cookie   "+cookies.get(i)+"domain  "+cookies.get(i).domain());
            if (cookies.get(i).name().equals("JSESSIONID")) {
                indexOfJSESSIONID = i;
            }
            else cookieStore.add(cookies.get(i));
        }
        if (indexOfJSESSIONID!=-1){
            if (lastIndex==-1){
                cookieStore.add(cookies.get(indexOfJSESSIONID));
                lastIndex=cookieStore.size()-1;
            }else
            cookieStore.set(lastIndex,cookies.get(indexOfJSESSIONID));
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        for (int i = 0; i < cookieStore.size(); i++) {
            Log.i(TAG, "load: cookie"+cookieStore.get(i));

        }
        Cookie first=new Cookie.Builder()
                .name("BIGipServerpool_jwc_xk")
                .value("156281024.20480.0000")
                .build();
        Cookie second=new Cookie.Builder()
                .name("JSESSIONID")
                .value("F96F8E052B5F82BFE49E8ACF55D1858E")
                .build();
        cookieStore.add(first);
        cookieStore.add(second);
        return cookieStore;
    }
}
