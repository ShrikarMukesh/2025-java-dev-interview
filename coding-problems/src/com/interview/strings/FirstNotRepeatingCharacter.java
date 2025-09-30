package com.interview.strings;

import java.util.HashMap;
import java.util.Map;

public class FirstNotRepeatingCharacter {
    public static void main(String[] args) {

        String s = "abacxabadckilm";
        System.out.println(firstNotRepeatingCharacter1(s));
        System.out.println(firstNotRepeatingCharacter2(s));
    }
    private static char firstNotRepeatingCharacter2(String s) {

        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for(int i=0;i<s.length();i++) {
            char c = s.charAt(i);
            if(map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            }else {
                map.put(c, 1);
            }
        }
        for(int i=0;i<s.length();i++) {
            char c = s.charAt(i);
            if(map.get(c) == 1) {
                return c;
            }
        }
        return '_';
    }
    private static char firstNotRepeatingCharacter1(String s) {

        int[] char_counts = new int[26];
        for(char c: s.toCharArray()) {
            char_counts[c - 'a']++;
        }
        for(char c: s.toCharArray()) {
            if(char_counts[c - 'a'] == 1) {
                return c;
            }
        }
        return '_';
    }
}