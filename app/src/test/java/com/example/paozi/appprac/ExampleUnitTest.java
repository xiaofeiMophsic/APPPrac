package com.example.paozi.appprac;

import org.junit.Test;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testEven() {
        int a = 2;
        if ((a & 2) != 0) {
            System.out.println("even");
        } else {
            System.out.println("odd");
        }

    }

    @Test
    public void testRxLaunch(){
        Observable<String> observableString = Observable.just("Hello", "world");
        Subscription subscription = observableString.subscribe(new Observer<String>() {

            @Override
            public void onCompleted() {
                System.out.println("completed");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });
    }
}