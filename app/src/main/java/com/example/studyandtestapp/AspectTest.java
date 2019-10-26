package com.example.studyandtestapp;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectTest {

    public static final String TAG="statistics";

    //activity打开统计
    @Pointcut("execution(* com.example.studyandtestapp.MainActivity.onCreate(..))")
    public void activityCreate(){}

    @Before("activityCreate()")
    public void afterActivityCreate(JoinPoint point){
        System.out.println("statis");
        Log.i(TAG, "afterActivityCreate: target--->"+point.getTarget());
        Log.i(TAG, "afterActivityCreate: this--->"+point.getThis());
        Log.i(TAG, "afterActivityCreate: arg-->"+point.getArgs()[0]);


    }

}
