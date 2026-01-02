package multithreading;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Going inside main method : " + Thread.currentThread().getName());
//        MultithreadingLearning runnableObject = new MultithreadingLearning();
//        Thread thread = new Thread(runnableObject);
//        System.out.println("Finish main method : " + Thread.currentThread().getName());
//        thread.start();

        /* second Way */
        MultithreadingLearning myThread = new MultithreadingLearning();
        myThread.start();
        System.out.println("Finish main method : " + Thread.currentThread().getName());

    }
}
