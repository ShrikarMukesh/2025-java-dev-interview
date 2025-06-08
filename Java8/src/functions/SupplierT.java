package functions;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class SupplierT {
    public static void main(String[] args) {
        Supplier<String> stringSupplier = () -> "Hello world";
        System.out.println(stringSupplier.get());

        String string = "Shrikar";
        Predicate<String> predicate = str -> str.contains("r") ;
        System.out.println(predicate.test(string));
    }
}
