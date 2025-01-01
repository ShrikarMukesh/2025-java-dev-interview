package core.java.interview.java8;

import java.util.function.Predicate;

/*
    * Predicate is a functional interface that represents a predicate (boolean-valued function) of one argument.
 */
public class PredicateExmp {
    public static void main(String[] args) {
        Predicate<Integer> isPositive = num-> num %2 == 0;
        System.out.println(isPositive.test(12));

        //Two elements are same
        Predicate<String> containsChar = str -> str.contains("XYZ");
        System.out.println(containsChar.test("XYZLKA"));
        System.out.println(containsChar.test("SHRIKAR"));
    }
}
