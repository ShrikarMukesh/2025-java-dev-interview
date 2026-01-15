package com.locks.condition;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class demonstrates the use of Condition, await(), and signal() for inter-thread communication.
 *
 * ## Condition Concepts
 *
 * A `Condition` object is always bound to a `Lock`. It provides a way for threads to wait for a certain condition to become true.
 * It is a more powerful and flexible alternative to the traditional `Object.wait()`, `Object.notify()`, and `Object.notifyAll()` methods.
 *
 * ### Key Differences from wait/notify:
 * 1.  **Multiple Conditions**: A single `Lock` can have multiple `Condition` objects. This allows for more fine-grained control. For example, in a Producer-Consumer problem, we can have one condition for "buffer not full" and another for "buffer not empty". This is impossible with `Object.wait/notify`, which has only a single wait set per object.
 * 2.  **Clarity**: The methods `await()`, `signal()`, and `signalAll()` are more descriptive than `wait()`, `notify()`, and `notifyAll()`.
 * 3.  **Compatibility**: `Condition` must be used with `Lock` implementations, whereas `wait/notify` are used with `synchronized` blocks.
 *
 * ## await() and signal()
 *
 * - **`await()`**: When a thread calls `condition.await()`, it **atomically releases the associated lock** and enters a waiting state. The thread will remain in this state until another thread calls `signal()` or `signalAll()` on the same condition, or until it is interrupted. Once awakened, it will attempt to re-acquire the lock before proceeding.
 *
 * - **`signal()`**: This method wakes up **one** waiting thread from the condition's wait set. The awakened thread will then try to re-acquire the lock. It's a good practice to call `signal()` only when you are sure the condition the other thread is waiting for is now true.
 *
 * - **`signalAll()`**: This wakes up **all** waiting threads for that condition. They will all compete to acquire the lock.
 */
public class ConditionExample {

    // --- Shared Resource using Lock and Condition ---
    static class SharedResource {
        private final Queue<Integer> buffer = new LinkedList<>();
        private final int capacity = 5;
        private final Lock lock = new ReentrantLock();

        // Condition for producers to wait on when the buffer is full.
        private final Condition bufferNotFull = lock.newCondition();
        // Condition for consumers to wait on when the buffer is empty.
        private final Condition bufferNotEmpty = lock.newCondition();

        // Producer method
        public void produce(int value) throws InterruptedException {
            lock.lock(); // Acquire the lock
            try {
                // Use a 'while' loop to prevent spurious wakeups
                while (buffer.size() == capacity) {
                    System.out.println("Buffer is full. Producer is waiting...");
                    bufferNotFull.await(); // Release the lock and wait
                }

                buffer.add(value);
                System.out.println("Produced: " + value);

                // Signal to a waiting consumer that the buffer is no longer empty.
                System.out.println("Signaling consumer...");
                bufferNotEmpty.signal();
            } finally {
                lock.unlock(); // Always release the lock in a finally block
            }
        }

        // Consumer method
        public int consume() throws InterruptedException {
            lock.lock(); // Acquire the lock
            try {
                // Use a 'while' loop to prevent spurious wakeups
                while (buffer.isEmpty()) {
                    System.out.println("Buffer is empty. Consumer is waiting...");
                    bufferNotEmpty.await(); // Release the lock and wait
                }

                int value = buffer.poll();
                System.out.println("Consumed: " + value);

                // Signal to a waiting producer that the buffer is no longer full.
                System.out.println("Signaling producer...");
                bufferNotFull.signal();

                return value;
            } finally {
                lock.unlock(); // Always release the lock
            }
        }
    }

    // --- Main method to demonstrate the interaction ---
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        // Producer Thread
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    resource.produce(i);
                    Thread.sleep(500); // Simulate work
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        // Consumer Thread
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    resource.consume();
                    Thread.sleep(1000); // Simulate work
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");

        producer.start();
        consumer.start();
    }
}
