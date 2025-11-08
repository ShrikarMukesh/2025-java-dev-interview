package com.basic;

public class MyRunnable implements Runnable{

    @Override
    public void run(){
        for (int i=0;i<5;i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new MyRunnable());
        thread.start();

        Thread thread1 = new Thread(()-> System.out.println("Lambda Thread running"));
    }
}
