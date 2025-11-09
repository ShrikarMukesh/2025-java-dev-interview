package com.volatilekeyword;

public class WithoutVolatile {
    private boolean flag = false;  // NOT volatile
    private int counter = 0;

    public static void main(String[] args) {
        WithoutVolatile example = new WithoutVolatile();

        // Writer thread
        Thread writer = new Thread(() -> {
            try {
                Thread.sleep(1000);
                example.counter = 42;
                example.flag = true;  // Signal that counter is ready
                System.out.println("Writer: Set flag to true, counter = " + example.counter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Reader thread
        Thread reader = new Thread(() -> {
            while (!example.flag) {
                // This loop might never exit!
                // The reader thread may cache flag=false and never see the update
            }
            System.out.println("Reader: Detected flag = true, counter = " + example.counter);
            // Might print counter = 0 due to visibility issues
        });

        reader.start();
        writer.start();
    }
}