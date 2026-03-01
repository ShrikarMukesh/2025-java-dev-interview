package com.interview.strings;

import java.util.Arrays;
import java.util.Comparator;

public class StringArraySorting {
    public static void main(String[] args) {
        String[] strings = {"c","java","html","SQL"};
        Arrays.asList(strings)
                .stream()
                .sorted(Comparator.comparing(String::length)
                        .thenComparing(Comparator.naturalOrder()))
                .forEach(System.out::println);
    }
}
