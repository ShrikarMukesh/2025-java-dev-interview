package com.elevens;

import java.util.Arrays;
import java.util.List;

public class Lexcography {
    public static void main(String[] args) {
        List<String> stringList = Arrays.asList("abc","aac","cde");
        for (String current : stringList) {
            char[] cArray = current.toCharArray();
            char falg = cArray[0];
            for (char c : cArray) {
                int result = Character.compare(falg, c);
                if (result < 0) {
                    System.out.println(result);
                }
            }
        }
    }
}
