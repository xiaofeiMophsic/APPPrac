package com.example.okhttp_sample;

import org.junit.Test;

import java.util.List;

/**
 * 作者：xiaofei
 * 日期：2016/8/1
 * 邮箱：paozi.xiaofei.123@gmail.com
 */
public class PECSTest {
    public PECSTest(){}
    @Test
    public <T> void copy(List<? super T> dist, List<? extends T> src){
        for (int i = 0, size = src.size(); i < size; i++) {
           dist.add(i, src.get(i));
        }
    }
}
