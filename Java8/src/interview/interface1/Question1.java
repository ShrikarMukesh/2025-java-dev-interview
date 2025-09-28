package interview.interface1;

interface First {
    default void print() {
        System.out.println("First Interface");
    }
}

interface Second {
    default void print() {
        System.out.println("Second Interface");
    }
}

public class Question1 implements First, Second {
    // To resolve ambiguity, override the print() method
    @Override
    public void print() {
        First.super.print();
        Second.super.print();
    }

    public static void main(String[] args) {
        // Use a proper instance for 'first' and 'second'
        Question1 instance = new Question1();

        // Call the print methods via the instance
        instance.print();
    }
}
