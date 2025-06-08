package functions;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerExmp {
    public static void main(String[] args) {
        Consumer<Integer> consumer = System.out::println;
        consumer.accept(9);

        List<Integer> integerList = Arrays.asList(1,6,7);
        integerList.iterator().forEachRemaining(System.out::println);

        Consumer<List<Integer>> listConsumer = integers -> {
            for (int i: integers)
                System.out.println(i);
        };
        listConsumer.accept(integerList);
    }
}
