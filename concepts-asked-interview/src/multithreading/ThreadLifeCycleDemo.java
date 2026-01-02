package multithreading;

public class ThreadLifeCycleDemo {
    private static final Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
        // Creating a new thread - state: NEW
        Thread worker = new Thread(() -> {

            // Thread enters RUNNABLE state when start() is called
            System.out.println("Thread started");

            // Synchronizing on shared lock
            synchronized (lock) {

                try {
                    // Thread enters WAITING state
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Thread resumes and completes execution
            System.out.println("Thread finished");
        });
        worker.start();
        // Printing state before start
        System.out.println("State after creation: " + worker.getState());

        // Main thread acquires lock to notify worker
        synchronized (lock) {
            // Wakes up waiting thread
            lock.notify();
        }
        // Waits for worker thread to finish - main thread in WAITING
        worker.join();

        // Thread has completed execution - TERMINATED
        System.out.println("Final state: " + worker.getState());
    }
}
