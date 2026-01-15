package com.locks.stampedLock;

public class Main {
    public static void main(String[] args) {
        // A single shared resource instance that will be accessed by multiple threads.
        SharedResourceStampedLockExample sharedResource = new SharedResourceStampedLockExample();

        // Create the first thread that will act as a producer.
        Thread thread1  = new Thread(() -> {
            sharedResource.produce();
        }, "Producer-Thread-1"); // Give the thread a descriptive name.

        // Create the second thread that will also try to act as a producer.
        Thread thread2  = new Thread(() -> {
            sharedResource.consume();
        }, "Consumer-Thread-2"); // Give the thread a descriptive name.

        // Start both threads. They will contend for the lock inside the produce() method.
        thread1.start();
        //thread2.start();
    }
}
