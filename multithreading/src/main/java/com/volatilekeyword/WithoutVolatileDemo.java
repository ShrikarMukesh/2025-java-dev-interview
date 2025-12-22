package com.volatilekeyword;

/**
 * @class SharedResource1
 * @definition This class represents a shared resource with a non-volatile boolean flag.
 * It is used to demonstrate visibility problems in a multithreaded environment.
 *
 * @interview_comment
 * This class is designed to show what happens when the 'volatile' keyword is NOT used for a shared variable.
 * The 'flag' variable is not declared as volatile. This means that changes made to it by one thread (the "Writer")
 * might not be immediately visible to another thread (the "Reader"). The Reader thread might cache the initial value
 * of 'flag' (false) and never see the update, leading to an infinite loop.
 */
class SharedResource1 {
    // This flag is not volatile, which can lead to visibility issues between threads.
    boolean flag = false;

    /**
     * @method setFlagTrue
     * @definition Sets the flag to true and prints a message.
     * This method is intended to be called by a "writer" thread.
     */
    public void setFlagTrue() {
        this.flag = true;
        System.out.println("Flag set to true by " + Thread.currentThread().getName());
    }

    /**
     * @method waitForFlag
     * @definition Waits in a busy-loop until the flag becomes true.
     * This method is intended to be called by a "reader" thread.
     * Because 'flag' is not volatile, this loop might never terminate.
     */
    public void waitForFlag() {
        System.out.println(Thread.currentThread().getName() + " is waiting for the flag...");
        // This is a busy-wait loop. It continuously checks the value of 'flag'.
        // Without 'volatile', the reader thread might be checking a cached value of 'flag'
        // and may never see the change made by the writer thread.
        while (!this.flag) {
            // This loop might run forever!
        }
        System.out.println("Flag change detected by " + Thread.currentThread().getName());
    }
}

/**
 * @class WithoutVolatileDemo
 * @definition This class demonstrates the potential for visibility problems when not using the 'volatile' keyword.
 * It creates two threads: a "Reader" that waits for a flag to be set, and a "Writer" that sets the flag.
 * Because the flag is not volatile, the program is not guaranteed to terminate.
 *
 * @interview_comment
 * This example illustrates a critical concept in Java concurrency: memory visibility.
 * Without proper synchronization (like using 'volatile' or 'synchronized'), there's no guarantee that a write
 * to a variable by one thread will be visible to another thread. This can happen due to compiler optimizations
 * or caching of variables in CPU registers. An interviewer might ask you to explain why this program might hang
 * and how to fix it (by making the 'flag' variable volatile).
 */
public class WithoutVolatileDemo {
    /**
     * @method main
     * @definition The main entry point of the program.
     * It creates a SharedResource1 instance and two threads to demonstrate the visibility issue.
     * @param args Command line arguments (not used).
     * @throws InterruptedException if the main thread is interrupted.
     */
    public static void main(String[] args) throws InterruptedException {
        // Create the shared resource instance.
        SharedResource1 shared = new SharedResource1();

        // Create a writer thread that will set the flag to true.
        Thread writer = new Thread(shared::setFlagTrue, "Writer");
        // Create a reader thread that will wait for the flag to become true.
        Thread reader = new Thread(shared::waitForFlag, "Reader");

        // Start the reader thread first.
        reader.start();
        // Sleep for a short time to increase the likelihood that the reader thread starts and caches the 'flag' value.
        Thread.sleep(1000);
        // Start the writer thread.
        writer.start();
    }
}
