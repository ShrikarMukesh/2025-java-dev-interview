package com.interview.main;

public class CheckPalindrome {
    public static void main(String[] args) {
        String input = "aabaa";
        StringBuffer result = new StringBuffer();

        for(int i=input.length()-1;i>=0;i--) {
            result = result.append(input.charAt(i));
        }
        if(input.equals(result.toString())) {
            System.out.println("Pal");
        }else{
            System.out.println("Not pal");
        }
    }
}
