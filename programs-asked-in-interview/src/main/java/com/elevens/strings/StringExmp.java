package com.elevens.strings;

public class StringExmp {
    public static void main(String[] args) {
        String[] strings = {"abc","ab","abcd","abcdfe"};
        System.out.println(findLongestCommonPrefix(strings));

    }
    public static String findLongestCommonPrefix(String[] words){

        if (words==null  || words.length == 0){
            return "";
        }
        if (words.length==1){
            return words[0];
        }
        String firstWord = words[0];
        for (int index =0; index < firstWord.length();index++){
            char currentChar = firstWord.charAt(index);
            for (int i =1;i<words.length-1;i++){
                if (index>= words[i].length()
                || words[i].charAt(index) != currentChar){
                    return firstWord.substring(0, index);
                }
            }
        }
        return "";
    }
}
