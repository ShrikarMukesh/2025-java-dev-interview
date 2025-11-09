package com.volatilekeyword;

public class WithVolatile {
    private volatile boolean flag = false;  // NOW volatile
    private int counter = 0;

    public static void main(String[] args) {
        WithVolatile example = new WithVolatile();

        // Writer thread
        Thread writer = new Thread(() -> {
            try {
                Thread.sleep(1000);
                example.counter = 42;
                example.flag = true;  // Writing to volatile establishes happens-before
                System.out.println("Writer: Set flag to true, counter = " + example.counter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Reader thread
        Thread reader = new Thread(() -> {
            while (!example.flag) {
                // Reading volatile flag ensures we see the latest value
            }
            // When we exit loop, we're guaranteed to see counter = 42
            System.out.println("Reader: Detected flag = true, counter = " + example.counter);
        });

        reader.start();
        writer.start();
    }
}