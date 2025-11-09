package com.volatilekeyword;

class SharedResource2 {
    volatile boolean flag = false; // ✅ now volatile

    public void setFlagTrue() {
        flag = true;
        System.out.println("Flag set to true by " + Thread.currentThread().getName());
    }

    public void waitForFlag() {
        System.out.println("Waiting for flag...");
        while (!flag) {
            // Busy wait until flag becomes true
        }
        System.out.println("Flag detected by " + Thread.currentThread().getName());
    }
}

public class WithVolatileDemo {
    public static void main(String[] args) throws InterruptedException {
        SharedResource2 shared = new SharedResource2();

        Thread writer = new Thread(shared::setFlagTrue, "Writer");
        Thread reader = new Thread(shared::waitForFlag, "Reader");

        reader.start();
        Thread.sleep(1000);
        writer.start();
    }
}
