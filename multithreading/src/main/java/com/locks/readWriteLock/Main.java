package com.locks.readWriteLock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        Thread thread1 = new Thread(()->{
            sharedResource.produce(readWriteLock);
        }, "Thread1");

        Thread thread2 = new Thread(()->{
            sharedResource.produce(readWriteLock);
        }, "Thread2");

        Thread thread3 = new Thread(()->{
            sharedResource.consume(readWriteLock);
        }, "Thread3");

        thread1.start();
        thread2.start();
        thread3.start();
    }

}
