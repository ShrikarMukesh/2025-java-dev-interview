package com.volatilekeyword;

/**
 * @class WithoutVolatile
 * @definition This class demonstrates memory visibility and instruction reordering issues when the 'volatile' keyword is not used.
 * It has a non-volatile boolean flag and a non-volatile integer counter.
 *
 * @interview_comment
 * This example highlights two potential problems without proper synchronization:
 * 1.  **Visibility:** The 'reader' thread might never see the 'writer' thread's update to 'flag = true' because the value might be cached. This would cause the reader to loop forever.
 * 2.  **Reordering:** Even if the reader sees 'flag = true', there is no guarantee it will see the correct value of 'counter'. The Java Memory Model allows the compiler and CPU to reorder instructions. The writer thread might set 'flag = true' *before* it sets 'counter = 42'. The reader could then see 'flag' as true, exit the loop, and print 'counter' as 0.
 *
 * Using 'volatile' on the 'flag' variable would solve both problems by creating a "happens-before" relationship, ensuring that all writes before the volatile write are visible to any thread that reads the volatile variable.
 */
public class WithoutVolatile {
    // These variables are shared between threads and are NOT volatile.
    private boolean flag = false;
    private int counter = 0;

    /**
     * @method main
     * @definition The main entry point of the program.
     * It creates two threads, a "writer" and a "reader", to demonstrate visibility and reordering problems.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        WithoutVolatile example = new WithoutVolatile();

        // The writer thread updates the counter and then sets the flag.
        Thread writer = new Thread(() -> {
            try {
                // Sleep to increase the chance the reader starts first.
                Thread.sleep(1000);
                // These two writes can be reordered by the compiler or CPU.
                example.counter = 42;
                example.flag = true;  // Signal that the counter is ready.
                System.out.println("Writer: Set flag to true, counter = " + example.counter);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });

        // The reader thread waits for the flag to be true, then reads the counter.
        Thread reader = new Thread(() -> {
            // This busy-wait loop might never exit if the reader thread caches 'flag' as false.
            while (!example.flag) {
                // Busy-waiting is generally inefficient but is used here for demonstration.
            }
            // Because there's no happens-before guarantee, the reader might see the updated flag
            // but still see the old value of the counter (0).
            System.out.println("Reader: Detected flag = true, counter = " + example.counter);
        });

        // Start both threads.
        reader.start();
        writer.start();
    }
}
