package com.example.okhttp_sample.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 作者：xiaofei
 * 日期：2016/7/28
 * 邮箱：paozi.xiaofei.123@gmail.com
 */
public interface GithubRetrofit {
    @GET("users/{user}/repos")
    Call<List<Object>> listRepos(@Path("user")String user);
}
