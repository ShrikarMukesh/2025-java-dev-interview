package asked;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.print("1");
        HelloWorld helloWorld = new HelloWorld();
        HelloWorld helloWorl1 = new HelloWorld();
        helloWorld.getMethod();
    }
    HelloWorld(){
        super();
        System.out.print(" 2");
    }
    {
        System.out.print(" 3");
    }
    static{
        System.out.print(" 4");
    }
    public void getMethod(){
        System.out.print(" 5");
    }
}
