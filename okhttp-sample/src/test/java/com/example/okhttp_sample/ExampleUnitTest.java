package com.example.okhttp_sample;

import com.example.okhttp_sample.retrofit.GithubRetrofit;

import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    Random random = new Random();
    int negativeCount = 0;
    @Test
    public void testOkhttp(){
        int n = 1431655764;
        int halfN = n / 2;
        System.out.println(n + ", " + halfN);
        int low = 0;
        for (int i = 0; i < 100_0000; i++) {
            int rN = random.nextInt(n);
            if (rN > 0 && rN < halfN)
                low++;
        }
        System.out.println(low);
        System.out.println(negativeCount);
    }

    private int random(int n) {
        return random.nextInt() % n;
    }

    @Test
    public void testRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GithubRetrofit service = retrofit.create(GithubRetrofit.class);
        Call<List<Object>> call = service.listRepos("square");
        try {
            List<Object> repos = call.execute().body();
            System.out.println(repos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRxJava(){
        String[] names = {"otto", "okhttp", "retrofit", "rxjava", "eventbus"};
        Observable.from(names).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
    }

    @Test
    public void testOther() {
        System.out.println("pre");
        someLogic(false);
        System.out.println("after");
    }

    private void someLogic(boolean devision) {
        if (!devision) {
            throw new IllegalArgumentException("args should not null");
        }
        System.out.println("do something");
        System.out.println("do complete");
    }
}