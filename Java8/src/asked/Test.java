package asked;

class Demo {
    static { System.out.println("Block 1"); }
    static { System.out.println("Block 2"); }
}
class Example1 {
    static int count = 5;

    static {
        System.out.println(count);
        print();
    }

    static String print() {
        System.out.println("Static method called");
        //
        return "Hello";
    }
}
class Example {
    static {
        System.out.println("Static");

    }
    {
        System.out.println("Instance");
    }
    Example() {
        System.out.println("Constructor");
    }
}

class Parent {
    static { System.out.println("Parent static"); }

    protected static String print(){
        return "Parent";
    }
}
class Child extends Parent {
    static { System.out.println("Child static"); }
    public static String print(){
        return "Child";
    }
}

public class Test {
    public static void main(String[] args) {
        //Demo demo = new Demo();
        //Example example = new Example();
        //Example1 example1 = new Example1();
        Child child = new Child();
        System.out.println(child.print());
    }

}
