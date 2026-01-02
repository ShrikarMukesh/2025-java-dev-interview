package asked;

import java.util.Arrays;
import java.util.List;

public class StreamVsParallelStream {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        System.out.println("--- Sequential Stream ---");
        numbers.stream().forEach(num -> {
            System.out.println(num + " " + Thread.currentThread().getName());
        });

        System.out.println("\n--- Parallel Stream ---");
        numbers.parallelStream().forEach(num -> {
            System.out.println(num + " " + Thread.currentThread().getName());
        });
    }
}
