package com.locks;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerProb {
    private final Queue<Integer> sharedBuffer = new LinkedList<>();
    private final int capacity = 5; // Maximum size of the buffer

    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            synchronized (this) {
                // Wait if the buffer is full
                while (sharedBuffer.size() == capacity) {
                    System.out.println("Buffer is full. Producer is waiting...");
                    wait();
                }

                System.out.println("Producer produced: " + value);
                sharedBuffer.add(value++);
                
                // Notify the consumer that there is data to consume
                notify();
                
                // Sleep to simulate time taken to produce
                Thread.sleep(1000);
            }
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            synchronized (this) {
                // Wait if the buffer is empty
                while (sharedBuffer.isEmpty()) {
                    System.out.println("Buffer is empty. Consumer is waiting...");
                    wait();
                }

                int value = sharedBuffer.poll();
                System.out.println("Consumer consumed: " + value);

                // Notify the producer that there is space in the buffer
                notify();
                
                // Sleep to simulate time taken to consume
                Thread.sleep(1000);
            }
        }
    }

    public static void main(String[] args) {
        ProducerConsumerProb resource = new ProducerConsumerProb();

        // Create the producer thread
        Thread producerThread = new Thread(() -> {
            try {
                resource.produce();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer-Thread");

        // Create the consumer thread
        Thread consumerThread = new Thread(() -> {
            try {
                resource.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer-Thread");

        // Start both threads
        producerThread.start();
        consumerThread.start();
    }
}
