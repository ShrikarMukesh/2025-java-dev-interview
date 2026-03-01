package com.interview.strings;

import java.util.LinkedHashSet;
import java.util.Set;

public class StringRepeatMethod {
    public static void main(String[] args) {
        String text = "hi";
        int count = 3;
        String result = text.repeat(count);
        System.out.println(result);
        System.out.println(isRepeatedPattern("srisri"));;
        System.out.println(removeRepeateCharsFromString("abcdackl"));
    }

    public static boolean isRepeatedPattern(String input){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<input.length()/2;i++){
            stringBuilder.append(input.charAt(i));
            String remaining = input.replace(stringBuilder.toString(), "");
            if (remaining.isEmpty()){
                return true;
            }
        }
        return false;
    }

    public static String removeRepeateCharsFromString(String input){

        Set<Character> distinctString = new LinkedHashSet<>();
        for (int i=0;i<input.length();i++){
            distinctString.add(input.charAt(i));
        }
        return distinctString.toString();
    }
}
