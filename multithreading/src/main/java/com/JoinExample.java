package com;
class ImportantResource{

    boolean isAvailable = false;
    public synchronized void getSeat(){
        System.out.println("Lock acquired");
        isAvailable = true;
        try {
            Thread.sleep(2000);
        }catch (InterruptedException exception){
            System.out.println(exception.getMessage());
        }
        System.out.println("Now seat is available " + isAvailable);
        System.out.println("Lock released");
    }
}
public class JoinExample {
    public static void main(String[] args) {
        System.out.println("Main thread started "+ Thread.currentThread().getName());
        ImportantResource importantResource = new ImportantResource();
        Thread thread1 = new Thread (()->{
              importantResource.getSeat();
        });

        thread1.setDaemon(true);
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Main thread Completed "+ Thread.currentThread().getName());
    }
}
