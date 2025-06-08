package functions;

public class Java8Demo {
    public static void main(String[] args) {
        // Streams
        // Java 8 --> minimal code, functional programing
        // Java 8 --> lambda expression, Streams, Date & Time API

        // lambda expression
        // lambda expression is an anonymous function ( no name, no return type, no access modifier)

//        Thread t1 = new Thread(new Task());
//        t1.start();
         Thread t1 = new Thread(()->{
            System.out.println("Task1 is running");
         });
         t1.start();

         MathOperation addition = (a, b) -> a + b;
         System.out.println(addition.operate(23,90));;

    }

}

class Calculator implements MathOperation{

    @Override
    public int operate(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        System.out.println(calculator.operate(2,8));
    }
}

interface MathOperation {
    int operate(int a, int b);
}
// Runnable interface is a functional interface
class Task implements Runnable{
    @Override
    public void run() {
        System.out.println("Task1 is running");
    }
}

