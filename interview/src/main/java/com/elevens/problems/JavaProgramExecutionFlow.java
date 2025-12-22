package com.elevens.problems;

public class JavaProgramExecutionFlow {

    public JavaProgramExecutionFlow(){
        super();
        System.out.println("Constructor Block");
    }
    public static void main(String[] args) {
        System.out.println("Main Method");
        JavaProgramExecutionFlow javaProgramExecutionFlow = new JavaProgramExecutionFlow();
        javaProgramExecutionFlow.printMethod();
    }
    static {
        System.out.println("Static Block");
    }
    {
        System.out.println("Instanace Block");
    }
    private void printMethod(){
        System.out.println("Print Method");
    }
}
