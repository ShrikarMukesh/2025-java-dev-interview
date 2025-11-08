package genZ;

import java.util.*;

public class StartsWith1 {
    public static void main(String[] args) {
        List<Integer> myList1 = Arrays.asList(10,15,8,49,25,98,32);
        //2) Given a list of integers, find out all the numbers starting with 1 using Stream functions?
        myList1.stream().filter(integer -> String.valueOf(integer).startsWith("1"))
                .forEach(System.out::println);

        //3) How to find duplicate elements in a given integers list in java using Stream functions?
        Set<Integer> distElements = new HashSet<>();
        List<Integer> myList2 = Arrays.asList(10,15,8,49,25,98,98,32,15);
//        List<Integer> finalResult = new ArrayList<>();
//        for (Integer integer : myList2){
//            boolean flag = distElements.add(integer);
//            if (!flag){
//                finalResult.add(integer);
//            }
//        }
//        System.out.println(finalResult);
        myList2.stream()
                .filter(integer -> !distElements.add(integer))
                .forEach(System.out::println);
    }
}
