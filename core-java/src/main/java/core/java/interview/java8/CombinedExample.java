package core.java.interview.java8;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
public class CombinedExample {
    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Predicate<Integer> isEven = num -> num % 2 == 0;

        int sumOfSquares = numbers.stream()
                .filter(isEven)
                .map(num -> num * num)
                .reduce(0, Integer::sum);

        System.out.println("Sum of squares of even numbers: " + sumOfSquares);
        // Output: Sum of squares of even numbers: 220
    }
}
