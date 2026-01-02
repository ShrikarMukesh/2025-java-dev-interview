package basic;

interface CardsService{
    default void iamDefalutMethod(){
        System.out.println("Defalut method");
    }
    private void cardNumber(){
        var cardNumber = "787473";
    }
    //Not possible
//    static {
//        System.out.println("Block");
//    }
}
class StaticExample {
    static double iamStaticVariable = 89;
    static {
        System.out.println("This is a static block");
    }
    static void staticMethod(){
        System.out.println("Static Method");
    }
}
class Test{
    public static void main(String[] args) {
        StaticExample.staticMethod();
        double d = StaticExample.iamStaticVariable;
        System.out.println(d);
    }
}

