package com.producerConsumer;

class PrintEvenOddUtility {
    // Starting counter
    //will force all threads to update and use the latest copy of this counter, and not use locally cached copies
    volatile static int counter = 1;

    int limit;

    PrintEvenOddUtility (int limit) {
        this.limit = limit;
    }

    //function to print odd numbers
    public synchronized void printOddNum () {
        while(counter<=limit) {
            if(counter%2 == 1) { //counter is odd, print it
                // remove thread name and use System.out.print() to print in one line, as per the sample output format
                System.out.println(Thread.currentThread().getName()+": "+counter);
                counter++;
                notifyAll();
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Function to print even numbers
    public synchronized void printEvenNum () {
        while (counter<=limit) {
            if(counter%2 == 0) { //counter is even, print it
                // remove thread name and use System.out.print() to print in one line, as per the sample output format
                System.out.println(Thread.currentThread().getName()+": "+counter);
                counter++;
                notifyAll();
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
// Single shared printer coordinates between two threads to print odd and even numbers alternately.
class SharedNumberPrinter {
    // flag that indicates whose turn it is: true -> even's turn, false -> odd's turn
    private boolean isEvenTurn = false; // start with odd number (1) so odd thread prints first

    // synchronized method for even thread to invoke
    public synchronized void printEvenNumberOnly(int i){
        // If it's not even's turn, wait until notified
        while (!isEvenTurn) {
            try {
                wait();
            } catch (InterruptedException e) {
                // restore interrupt status and exit
                Thread.currentThread().interrupt();
                return;
            }
        }
        // At this point it's even's turn and we can print
        System.out.println("Even Number " + i +  " PrintedBy : "+ Thread.currentThread().getName());
        // Switch turn to odd and notify waiting threads
        isEvenTurn = false;
        notifyAll();
    }

    // synchronized method for odd thread to invoke
    public synchronized void printOddNumberOnly(int i){
        // If it's not odd's turn, wait until notified
        while (isEvenTurn) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        // It's odd's turn -> print the odd number
        System.out.println("Odd Number " + i +  " PrintedBy : "+ Thread.currentThread().getName());
        // Switch turn to even and notify waiting threads
        isEvenTurn = true;
        notifyAll();
    }
}

public class PrintEvenOddUsingTwoThreads {

    public static void main(String[] args) {
        // Create a single shared printer object that both threads will use as the monitor.
        SharedNumberPrinter printer = new SharedNumberPrinter();

        // Thread responsible for printing even numbers.
        Thread evenThread = new Thread(() -> {
            // start from 2 and print even numbers up to 100
            for (int i = 2; i <= 100; i += 2) {
                // call the shared printer's method; it will wait if it's not this thread's turn
                printer.printEvenNumberOnly(i);
            }
        });
        evenThread.setName("EvenThread");

        // Thread responsible for printing odd numbers.
        Thread oddThread = new Thread(() -> {
            // start from 1 and print odd numbers up to 99
            for (int i = 1; i <= 99; i += 2) {
                // call the shared printer's method; it will wait if it's not this thread's turn
                printer.printOddNumberOnly(i);
            }
        });
        oddThread.setName("OddThread");

        // Start both threads. Because printer.isEvenTurn starts as false, the odd thread
        // will print first (1), then signal the even thread to print (2), and so on.
        evenThread.start();
        oddThread.start();

        // Optionally join both threads to ensure main waits for completion before exiting.
        try {
            evenThread.join();
            oddThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        PrintEvenOddUtility printEvenOddUtility = new PrintEvenOddUtility (10);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                printEvenOddUtility.printOddNum();
            }
        });

        t1.setName("Odd"); // for clearer verification

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                printEvenOddUtility.printEvenNum();
            }
        });

        t2.setName("Even"); // for clearer verification

        t1.start();
        t2.start();
    }
}
