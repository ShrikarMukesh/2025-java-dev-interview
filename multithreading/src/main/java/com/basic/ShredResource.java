package com.basic;

public class ShredResource {
    boolean isItemAvailable = false;
    public synchronized void adamItem(){
        isItemAvailable = true;
        System.out.println("Producer thread calling notify method");
        notifyAll();
    }

    public synchronized void consumeItem(){
        System.out.println("Consumer thread inside consumeItem method");
        while (!isItemAvailable){
            try {
                System.out.println("Consumer thread is waiting");
                wait();
            }catch (InterruptedException exception){
                exception.printStackTrace();
            }
        }
        System.out.println("Consumer thread consumed the item");
        isItemAvailable = false;
    }

    public static void main(String[] args) {

        ShredResource shredResource = new ShredResource();

        Thread producer = new Thread(()->{
            try {
                Thread.sleep(2000);
            }catch (Exception exception){
                exception.printStackTrace();
            }
            shredResource.adamItem();
        });


        Thread consumer = new Thread(()->{
            shredResource.consumeItem();
        });
        producer.start();
        consumer.start();
    }
}
