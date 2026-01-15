package com.locks.reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * This class demonstrates the use of ReentrantLock.
 *
 * Synchronized vs. ReentrantLock:
 *
 * 1.  Acquisition:
 *     - synchronized: Is a keyword and a block structure. The lock is automatically acquired and released by the JVM.
 *     - ReentrantLock: Is a class. You must manually call lock() to acquire and unlock() to release it.
 *     This is more flexible but requires careful handling (usually in a finally block).
 *
 * 2.  Interruptibility:
 *     - synchronized: A thread waiting for a synchronized block cannot be interrupted. It will wait indefinitely.
 *     - ReentrantLock: Provides lockInterruptibly(), which allows a waiting thread to be interrupted.
 *
 * 3.  Fairness:
 *     - synchronized: The lock is non-fair by default. There's no guarantee that the longest-waiting thread will get the lock next.
 *     - ReentrantLock: Can be configured to be "fair". A fair lock gives access to the longest-waiting thread, preventing starvation but reducing throughput.
 *
 * 4.  Try Lock:
 *     - synchronized: A thread will block until the lock is available. There is no way to "try" and fail if the lock is not available.
 *     - ReentrantLock: Provides tryLock(), which attempts to acquire the lock and returns immediately (true if successful, false if not). An overloaded version can timeout.
 *
 * 5.  Condition Objects:
 *     - synchronized: Relies on wait(), notify(), and notifyAll() on the object itself. These methods are inflexible as they are tied to a single condition set per object.
 *     - ReentrantLock: Provides Condition objects (via lock.newCondition()). You can have multiple conditions per lock, allowing for more complex coordination (e.g., separate conditions for "buffer is full" and "buffer is empty").
 *
 */
public class SharedResource {
    // A flag to indicate if a resource is available for consumption.
    boolean isAvailable = false;

    // A ReentrantLock instance to protect the shared resource (isAvailable).
    // This is the modern, more flexible alternative to the 'synchronized' keyword.

    /**
     * A method simulating a producer's action.
     * It acquires a lock to ensure exclusive access to the shared state (isAvailable).
     */
    public void produce(ReentrantLock lock) {


        try {
            // The critical section starts here.
            // This is where the shared state is modified.
            // Manually acquire the lock. From this point, no other thread can acquire this lock until it's released.
            lock.lock();
            System.out.println("Lock acquired by " + Thread.currentThread().getName());
            isAvailable = true;
            System.out.println("Produced resource, isAvailable is now true.");
            // Simulate some work being done while holding the lock.
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // Handle the case where the thread is interrupted while sleeping.
            Thread.currentThread().interrupt(); // It's good practice to restore the interrupted status.
            throw new RuntimeException(e);
        } finally {
            // This 'finally' block is crucial.
            // It ensures the lock is ALWAYS released, even if an exception occurs in the 'try' block.
            // Forgetting to unlock can lead to deadlocks.
            System.out.println("Lock released by " + Thread.currentThread().getName());
            lock.unlock();
        }
    }

}
