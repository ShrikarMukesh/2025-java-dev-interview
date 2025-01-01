package core.java.interview.java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class SecondHighest {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(10, 20, 30, 40, 50);
        int secondHighest = list.stream()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("List is empty"));
        System.out.println("Second highest element: " + secondHighest);
    }
}
