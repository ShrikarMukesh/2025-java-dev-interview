package com.introduction; // Defines the package for the class

import java.util.LinkedList; // Imports the LinkedList class
import java.util.Queue; // Imports the Queue interface

// This class represents a shared resource that will be accessed by multiple threads (Producer-Consumer Problem)
public class SharedResource {

    private Queue<Integer> sharedBuffer; // A queue to hold the data being exchanged between producer and consumer
    private int bufferSize; // The maximum size of the shared buffer

    // Constructor to initialize the shared resource with a specific buffer size
    public SharedResource(int bufferSize) {
        this.sharedBuffer = new LinkedList<>(); // Initializes the buffer as a new LinkedList
        this.bufferSize = bufferSize; // Sets the buffer size
    }

    // This method is for the producer thread to add items to the buffer
    public synchronized void produce(int item) throws InterruptedException {

        // If the buffer is full, the producer thread will wait for the consumer to consume items
        while (sharedBuffer.size() == bufferSize){
            System.out.println("Buffer is full, producer is waiting for the consumer to consume.");
            wait(); // The thread releases the lock and waits
        }

        sharedBuffer.add(item); // Adds the new item to the buffer
        System.out.println("Produced: " + item);
        // Notifies one of the waiting threads (in this case, the consumer) that it can now proceed
        notify();
    }

    // This method is for the consumer thread to remove items from the buffer
    public synchronized int consume() throws InterruptedException {

        // If the buffer is empty, the consumer thread will wait for the producer to produce items
        while (sharedBuffer.isEmpty()){
            System.out.println("Buffer is empty, consumer is waiting for the producer to produce.");
            wait(); // The thread releases the lock and waits
        }

        int item = sharedBuffer.poll(); // Removes an item from the buffer
        System.out.println("Consumed: " + item);
        // Notifies one of the waiting threads (in this case, the producer) that it can now proceed
        notify();
        return item; // Returns the consumed item
    }

    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource(3); // Creates a shared resource with a buffer size of 3
        // Creates a new thread for the producer
        Thread producerThread = new Thread(() -> {
            try {
                // The producer will produce 7 items (from 0 to 6)
                for (int i=0; i<=6;i++){
                    sharedResource.produce(i);
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // Creates a new thread for the consumer
        Thread consumerThread = new Thread(() -> {
            try {
                // The consumer will consume 7 items
                for (int i=0; i<=6;i++){
                    sharedResource.consume();
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        producerThread.start(); // Starts the producer thread
        consumerThread.start(); // Starts the consumer thread
    }
}
