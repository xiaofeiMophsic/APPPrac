package com.example.java.rxjava;

import com.example.Main;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by paozi on 2016/4/13.
 */
public class RxJavaTest {

    public static void main(String[] args) {
        /*
        Observable<String> observable = Observable.create(

                (Subscriber<? super String> subscriber) -> {
                    subscriber.onNext("hello");
                    subscriber.onNext("world");
                    subscriber.onCompleted();
                }
        );

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("mission completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("mission failed");
            }

            @Override
            public void onNext(String s) {
                System.out.println("mission success:" + s);
            }
        };

        observable.subscribe(subscriber);
        */
        Observable<String> observable2 = Observable.just("hello");
        observable2.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("mission success:" + s);
            }
        });

        Observable.just("hello", "world")
                .map(s1 -> s1.hashCode())
                .map(i1 -> i1.toString())
                .subscribe(s -> System.out.println("mission success:" + s));

    }
}
