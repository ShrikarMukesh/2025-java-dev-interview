package com.volatilekeyword;

/**
 * @class WithVolatile
 * @definition This class demonstrates how the 'volatile' keyword solves memory visibility and instruction reordering issues.
 * It has a volatile boolean flag and a non-volatile integer counter.
 *
 * @interview_comment
 * This example is the solution to the problems shown in the 'WithoutVolatile' class.
 * By declaring the 'flag' variable as 'volatile', we establish a "happens-before" relationship.
 *
 * A "happens-before" relationship is a guarantee that memory writes by one specific statement are visible to another specific statement.
 * In this case:
 * 1.  A write to a volatile variable (`flag = true`) happens-before every subsequent read of that same variable.
 * 2.  This means the reader thread is guaranteed to see the `flag` as true eventually and will exit the loop.
 * 3.  Crucially, it also means that all other memory writes that happened *before* the volatile write in the writer thread (i.e., `counter = 42`) are now visible to the reader thread *after* it reads the volatile flag.
 *
 * This prevents both the infinite loop and the reordering problem, guaranteeing the reader will print "counter = 42".
 */
public class WithVolatile {
    // The 'flag' is now volatile, ensuring visibility and ordering guarantees.
    private volatile boolean flag = false;
    // The 'counter' does not need to be volatile because its access is ordered by the volatile 'flag'.
    private int counter = 0;

    /**
     * @method main
     * @definition The main entry point of the program.
     * It creates a "writer" and a "reader" thread to demonstrate the correct behavior when using 'volatile'.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        WithVolatile example = new WithVolatile();

        // The writer thread updates the counter and then sets the volatile flag.
        Thread writer = new Thread(() -> {
            try {
                // Sleep to increase the chance the reader starts first.
                Thread.sleep(1000);
                // This write is guaranteed to be visible to the reader thread...
                example.counter = 42;
                // ...because of this subsequent volatile write.
                example.flag = true;  // Writing to a volatile variable.
                System.out.println("Writer: Set flag to true, counter = " + example.counter);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });

        // The reader thread waits for the volatile flag to be true, then reads the counter.
        Thread reader = new Thread(() -> {
            // Reading the volatile 'flag' variable ensures we get the latest value from main memory.
            while (!example.flag) {
                // The loop is now guaranteed to terminate.
            }
            // Because we have read 'flag' as true, we are guaranteed to see all writes that happened before it.
            // Therefore, 'counter' will be 42.
            System.out.println("Reader: Detected flag = true, counter = " + example.counter);
        });

        // Start both threads.
        reader.start();
        writer.start();
    }
}
