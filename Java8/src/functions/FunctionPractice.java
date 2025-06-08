package functions;

import java.util.function.Function;

public class FunctionPractice {
    public static void main(String[] args) {
        //Function --> work for me
        Function<Integer, Integer> doubleIt = x -> 2*x;
        Function<Integer, Integer> tripleIt = x -> 3 * x;
        System.out.println(doubleIt.andThen(tripleIt).apply(30));
        System.out.println(doubleIt.compose(tripleIt).apply(4));
    }
}
