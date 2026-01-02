package genZ;

// 1. Eager Initialization
// Instance is created at the time of class loading.
// Good if the instance is small and always needed.
class EagerSingleton {
    private static final EagerSingleton INSTANCE = new EagerSingleton();

    private EagerSingleton() {}

    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}

// 2. Lazy Initialization
// Instance is created only when requested.
// Not thread-safe by default.
class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton() {}

    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}

// 3. Thread-Safe Singleton (Double-Checked Locking)
// Standard way to implement thread-safe lazy singleton.
class ThreadSafeSingleton {
    private static volatile ThreadSafeSingleton instance;

    private ThreadSafeSingleton() {}

    public static ThreadSafeSingleton getInstance() {
        if (instance == null) {
            synchronized (ThreadSafeSingleton.class) {
                if (instance == null) {
                    instance = new ThreadSafeSingleton();
                }
            }
        }
        return instance;
    }
}

// 4. Bill Pugh Singleton (Static Inner Class)
// Thread-safe and lazy-loaded without synchronization overhead.
class BillPughSingleton {
    private BillPughSingleton() {}

    private static class SingletonHelper {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}

// 5. Enum Singleton
// Best way to implement Singleton in Java. Handles serialization and reflection attacks.
enum EnumSingleton {
    INSTANCE;
    
    public void doSomething() {
        System.out.println("Enum Singleton doing something");
    }
}

// Spring Boot Context:
// In Spring Boot, beans are Singletons by default (Scope="singleton").
// The container manages the lifecycle and ensures only one instance exists per context.
// Example (Conceptual):
/*
@Service
public class UserService {
    // This class is instantiated once by Spring container
}
*/

public class SingletonExamples {
    public static void main(String[] args) {
        EagerSingleton eager = EagerSingleton.getInstance();
        LazySingleton lazy = LazySingleton.getInstance();
        ThreadSafeSingleton threadSafe = ThreadSafeSingleton.getInstance();
        BillPughSingleton billPugh = BillPughSingleton.getInstance();
        EnumSingleton enumSingleton = EnumSingleton.INSTANCE;
        
        System.out.println("All singletons retrieved successfully.");
    }
}
