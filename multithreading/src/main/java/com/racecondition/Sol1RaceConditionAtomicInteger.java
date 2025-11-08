package com.racecondition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class AtomicCounter {

    private AtomicInteger count = new AtomicInteger(0);
    public void increment(){
       count.incrementAndGet(); // atomic operation
    }

    public int getCount() {
        return count.get();
    }
}
public class Sol1RaceConditionAtomicInteger {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        AtomicCounter counter = new AtomicCounter();

        Runnable task1 = () -> {
            for (int i = 0; i < 100_000; i++) {
                counter.increment();
            }
        };
        Runnable task2 = () -> {
            for (int i = 0; i < 100_000; i++) {
                counter.increment();
            }
        };

        pool.submit(task1);
        pool.submit(task2);

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("Expected count: 200000");
        System.out.println("Final count (AtomicInteger): " + counter.getCount());
    }
}
