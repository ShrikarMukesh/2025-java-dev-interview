package com.threadpoolExceutor;

import java.util.concurrent.*;

public class ThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService executorService = new
                ThreadPoolExecutor(3,
                5,
                3000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(4) {
        });
    }
}
