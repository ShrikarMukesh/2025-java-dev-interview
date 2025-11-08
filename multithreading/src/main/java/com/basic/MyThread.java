package com.basic;

public class MyThread extends Thread {

    @Override
    public void run(){

        for (int i=0;i<5;i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
       MyThread myThread = new MyThread();
        //start() → creates a new thread and calls run() asynchronously.
        //run() directly → executes in the current thread (not new one). Main Thread
       //myThread.start();
       myThread.run();
    }
}
