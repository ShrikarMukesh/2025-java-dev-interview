package com.threadpoolExceutor;

import java.util.concurrent.*;


public class ThreadPoolExample {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new
                ThreadPoolExecutor(2,
                5,
                1,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(10),
                //Executors.defaultThreadFactory(),
                new MyCustomThreadFactory(),
                //new ThreadPoolExecutor.DiscardOldestPolicy()
                new CustomRejectionHandler()
        );

        threadPoolExecutor.allowCoreThreadTimeOut(true);
        for (int i=1;i<=25;i++){
            threadPoolExecutor.submit(()->{
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Task processed by :" + Thread.currentThread().getName());
            });
        }
    }
}
class MyCustomThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setPriority(Thread.NORM_PRIORITY);
        thread.setDaemon(false);
        return thread;
    }
}

class CustomRejectionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("Task Rejected : " + r.toString());
    }
}