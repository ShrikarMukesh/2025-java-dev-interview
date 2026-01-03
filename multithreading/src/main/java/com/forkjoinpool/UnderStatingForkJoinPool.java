package com.forkjoinpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class UnderStatingForkJoinPool {

    public static void main(String[] args) {
        // Simulate a large dataset of transactions (10 million records)
        long[] transactions = LongStream.rangeClosed(1, 10_000_000).toArray();

        ForkJoinPool pool = new ForkJoinPool();
        
        // Create the main task covering the entire array
        TransactionProcessor task = new TransactionProcessor(transactions, 0, transactions.length);

        System.out.println("Starting processing...");
        long startTime = System.currentTimeMillis();
        
        // Submit the task to the pool and wait for the result
        Long sum = pool.invoke(task);
        
        long endTime = System.currentTimeMillis();

        System.out.println("Total Transaction Value: " + sum);
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }

    // RecursiveTask returns a result (RecursiveAction does not)
    static class TransactionProcessor extends RecursiveTask<Long> {
        private static final int THRESHOLD = 10_000; // Split tasks larger than this
        private final long[] transactions;
        private final int start;
        private final int end;

        public TransactionProcessor(long[] transactions, int start, int end) {
            this.transactions = transactions;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            int length = end - start;
            
            // Base case: if task is small enough, compute directly
            if (length <= THRESHOLD) {
                return computeDirectly();
            } else {
                // Recursive case: split the task
                int mid = start + length / 2;
                TransactionProcessor leftTask = new TransactionProcessor(transactions, start, mid);
                TransactionProcessor rightTask = new TransactionProcessor(transactions, mid, end);

                // Fork the left task (push to queue)
                leftTask.fork(); 
                
                // Compute the right task on the current thread
                Long rightResult = rightTask.compute(); 
                
                // Wait for the left task to complete and get result
                Long leftResult = leftTask.join(); 

                return leftResult + rightResult;
            }
        }

        private Long computeDirectly() {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += transactions[i];
            }
            return sum;
        }
    }
    public void print(){
        System.out.println("Print methody");
    }
    public void add(){
        System.out.println("add methody");
    }


}
