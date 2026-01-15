package com.locks.stampedLock; // Defines the package for the class.


import java.util.concurrent.locks.StampedLock; // Imports the StampedLock class from Java's concurrent locks package.

// This class demonstrates the use of StampedLock for controlling access to a shared resource.
public class SharedResourceStampedLockExample {

    int a = 10; // A shared integer variable, initialized to 10.
    StampedLock lock = new StampedLock(); // An instance of StampedLock to manage access to the shared resource 'a'.

    // This method demonstrates an optimistic read operation.
    // It's intended to be a "producer" but the logic is more like a reader that tries to update optimistically.
    public void produce() {
       // Attempt to get a "stamp" for an optimistic read. This is non-blocking and does not acquire a real lock.
       // It returns a stamp (a long value) that represents the lock's state. A stamp of 0 means a write lock is held.
       long stamp = lock.tryOptimisticRead();
       try{
           System.out.println("Taken optimistic lock stamp. Trying to read and update.");
           // Here, we optimistically perform work assuming no writes will interfere.
           // NOTE: In a real optimistic read, you would typically only read data, not write it.
           // This example modifies 'a' to demonstrate the rollback mechanism.
           a = 11;
           // Simulate some work being done, which takes time.
           Thread.sleep(6000);
           // After the work, we validate the stamp.
           // lock.validate(stamp) checks if any write lock has been acquired since the stamp was obtained.
           if (lock.validate(stamp)){
               // If the stamp is still valid, it means no write lock interfered. The optimistic assumption was correct.
               System.out.println("Update of 'a' was successful without lock interference.");
           } else {
               // If the stamp is invalid, it means a write lock was acquired, and our read data might be inconsistent.
               // We must handle this by rolling back any work done based on the optimistic read.
               System.out.println("A write lock was acquired. Rolling back the work.");
               a = 10; // Rollback the change to 'a'.
           }
       } catch (InterruptedException e) {
           // Handle the case where the thread is interrupted while sleeping.
           throw new RuntimeException(e);
       }
    }

    // This method demonstrates an exclusive write lock operation.
    // It's named "consume" but acts as a writer that exclusively locks and modifies the shared resource.
    public void consume() {
        // Acquire an exclusive write lock. This is a blocking call.
        // It waits until no other read or write locks are held, then returns a stamp for unlocking.
        long stamp = lock.writeLock();
        System.out.println("Write lock acquired by: " + Thread.currentThread().getName());
        try{
            // Inside the write lock, we have exclusive access to the shared resource.
            System.out.println("Performing write operation.");
            a = 9; // Modify the shared variable 'a'.
        }finally {
            // It is crucial to release the write lock in a 'finally' block to prevent deadlocks.
            // The stamp returned by writeLock() must be used to unlock it.
            lock.unlockWrite(stamp);
            System.out.println("Write lock released by: " + Thread.currentThread().getName());
        }
    }

    // A main method is needed to see this in action.
    public static void main(String[] args) {
        SharedResourceStampedLockExample resource = new SharedResourceStampedLockExample();

        // A thread that tries to consume (write)
        Thread consumerThread = new Thread(resource::consume, "ConsumerThread");

        // A thread that tries to produce (optimistic read)
        Thread producerThread = new Thread(resource::produce, "ProducerThread");

        producerThread.start(); // Start the optimistic reader
        try {
            Thread.sleep(1000); // Give the producer a head start before the writer starts.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        consumerThread.start(); // Start the writer. This will acquire a write lock, causing the optimistic read's validation to fail.
    }
}
