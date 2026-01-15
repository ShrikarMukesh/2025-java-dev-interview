package com.locks.readWriteLock;

import java.util.concurrent.locks.ReadWriteLock;

public class SharedResource {
    boolean isAvailable = false;
    public void produce(ReadWriteLock lock) {
        try {
            lock.readLock().lock();
            System.out.println("Lock acquired by " + Thread.currentThread().getName());
            isAvailable = true;
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } finally {
            System.out.println("Read Lock released by " + Thread.currentThread().getName());
            lock.readLock().unlock();
        }
    }

    public void consume(ReadWriteLock lock) {
        try {
            lock.writeLock().lock();
            System.out.println("Write Lock acquired by " + Thread.currentThread().getName());
            isAvailable = false;
        }
        catch (Exception exception){
        }
        finally {
            System.out.println("Lock released by " + Thread.currentThread().getName());
            lock.writeLock().unlock();
        }
    }
}
