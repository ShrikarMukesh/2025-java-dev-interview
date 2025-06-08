package functions;

import java.util.function.Predicate;

public class Predicate1 {
    public static void main(String[] args) {
       //Predicate -- Functional interface is a (boolean-valued function)
        Predicate<Integer> isEven = integer -> integer % 2 ==0;
        System.out.println(isEven.test(89));
        System.out.println(isEven.test(80));

        Predicate<String> startsWithA = str -> str.startsWith("A");
        System.out.println(startsWithA.test("ARUN"));
        System.out.println(startsWithA.test("SHRIKAR"));

        Predicate<String> andMethod = startsWithA.and(s -> s.endsWith("R"));

        System.out.println(andMethod.test("AHRIKAR"));



    }
}
@FunctionalInterface
interface Reports {
    String totalRepo(int patientId);
    //In functional interface we cant write two abstract methods
    //String patientName();
    
    Predicate p = null;
}