package  functions;
import java.util.Arrays;

public class Java8Practice {
    public static void main(String[] args) {
        String[] arr = {"hello", "zebra", "world"};
        Arrays.stream(arr)
                .map(s -> {
                    char[] chars = s.toCharArray();
                    Arrays.sort(chars);
                    return new String(chars);
                })
                .forEach(System.out::println);

    }
}
