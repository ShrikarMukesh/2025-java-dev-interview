package multithreading; // package declaration: places this class in the 'multithreading' package

public class MonitorLockExample { // class demonstrating monitor locks (synchronization) in Java

    public synchronized void task1(){ // method declared 'synchronized' - the current object's monitor is acquired before entering
        try {
            System.out.println("Inside task1"); // print a message indicating task1 started
            Thread.sleep(10000); // sleep for 10 seconds (10000 ms). While sleeping, this thread still holds the object's monitor
        }catch (Exception exception){
            exception.printStackTrace(); // if sleep is interrupted or another exception occurs, print stack trace
        }
    }

    public void task2(){ // non-synchronized method (no monitor required to enter the method itself)
        System.out.println("task2, But before synchronised"); // this runs without holding the monitor
        synchronized (this){ // explicit synchronized block on 'this' - acquires the same object's monitor as task1
            System.out.println("task2, inside synchronised"); // runs while holding the object's monitor
        } // monitor released here at the end of the synchronized block
    }

    public void task3(){ // plain method with no synchronization
        System.out.println("task3"); // simply prints a message; does not interact with the monitor
    }

    public static void main(String[] args) {

        MonitorLockExample monitorLockExampleObject = new MonitorLockExample(); // create a single instance used by all threads
        Thread thread1 = new Thread(()-> monitorLockExampleObject.task1()); // thread that will call task1() on the instance
        Thread thread2 = new Thread(()-> monitorLockExampleObject.task2()); // thread that will call task2() on the same instance
        Thread thread3 = new Thread(()-> monitorLockExampleObject.task3()); // thread that will call task3() on the same instance


        thread1.start(); // start thread1 - it will attempt to acquire the instance monitor because task1 is synchronized
        thread2.start(); // start thread2 - it may run the unsynchronized part of task2 but will block if it reaches the synchronized block while another thread holds the monitor
        thread3.start(); // start thread3 - runs task3 which is not synchronized and will execute immediately regardless of the monitor
    }
}
