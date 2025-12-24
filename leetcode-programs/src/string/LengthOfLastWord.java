package string;

public class LengthOfLastWord {
    public static void main(String[] args) {
        String string = "  hello shrikar how are you   ";
        System.out.println("Initial Length " + string.length());

        char[] chars = string.toCharArray();
        int length = chars.length -1;
        while (length >= 0) {
            if (chars[length] == ' '){
                --length;
            }else {
                break;
            }
        }
        int res = 0;
        while (length>=0){
            if (chars[length] != ' '){
                --length;
                res++;
            }else {
                break;
            }
        }
        System.out.println(res);
        //second approach
        System.out.println(lengthOfLastWord(string));
    }
    public static int lengthOfLastWord(String s) {
        String[] strs = s.trim().split(" ");
        return strs[strs.length-1].length();
    }
}
