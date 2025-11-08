package com.racecondition;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

class LockCounter {
    private int count = 0;
    private final ReentrantLock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
}

public class Sol3RaceConditionDemoReentrantLock {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        LockCounter counter = new LockCounter();

        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) {
                counter.increment();
            }
        };

        pool.submit(task);
        pool.submit(task);

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("Expected count: 200000");
        System.out.println("Final count (ReentrantLock): " + counter.getCount());
    }
}
