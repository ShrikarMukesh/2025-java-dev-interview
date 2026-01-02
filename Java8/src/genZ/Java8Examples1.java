package genZ;

import java.util.Arrays;
import java.util.List;

public class Java8Examples1 {
    public static void main(String[] args) {
        System.out.println("Even numbers");
        List<Integer> list = Arrays.asList(10,15,8,49,25,98,32);
        list.stream()
                .filter(integer -> integer % 2 == 0)
                .forEach(System.out::println);

        //find out all the numbers starting with 1 using Stream functions?
        System.out.println("find out all the numbers starting with 1 using Stream functions");
        List<Integer> myList = Arrays.asList(10,15,8,49,25,98,32);
        myList.stream()
                .map(s -> s + "")
                .filter(s -> s.startsWith("1"))
                .forEach(System.out::println);

    }
}
