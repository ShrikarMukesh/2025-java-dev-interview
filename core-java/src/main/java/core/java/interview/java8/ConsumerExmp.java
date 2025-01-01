package core.java.interview.java8;

import java.util.function.Consumer;

/*
 A Consumer is a functional interface that accepts a
 single argument and performs some operation without returning a result.
 */
public class ConsumerExmp {
    public static void main(String[] args) {
        Consumer<String> printUpperCase = str -> System.out.println(str.toUpperCase());

        printUpperCase.accept("hello"); // Output: HELLO
        printUpperCase.accept("java functional programming");

    }
}
