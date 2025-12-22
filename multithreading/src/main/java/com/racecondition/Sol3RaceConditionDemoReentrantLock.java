package com.racecondition;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @class LockCounter
 * @definition This class provides a thread-safe counter using a ReentrantLock.
 * A ReentrantLock is a mutual exclusion lock, similar to the synchronized keyword, but with more flexibility.
 * It allows a thread to acquire the same lock multiple times without causing a deadlock.
 *
 * @interview_comment
 * ReentrantLock is part of the java.util.concurrent.locks package and provides more features than synchronized blocks.
 * Key features include:
 * - tryLock(): Attempt to acquire a lock without blocking.
 * - lockInterruptibly(): Acquire a lock unless the thread is interrupted.
 * - Timed lock acquisition: Attempt to acquire a lock for a specific duration.
 * - Fairness policy: The lock can be configured to grant access to the longest-waiting thread.
 *
 * Using a finally block to call unlock() is a critical best practice to ensure the lock is always released, even if an exception occurs.
 */
class LockCounter {
    // The counter variable, which is not thread-safe by itself.
    private int count = 0;
    // A ReentrantLock to protect access to the count variable.
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * @method increment
     * @definition Increments the counter in a thread-safe manner.
     * It acquires the lock before incrementing the count and releases it in a finally block.
     */
    public void increment() {
        // Acquire the lock. If the lock is held by another thread, this thread will block.
        lock.lock();
        try {
            // This is the critical section. Only one thread can execute this at a time.
            count++;
        } finally {
            // Release the lock. This is in a finally block to ensure it's always executed.
            lock.unlock();
        }
    }

    /**
     * @method getCount
     * @definition Returns the current value of the counter in a thread-safe manner.
     * It acquires the lock before reading the count to ensure a consistent view of the data.
     * @return The current value of the counter.
     */
    public int getCount() {
        // Acquire the lock to ensure we read the most up-to-date value.
        lock.lock();
        try {
            // Return the current count.
            return count;
        } finally {
            // Release the lock.
            lock.unlock();
        }
    }
}

/**
 * @class Sol3RaceConditionDemoReentrantLock
 * @definition This class demonstrates how to solve a race condition using a ReentrantLock.
 * It creates two tasks that concurrently increment a shared counter. The ReentrantLock ensures that
 * the increments are atomic, preventing lost updates and ensuring the final count is correct.
 *
 * @interview_comment
 * This example showcases a common solution for race conditions. By using ReentrantLock, we ensure mutual exclusion
 * for the critical section (the increment operation). This is a more explicit and flexible way to handle locking
 * compared to the synchronized keyword. Interviewers may ask about the differences between ReentrantLock and
 * synchronized, such as the ability to interrupt a waiting thread and the option for a fairness policy.
 */
public class Sol3RaceConditionDemoReentrantLock {
    /**
     * @method main
     * @definition The main entry point of the program.
     * It sets up an ExecutorService, creates a LockCounter, and submits two tasks that increment the counter.
     * It then waits for the tasks to complete and prints the final count.
     * @param args Command line arguments (not used).
     * @throws InterruptedException if the thread is interrupted while waiting for tasks to complete.
     */
    public static void main(String[] args) throws InterruptedException {
        // Create a thread pool with a fixed number of threads.
        ExecutorService pool = Executors.newFixedThreadPool(10);
        // Create an instance of the thread-safe counter.
        LockCounter counter = new LockCounter();

        // Define a task that increments the counter 100,000 times.
        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) {
                counter.increment();
            }
        };

        // Submit the task to be executed by two different threads from the pool.
        pool.submit(task);
        pool.submit(task);

        // Shut down the pool, which means it will no longer accept new tasks.
        pool.shutdown();
        // Wait for all submitted tasks to complete, with a timeout of 1 minute.
        pool.awaitTermination(1, TimeUnit.MINUTES);

        // The expected count is 200,000 because two threads each increment the counter 100,000 times.
        System.out.println("Expected count: 200000");
        // Print the final count. With the ReentrantLock, this will be 200,000.
        System.out.println("Final count (ReentrantLock): " + counter.getCount());
    }
}
