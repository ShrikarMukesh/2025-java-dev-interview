package com.elevens.interview;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SecondLongestStream {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("mukesh","arun","cobra","rudrapratap");
        strings.stream()
                .sorted(Comparator.comparing(String::length))
                .limit(2)
                .skip(1)
                .forEach(System.out::println);
    }
}
