package com.locks.semaphoreLock;

public class Main {
    public static void main(String[] args) {
        SharedResourceSemaphoreLockExample sharedResource = new SharedResourceSemaphoreLockExample();
        Thread thread1  = new Thread(() -> {
            sharedResource.produce();
        }, "Producer-Thread-1");

        Thread thread2  = new Thread(() -> {
            sharedResource.produce();
        }, "Producer-Thread-2");

        Thread thread3  = new Thread(() -> {
            sharedResource.produce();
        }, "Producer-Thread-3");

        Thread thread4  = new Thread(() -> {
            sharedResource.produce();
        }, "Producer-Thread-4");

        // Start both threads. They will contend for the lock inside the produce() method.
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
