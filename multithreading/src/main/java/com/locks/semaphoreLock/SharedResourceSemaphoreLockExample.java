package com.locks.semaphoreLock;

import java.util.concurrent.Semaphore;

public class SharedResourceSemaphoreLockExample {
    Semaphore semaphoreLock = new Semaphore(2);

    boolean isAvailable = false;
    public void produce() {

        try {
            semaphoreLock.acquire();
            System.out.println("Lock acquired by " + Thread.currentThread().getName());
            isAvailable = true;
            Thread.sleep(3000);
        } catch (InterruptedException e) {

            Thread.currentThread().interrupt(); // It's good practice to restore the interrupted status.
            throw new RuntimeException(e);
        } finally {
            System.out.println("Lock released by " + Thread.currentThread().getName());
            semaphoreLock.release();
        }
    }
}
