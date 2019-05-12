package com.example.ccnuscores;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface CcnuServices {

    @POST("https://account.ccnu.edu.cn/cas/login;jsessionid={jsession}")
    @FormUrlEncoded
    Observable<ResponseBody> performCampusLogin(@Path("jsession") String jsession, @Field("username")
            String usrname, @Field("password") String password
            , @Field("lt") String valueOfLt, @Field("execution") String valueOfExe,
                                                @Field("_eventId") String submit, @Field("submit") String login);

    //教务系统的登录
    //需要携带cookie cookie没有放在header里面
    @GET("http://xk.ccnu.edu.cn/ssoserver/login?ywxt=jw&url=xtgl/index_initMenu.html")
    Observable<ResponseBody> performSystemLogin();


    //account.ccnu.edu.cn 先从这个进行统一身份认证
    @GET("https://account.ccnu.edu.cn/cas/login")
    Observable<ResponseBody>firstLogin();
}
