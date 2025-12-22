package com.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @class BasicCompletableFuture
 * @definition This class demonstrates basic usage of CompletableFuture for asynchronous programming.
 * CompletableFuture is a class in Java 8 that is used for asynchronous programming. It is an extension of Future,
 * which provides more flexibility and power for combining and composing asynchronous computations.
 *
 * @interview_comment
 * CompletableFuture is a key feature for modern concurrent programming in Java. Interviewers often ask about:
 * - Its advantages over the traditional Future interface (e.g., non-blocking, chaining operations).
 * - Common methods like `supplyAsync`, `runAsync`, `thenApply`, `thenAccept`, `thenCompose`, `thenCombine`, `exceptionally`.
 * - How to handle exceptions and timeouts.
 * - The default thread pools used (ForkJoinPool.commonPool()).
 */
public class BasicCompletableFutureDefinition {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("--- CompletableFuture with runAsync (no return value) ---");
        // runAsync: Executes a Runnable asynchronously. No return value.
        CompletableFuture<Void> runFuture = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Task executed in runAsync: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });

        // Block and wait for the completion of the Future
        runFuture.get(); // This will block until the task is complete
        System.out.println("runAsync task completed.");

        System.out.println("\n--- CompletableFuture with supplyAsync (with return value) ---");
        // supplyAsync: Executes a Supplier asynchronously and returns a new CompletableFuture
        // that is completed with the value of the supplier's result.
        CompletableFuture<String> supplyFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Task executed in supplyAsync: " + Thread.currentThread().getName());
                return "Hello from supplyAsync!";
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });

        // get() blocks until the Future is completed and returns the result.
        String result = supplyFuture.get();
        System.out.println("supplyAsync task completed with result: " + result);

        System.out.println("\n--- Chaining CompletableFuture with thenApply (transformation) ---");
        // thenApply: Transforms the result of the previous CompletableFuture.
        CompletableFuture<String> greetingFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                return "John";
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }).thenApply(name -> {
            // This block executes after the supplyAsync task completes.
            // It receives the result ("John") as input.
            System.out.println("thenApply executed by: " + Thread.currentThread().getName());
            return "Hello, " + name + "!";
        });

        System.out.println("Chained greeting: " + greetingFuture.get());

        System.out.println("\n--- Chaining CompletableFuture with thenAccept (consumer) ---");
        // thenAccept: Consumes the result of the previous CompletableFuture (no return value).
        CompletableFuture<Void> consumeFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
                return "World";
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }).thenAccept(data -> {
            // This block executes after the supplyAsync task completes.
            // It receives the result ("World") as input.
            System.out.println("thenAccept executed by: " + Thread.currentThread().getName());
            System.out.println("Consumed data: " + data);
        });

        consumeFuture.get(); // Wait for the consumer to finish
        System.out.println("thenAccept task completed.");

        System.out.println("\n--- Handling Exceptions with exceptionally ---");
        CompletableFuture<String> exceptionallyFuture = CompletableFuture.supplyAsync(() -> {
            if (true) { // Simulate an error
                throw new RuntimeException("Something went wrong!");
            }
            return "Success";
        }).exceptionally(ex -> {
            // This block executes if an exception occurs in the previous stage.
            System.err.println("Caught exception: " + ex.getMessage());
            return "Fallback value due to error"; // Return a fallback value
        });

        System.out.println("Result with exception handling: " + exceptionallyFuture.get());

        System.out.println("\n--- CompletableFuture with custom Executor ---");
        // You can specify an Executor for asynchronous operations.
        // By default, CompletableFuture uses ForkJoinPool.commonPool().
        ExecutorService customExecutor = Executors.newFixedThreadPool(2);
        CompletableFuture<String> customExecutorFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task in custom executor: " + Thread.currentThread().getName());
            return "Custom Executor Result";
        }, customExecutor);
        System.out.println(customExecutorFuture.get());
        customExecutor.shutdown();

        System.out.println("\n--- All CompletableFuture examples finished ---");
    }
}
