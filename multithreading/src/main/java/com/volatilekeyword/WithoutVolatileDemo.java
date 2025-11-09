package com.volatilekeyword;

class SharedResource1 {
    boolean flag = false; // not volatile

    public void setFlagTrue() {
        flag = true;
        System.out.println("Flag set to true by " + Thread.currentThread().getName());
    }

    public void waitForFlag() {
        System.out.println("Waiting for flag...");
        while (!flag) { // might never exit!
            // Busy wait
        }
        System.out.println("Flag detected by " + Thread.currentThread().getName());
    }
}

public class WithoutVolatileDemo {
    public static void main(String[] args) throws InterruptedException {
        SharedResource1 shared = new SharedResource1();

        Thread writer = new Thread(shared::setFlagTrue, "Writer");
        Thread reader = new Thread(shared::waitForFlag, "Reader");

        reader.start();
        Thread.sleep(1000); // ensure reader starts first
        writer.start();
    }
}
