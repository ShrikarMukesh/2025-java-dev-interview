package regex;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Example1 {
    public static void main(String[] args) {
        while (true){
            System.out.println("Enter Regular Expression");
            Scanner scanner = new Scanner(System.in);
            String re = scanner.nextLine();
            System.out.println("Enter String");
            String string = scanner.nextLine();
            checkStringAgainstRE(re,string);
            System.out.println("Want to exit [Y/N]");
            String choice = scanner.nextLine();
            if (choice.trim().equalsIgnoreCase("Y")){
                break;
            }
        }
    }
    public static void checkStringAgainstRE(String re, String str){
        Pattern pattern = Pattern.compile(re);
        Matcher matcher = pattern.matcher(str);
        boolean matches = matcher.matches();
        System.out.println(matches);

    }
}
