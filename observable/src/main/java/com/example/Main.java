package com.example;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by paozi on 2016/4/13.
 */
public class Main {

    public static void main(String[] args) {
        double d = 0.1;
        System.out.println(2.00f-1.10f);
        new Main().method("");
    }

    public void method(String s){
        if (null == s || "".equals(s)) {
            throw new IllegalArgumentException("asdfasdf");
        }

    }

    public enum Type {
        TYPE_1,
        TYPE_2,
        TYPE_3
    }
}
