package core.java.interview.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//The map operation transforms each element of a stream.
public class MapExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        List<String> upperCaseNames = names.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println(upperCaseNames); // Output: [ALICE, BOB, CHARLIE]
        names.stream().map(s -> s.length()).forEach(System.out::print);
    }
}
