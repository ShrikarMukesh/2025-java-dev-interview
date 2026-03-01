package com.interview.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        int[][] inputArray = new int[3][2];
        List<List<Integer>> integerList = Arrays.asList(Arrays.asList(1,1), Arrays.asList(2), Arrays.asList(1,1));
        ArrayList<Integer> finalList = new ArrayList<>();
        integerList.stream()
                        .collect(Collectors.toUnmodifiableList()).forEach(System.out::println);
        System.out.println(finalList);
    }
}
