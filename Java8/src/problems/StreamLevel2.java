package problems;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StreamLevel2 {
    public static void main(String[] args) {
        List<Integer> myList = Arrays.asList(10,15,8,49,25,98,32);

        myList.stream()
                .filter(integer -> String.valueOf(integer).contains("1"))
                .forEach(System.out::println);

        myList.stream()
                .map(str -> str + "")
                .filter(s -> s.contains("1"))
                .forEach(System.out::println);

        List<Integer> dupNumbers = Arrays.asList(10,15,8,49,25,98,98,32,15);

        Set<Integer> set = new HashSet();
        dupNumbers.stream()
                .filter(
                        integer -> set.add(integer)
                )
                .forEach(System.out::println);
    }
}
