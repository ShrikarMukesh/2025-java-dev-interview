package com.racecondition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// The original, non-thread-safe Counter class
class Counter {
    private int count = 0;

    public void increment() {
        count++; // Not atomic!
    }

    public int getCount() {
        return count;
    }
}
public class RaceConditionDemo {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Counter counter = new Counter();
        Runnable task1 = ()-> {
            for (int i=0;i<100000;i++){
                System.out.println(Thread.currentThread().getName() + " ");
                counter.increment();
            }
        };

        Runnable task2 = ()-> {
            for (int i=0;i<100000;i++){
                System.out.println(Thread.currentThread().getName() + " ");
                counter.increment();
            }
        };

        executorService.submit(task1);
        executorService.submit(task2);

        // Wait for the tasks to complete
        executorService.shutdown();
        executorService.awaitTermination(6, TimeUnit.SECONDS);

        System.out.println("Expected count: 200000");
        System.out.println("Final count is: " + counter.getCount());
    }
}
