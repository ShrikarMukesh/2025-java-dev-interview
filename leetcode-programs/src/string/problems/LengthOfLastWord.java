package string.problems;

public class LengthOfLastWord {
    public static void main(String[] args) {
        String str = " Real Madrid is best club in the world football   ";
        String[] strings = str.trim().split(" ");
        int len = strings[strings.length - 1].length();
        System.out.println(len);
    }

    public void problem1(){
        String str = "My name is {name} I am from {place} and working in {company}";
    }
}
