package com.elevens;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Thread1 implements Runnable {
    @Override
    public void run() {
        for (int i =1; i<=25;i++){
            System.out.println(i);
        }
    }
}

class Thread2 implements Runnable {
    @Override
    public void run() {
        for (int i =26; i<=50;i++){
            System.out.println(i);
        }
    }
}

public class Interview2 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executors = Executors.newFixedThreadPool(2);

        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();

        Thread thread = new Thread(new Thread(thread1));
        thread.run();
        thread.join();
        Thread threadNew = new Thread(new Thread(thread2));
        threadNew.run();
        threadNew.join();

//        executors.submit(thread1).isDone();
//        executors.submit(thread2);

//        executors.awaitTermination(2, TimeUnit.SECONDS);
//        executors.shutdown();
    }
}
