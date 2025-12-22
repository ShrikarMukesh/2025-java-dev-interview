package com.racecondition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @class Counter
 * @definition This class represents a simple counter that is NOT thread-safe.
 * It has an internal integer state that is incremented by the increment() method.
 *
 * @interview_comment
 * This class is designed to demonstrate a race condition. The increment operation (count++) is not atomic.
 * It consists of three steps: read the value, increment the value, and write the value back.
 * In a multithreaded environment, these steps can interleave, leading to lost updates.
 */
class Counter {
    private int count = 0;

    /**
     * @method increment
     * @definition Increments the counter.
     * This method is NOT thread-safe. Multiple threads calling this method concurrently can cause a race condition.
     */
    public void increment() {
        count++; // Not atomic! Read -> Modify -> Write
    }

    /**
     * @method getCount
     * @definition Returns the current value of the counter.
     * @return The current value of the counter.
     */
    public int getCount() {
        return count;
    }
}

/**
 * @class RaceConditionDemo
 * @definition This class demonstrates a race condition in Java.
 * It creates two tasks that concurrently increment a shared, non-thread-safe counter.
 * Due to the race condition, the final count will likely be less than the expected value.
 *
 * @interview_comment
 * A race condition occurs when two or more threads access shared data and try to change it at the same time.
 * Because the thread scheduling algorithm can swap between threads at any time, you don't know the order in which
 * the threads will attempt to access the shared data. Therefore, the result of the change in data is dependent on
 * the thread scheduling algorithm, i.e., both threads are "racing" to access/change the data.
 * This example is a classic demonstration of why synchronization is needed when mutating shared state.
 */
public class RaceConditionDemo {
    /**
     * @method main
     * @definition The main entry point of the program.
     * It sets up an ExecutorService, creates a non-thread-safe Counter, and submits two tasks that increment the counter.
     * It then waits for the tasks to complete and prints the final count, which demonstrates the race condition.
     * @param args Command line arguments (not used).
     * @throws InterruptedException if the thread is interrupted while waiting for tasks to complete.
     */
    public static void main(String[] args) throws InterruptedException {

        // Create a thread pool with a fixed number of threads.
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // Create an instance of the non-thread-safe counter.
        Counter counter = new Counter();

        // Define a task that increments the counter 100,000 times.
        Runnable task = ()-> {
            for (int i=0;i<100000;i++){
                counter.increment();
            }
        };

        // Submit the task to be executed by two different threads from the pool.
        executorService.submit(task);
        executorService.submit(task);

        // Shut down the pool, which means it will no longer accept new tasks.
        executorService.shutdown();
        // Wait for all submitted tasks to complete, with a timeout of 6 seconds.
        executorService.awaitTermination(6, TimeUnit.SECONDS);

        // The expected count is 200,000 because two threads each increment the counter 100,000 times.
        System.out.println("Expected count: 200000");
        // Print the final count. Due to the race condition, this will likely be less than 200,000.
        System.out.println("Final count is: " + counter.getCount());
    }
}
