package com.introduction;

// Real-world example: A Stock Price Emitter that simulates fetching and broadcasting stock prices.
// This class demonstrates how to safely implement STOP, SUSPEND, and RESUME functionality.
public class StockPriceEmitter {

    // Volatile flag to control the running state of the producer (STOP)
    private volatile boolean isRunning = true;
    // Volatile flag to control the suspension state (SUSPEND/RESUME)
    private volatile boolean isSuspended = false;
    // Lock object to handle synchronization for suspension
    private final Object lock = new Object();

    // The produce method simulates the continuous production of data
    public void produce() {
        System.out.println("Producer started. Emitting stock prices...");
        double price = 100.0;

        // Loop continues as long as the producer is not stopped
        while (isRunning) {
            
            // Check if the producer is suspended
            synchronized (lock) {
                while (isSuspended) {
                    try {
                        System.out.println("Producer is SUSPENDED. Waiting to resume...");
                        // Wait releases the lock and pauses execution until notified
                        lock.wait(); 
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Restore interrupted status
                        System.out.println("Producer interrupted during suspension.");
                    }
                }
            }

            // Double-check isRunning in case we were stopped while suspended
            if (!isRunning) {
                break;
            }

            // Simulate producing a stock price update
            price += (Math.random() - 0.5) * 2.0; // Random fluctuation
            System.out.printf("New Stock Price: %.2f%n", price);

            try {
                // Simulate time taken to fetch/produce the next item
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Producer interrupted during sleep.");
            }
        }
        System.out.println("Producer has been STOPPED.");
    }

    // Method to suspend the producer
    public void suspend() {
        isSuspended = true;
        System.out.println("Requesting SUSPEND...");
    }

    // Method to resume the producer
    public void resume() {
        synchronized (lock) {
            isSuspended = false;
            lock.notify(); // Notify the waiting thread to wake up
            System.out.println("Requesting RESUME...");
        }
    }

    // Method to stop the producer
    public void stop() {
        isRunning = false;
        // If the thread is currently suspended, we need to resume it so it can exit the loop
        resume(); 
        System.out.println("Requesting STOP...");
    }

    public static void main(String[] args) {
        StockPriceEmitter emitter = new StockPriceEmitter();

        // Create a thread to run the produce method
        Thread producerThread = new Thread(emitter::produce);
        producerThread.start();

        try {
            // Let it run for a few seconds
            Thread.sleep(3000);

            // Suspend the producer
            emitter.suspend();
            
            // Wait while it is suspended
            Thread.sleep(3000);

            // Resume the producer
            emitter.resume();

            // Let it run for a bit more
            Thread.sleep(3000);

            // Stop the producer
            emitter.stop();

            producerThread.join(); // Wait for the thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
