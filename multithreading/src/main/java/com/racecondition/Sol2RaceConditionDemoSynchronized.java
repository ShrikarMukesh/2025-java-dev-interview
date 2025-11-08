package com.racecondition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class SyncCounter {
   private int count = 0;
   public synchronized void increment(){
       count++; // protected by intrinsic lock
   }

    public synchronized int getCount() {
        return count;
    }
}
public class Sol2RaceConditionDemoSynchronized {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService pool = Executors.newFixedThreadPool(10);

        SyncCounter counter = new SyncCounter();

        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) {
                System.out.println(Thread.currentThread().getName() + " ");
                counter.increment();
            }
        };

        pool.submit(task);
        pool.submit(task);

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("Expected count: 200000");
        System.out.println("Final count (synchronized): " + counter.getCount());
    }
}
