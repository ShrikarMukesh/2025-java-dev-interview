package core.java.interview.java8;

import java.util.function.Function;

//A Function is a functional interface that takes one argument and produces a result.
public class FunctionExmp {
    public static void main(String[] args) {
        Function<String,Integer> function = s -> s.length();
        System.out.println(function.apply("REAL MADRID"));
    }
}
