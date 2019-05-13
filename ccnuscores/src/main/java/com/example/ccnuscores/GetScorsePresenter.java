package com.example.ccnuscores;

import android.widget.Scroller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GetScorsePresenter {

    private CcnuServices clientWithRetrofit;
    public GetScorsePresenter(){
            clientWithRetrofit=SingleRetrofit.getClient().create(CcnuServices.class);

    }

    //登录教务处获取cookie
    public void LoginJWC(){
        clientWithRetrofit.firstLogin()
                          .subscribeOn(Schedulers.io())
                          .flatMap(new Func1<Response<ResponseBody>, Observable<ResponseBody>>() {
                              @Override
                              public Observable<ResponseBody> call(Response<ResponseBody> response) {
                                  if (response.code()!=200)
                                      return Observable.error(new HttpException(response));
                                  String[]params=null;
                                  try {
                                    params= getWordFromHtml(response.body().string());
                                  } catch (IOException e) {
                                      e.printStackTrace();
                                      return Observable.error(e);
                                  }
                                    if (params==null)
                                        return Observable.error(new NullPointerException("first html get words wrong"));

                                  return clientWithRetrofit.performCampusLogin();
                              }
                          })


    }


    /**
     * 匹配：
     * <input type="hidden" name="lt" value="LT-31315-O4Nt1gZeHUSnmzr4DALQwyn3xNyir6-account.ccnu.edu.cn" />
     * <input type="hidden" name="execution" value="e1s1" />
     * @param html
     * @return string[] length=2,string[0]=lt,string[1]=execution
     */
    public String [] getWordFromHtml(String html){
        String[] res=new String[2];
        Pattern ltPattern=Pattern.compile("name=\"lt\" value=\"(.+?)\" />");
        Pattern executionP=Pattern.compile("name=\"execution\" value=\"(.+?)\"");
        Matcher m1=ltPattern.matcher(html);
        Matcher m2=executionP.matcher(html);
        if (m1.find())
            res[0]=m1.group(1);
        else res[0]=null;
        if (m2.find())
            res[1]=m2.group(1);
        else res[1]=null;
        return res;
    }



}
