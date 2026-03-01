package basic;

public class WrapperClasses {
    public static void main(String[] args) {
        Integer a = 124;
        Integer b = 124;
        // True because 124 is within the Integer Cache range (-128 to 127)
        System.out.println("a == b: " + (a == b));

        Integer c = 128;
        Integer d = 128;
        // False because 128 is outside the cache range, so new objects are created
        System.out.println("c == d: " + (c == d));

        // Always use .equals() to compare values of wrapper objects
        System.out.println("c.equals(d): " + c.equals(d));
    }
}
