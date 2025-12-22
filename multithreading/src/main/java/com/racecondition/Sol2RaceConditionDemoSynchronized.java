package com.racecondition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @class SyncCounter
 * @definition This class provides a thread-safe counter using the synchronized keyword.
 * An intrinsic lock (or monitor lock) is an implicit lock associated with every object. When a thread invokes a
 * synchronized method, it automatically acquires the intrinsic lock for that object and releases it when the method returns.
 *
 * @interview_comment
 * This class demonstrates a common way to ensure thread safety in Java.
 * The 'synchronized' keyword can be applied to methods or blocks of code. When applied to a method,
 * it locks the entire method, ensuring that only one thread can execute it at a time on a given object instance.
 */
class SyncCounter {
   private int count = 0;

   /**
    * @method increment
    * @definition Increments the counter in a thread-safe manner.
    * The 'synchronized' keyword ensures that this method can only be executed by one thread at a time for a given instance of SyncCounter.
    */
   public synchronized void increment(){
       count++;
   }

    /**
     * @method getCount
     * @definition Returns the current value of the counter.
     * This method is also synchronized to ensure that any thread calling it will see the latest value of 'count'
     * after any increments have completed. This prevents memory consistency errors.
     * @return The current value of the counter.
     */
    public synchronized int getCount() {
        return count;
    }
}

/**
 * @class Sol2RaceConditionDemoSynchronized
 * @definition This class demonstrates how to solve a race condition using the 'synchronized' keyword.
 * It creates two tasks that concurrently increment a shared counter. The 'synchronized' keyword on the
 * increment and getCount methods of the counter ensures that the operations are atomic and visible across threads,
 * preventing lost updates and ensuring the final count is correct.
 *
 * @interview_comment
 * This is a classic example of using synchronization to fix a race condition. The 'synchronized' keyword provides
 * a simple way to ensure mutual exclusion and visibility. Interviewers might ask about the performance implications of
 * synchronization and compare it to other mechanisms like AtomicInteger or ReentrantLock.
 */
public class Sol2RaceConditionDemoSynchronized {

    /**
     * @method main
     * @definition The main entry point of the program.
     * It sets up an ExecutorService, creates a SyncCounter, and submits two tasks that increment the counter.
     * It then waits for the tasks to complete and prints the final count.
     * @param args Command line arguments (not used).
     * @throws InterruptedException if the thread is interrupted while waiting for tasks to complete.
     */
    public static void main(String[] args) throws InterruptedException {

        // Create a thread pool with a fixed number of threads.
        ExecutorService pool = Executors.newFixedThreadPool(5);

        // Create an instance of the thread-safe counter.
        SyncCounter counter = new SyncCounter();

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
        // Print the final count. With synchronization, this will be 200,000.
        System.out.println("Final count (synchronized): " + counter.getCount());
    }
}
