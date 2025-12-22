package com.racecondition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @class AtomicCounter
 * @definition This class provides a thread-safe counter using AtomicInteger.
 * AtomicInteger is part of the java.util.concurrent.atomic package and provides atomic operations on an integer value.
 * These operations are performed without using locks, often relying on hardware-level compare-and-swap (CAS) instructions.
 *
 * @interview_comment
 * AtomicInteger is a highly efficient way to perform thread-safe operations on a single integer variable.
 * It's generally faster than using 'synchronized' or 'ReentrantLock' for simple atomic operations like incrementing a counter,
 * because it avoids the overhead of locking. Interviewers often ask about CAS (Compare-And-Swap) operations, which are the
 * foundation of how atomic variables work.
 */
class AtomicCounter {

    // Initialize an AtomicInteger with a starting value of 0.
    private final AtomicInteger count = new AtomicInteger(0);

    /**
     * @method increment
     * @definition Increments the counter atomically.
     * The 'incrementAndGet' method is an atomic operation that increments the current value by one and returns the new value.
     * This operation is thread-safe.
     */
    public void increment(){
       count.incrementAndGet(); // This is an atomic operation.
    }

    /**
     * @method getCount
     * @definition Returns the current value of the counter.
     * The 'get' method returns the current value of the AtomicInteger, which is a volatile read.
     * @return The current value of the counter.
     */
    public int getCount() {
        return count.get();
    }
}

/**
 * @class Sol1RaceConditionAtomicInteger
 * @definition This class demonstrates how to solve a race condition using AtomicInteger.
 * It creates two tasks that concurrently increment a shared counter. Because the counter uses AtomicInteger,
 * the increments are atomic, preventing lost updates and ensuring the final count is correct.
 *
 * @interview_comment
 * This example is a best-practice solution for simple counters in a multithreaded environment.
 * It's efficient and lock-free. An interviewer might ask you to compare this approach with lock-based approaches
 * (synchronized, ReentrantLock) and discuss the trade-offs (performance vs. complexity, applicability to more complex critical sections).
 */
public class Sol1RaceConditionAtomicInteger {
    /**
     * @method main
     * @definition The main entry point of the program.
     * It sets up an ExecutorService, creates an AtomicCounter, and submits two tasks that increment the counter.
     * It then waits for the tasks to complete and prints the final count.
     * @param args Command line arguments (not used).
     * @throws InterruptedException if the thread is interrupted while waiting for tasks to complete.
     */
    public static void main(String[] args) throws InterruptedException {
        // Create a thread pool with a fixed number of threads.
        ExecutorService pool = Executors.newFixedThreadPool(10);
        // Create an instance of the thread-safe atomic counter.
        AtomicCounter counter = new AtomicCounter();

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
        // Print the final count. With AtomicInteger, this will be 200,000.
        System.out.println("Final count (AtomicInteger): " + counter.getCount());
    }
}
