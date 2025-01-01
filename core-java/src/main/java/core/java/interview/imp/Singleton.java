package core.java.interview.imp;



public class Singleton {
    /*
    - **private**: The `instance` variable is private to ensure that it cannot be accessed directly from outside the `Singleton` class.
           This enforces encapsulation and ensures that the only way to access the `Singleton` instance is through the `getInstance` method.

    - **static**: The `instance` variable is static so that it belongs to the class itself rather than any particular instance of the class.
           This ensures that there is only one instance of the `Singleton` class, which is shared across all instances of the class.

    - **volatile**: The `volatile` keyword ensures that multiple threads handle the `instance` variable correctly when it is being
           initialized to the `Singleton` instance. It guarantees visibility of changes to variables across threads, preventing issues
            with caching and ensuring that the most up-to-date value is always read.
     */
    private static volatile Singleton instance;

    // Private constructor to prevent instantiation from outside the class
    private Singleton(){
    }

    // Public static method to return the Singleton instance
    public static Singleton getInstance(){
        // Checks if the instance is null
        if (instance == null){
            // Synchronizes on the Singleton class
            synchronized (Singleton.class){
                // Double-checks if the instance is null inside the synchronized block
                if (instance == null) {
                    // Creates a new instance of Singleton
                    instance = new Singleton();
                }
            }
        }
        return instance; // Returns the Singleton instance
    }
}