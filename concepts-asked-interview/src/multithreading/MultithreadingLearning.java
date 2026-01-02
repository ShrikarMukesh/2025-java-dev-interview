package multithreading;

//public class MultithreadingLearning implements Runnable {
//
//    @Override
//    public void run(){
//        System.out.println("Code Executed By Thread : " + Thread.currentThread().getName());
//    }
//}

public class MultithreadingLearning extends Thread {

    @Override
    public void run(){
        System.out.println("Code Executed By Thread : " + Thread.currentThread().getName());
    }
}
