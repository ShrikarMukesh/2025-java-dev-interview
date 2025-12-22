package com.volatilekeyword;

/**
 * @class SharedResource2
 * @definition This class represents a shared resource with a volatile boolean flag.
 * It is used to demonstrate how 'volatile' solves visibility problems in a multithreaded environment.
 *
 * @interview_comment
 * This class is the "fixed" version of SharedResource1 from the WithoutVolatileDemo.
 * The 'flag' variable is now declared as 'volatile'. This guarantees that any write to 'flag' by one thread
 * is immediately visible to any other thread that reads it. The 'volatile' keyword ensures that reads and writes
 * go directly to and from main memory, bypassing any thread-local caches.
 */
class SharedResource2 {
    // The 'volatile' keyword ensures that changes to 'flag' are visible across all threads.
    volatile boolean flag = false;

    /**
     * @method setFlagTrue
     * @definition Sets the flag to true and prints a message.
     * This method is intended to be called by a "writer" thread.
     */
    public void setFlagTrue() {
        this.flag = true; // This write is immediately flushed to main memory.
        System.out.println("Flag set to true by " + Thread.currentThread().getName());
    }

    /**
     * @method waitForFlag
     * @definition Waits in a busy-loop until the flag becomes true.
     * This method is intended to be called by a "reader" thread.
     * Because 'flag' is volatile, this loop is guaranteed to terminate once the writer thread sets the flag.
     */
    public void waitForFlag() {
        System.out.println(Thread.currentThread().getName() + " is waiting for the flag...");
        // This busy-wait loop continuously reads the 'flag' from main memory.
        // When the writer thread changes the flag, this thread will see the update.
        while (!this.flag) {
            // This loop is now guaranteed to terminate.
        }
        System.out.println("Flag change detected by " + Thread.currentThread().getName());
    }
}

/**
 * @class WithVolatileDemo
 * @definition This class demonstrates how the 'volatile' keyword solves visibility problems.
 * It creates a "Reader" thread that waits for a flag and a "Writer" thread that sets the flag.
 * Because the flag is volatile, the program is guaranteed to terminate correctly.
 *
 * @interview_comment
 * This example clearly shows the practical effect of the 'volatile' keyword. It ensures that the change made by the
 * 'Writer' thread is visible to the 'Reader' thread, preventing the infinite loop seen in the 'WithoutVolatileDemo'.
 * Interviewers will expect you to know that 'volatile' guarantees visibility but does not guarantee atomicity for
 * compound actions (like incrementing a counter). It is suitable for simple flags or status indicators.
 */
public class WithVolatileDemo {
    /**
     * @method main
     * @definition The main entry point of the program.
     * It creates a SharedResource2 instance and two threads to demonstrate the correct visibility behavior with 'volatile'.
     * @param args Command line arguments (not used).
     * @throws InterruptedException if the main thread is interrupted.
     */
    public static void main(String[] args) throws InterruptedException {
        // Create the shared resource instance.
        SharedResource2 shared = new SharedResource2();

        // Create a writer thread that will set the flag to true.
        Thread writer = new Thread(shared::setFlagTrue, "Writer");
        // Create a reader thread that will wait for the flag to become true.
        Thread reader = new Thread(shared::waitForFlag, "Reader");

        // Start the reader thread first.
        reader.start();
        // Sleep for a short time to increase the likelihood that the reader thread starts its loop.
        Thread.sleep(1000);
        // Start the writer thread. The change to the volatile flag will be visible to the reader.
        writer.start();
    }
}
