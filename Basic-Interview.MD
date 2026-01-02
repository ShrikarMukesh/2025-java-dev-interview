

1. In Java, **a `static` block cannot throw a checked exception**.

Here’s why:

### 1. Static block execution

* Static blocks run **once when the class is loaded into the JVM**.
* The JVM is responsible for loading classes, so the code inside a static block must be **clean and guaranteed to run without requiring the caller to handle exceptions**.

### 2. Checked exceptions rule

* Checked exceptions in Java must either be **declared in the method signature** (using `throws`) or **handled** inside the method using `try-catch`.
* But a static block is **not a method** and **cannot have a `throws` clause**.

So:

```java
class basic.Test {
    static {
        // ❌ Compile-time error: unreported exception
        throw new Exception("Checked Exception");
    }
}
```

This won’t compile.

### 3. What you can do

* You can throw **unchecked exceptions** (`RuntimeException` or subclasses) from a static block:

```java
class basic.Test {
    static {
        // ✅ Allowed
        throw new RuntimeException("Unchecked Exception");
    }
}
```

* Or, if you want to deal with checked exceptions, you must **handle them inside the block**:

```java
class basic.Test {
    static {
        try {
            throw new Exception("Checked Exception handled");
        } catch (Exception e) {
            System.out.println("Handled: " + e.getMessage());
        }
    }
}
```

---

✅ **Summary:**
A static block **cannot throw checked exceptions** because it cannot declare `throws`. It can only throw unchecked exceptions, or you must handle checked ones inside the block.

---

# ✅ Scenario 1: Overriding `equals()` but not `hashCode()`

### **Interview Question:**

*"What happens if you override only the `equals()` method but not the `hashCode()` method in a class that is used as a key in a `HashMap`?"*

---

### **Answer:**

If you override only `equals()` and not `hashCode()`:

1. Two logically equal objects may have **different hash codes** (because the default `hashCode()` from `Object` is based on memory address).
2. In a `HashMap`, keys are stored in **buckets determined by hash code**. Since the hash codes differ, the keys are placed in **different buckets**.
3. As a result, `map.get(equalObject)` may return **null**, even though `equals()` says the keys are equal.

---

### **Code Example:**

```java
import java.util.*;

class Employee {
    String name;

    Employee(String name) { this.name = name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee e = (Employee) o;
        return Objects.equals(name, e.name);
    }
    // ❌ hashCode not overridden
}

public class basic.Test {
    public static void main(String[] args) {
        Employee e1 = new Employee("Alice");
        Employee e2 = new Employee("Alice");

        Map<Employee, String> map = new HashMap<>();
        map.put(e1, "Developer");

        System.out.println(map.get(e2)); // ❌ null
    }
}
```

### **Explanation:**

* `e1.equals(e2)` → `true`
* But `e1.hashCode() != e2.hashCode()` → different buckets
* Retrieval fails (`null`)

---

---

# ✅ Scenario 2: Overriding `hashCode()` but not `equals()`

### **Interview Question:**

*"What happens if you override only the `hashCode()` method but not the `equals()` method in a class that is used as a key in a `HashMap`?"*

---

### **Answer:**

If you override only `hashCode()` and not `equals()`:

1. Two logically equal objects may have the **same hash code**.
2. They are stored in the **same bucket** in the `HashMap`.
3. But since `equals()` falls back to `Object.equals()` (reference comparison), the objects are **not considered equal**.
4. This leads to **duplicate keys** in hash-based collections and inconsistent lookups.

---

### **Code Example:**

```java
import java.util.*;

class Employee {
    String name;

    Employee(String name) { this.name = name; }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    // ❌ equals not overridden
}

public class basic.Test {
    public static void main(String[] args) {
        Employee e1 = new Employee("Alice");
        Employee e2 = new Employee("Alice");

        Map<Employee, String> map = new HashMap<>();
        map.put(e1, "Developer");
        map.put(e2, "Manager");

        System.out.println(map.size()); // 2 ❌ duplicate keys allowed
        System.out.println(map.get(e1)); // Developer
        System.out.println(map.get(e2)); // Manager
    }
}
```

### **Explanation:**

* `e1.hashCode() == e2.hashCode()` → same bucket
* `e1.equals(e2)` → `false` (different objects)
* `HashMap` treats them as **different keys** → duplicates

---

---

# ✅ Best Practice

### **Interview Question:**

*"What is the best practice when overriding `equals()` and `hashCode()`?"*

---

### **Answer:**

* Always **override both methods together**.
* Follow the contract:

    1. If `a.equals(b)` → then `a.hashCode() == b.hashCode()` must be true.
    2. If `a.hashCode() == b.hashCode()` → then `a.equals(b)` *may* be true (collisions possible).

---

### **Correct Implementation:**

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Employee)) return false;
    Employee e = (Employee) o;
    return Objects.equals(name, e.name);
}

@Override
public int hashCode() {
    return Objects.hash(name);
}
```

This ensures consistent behavior in **HashMap**, **HashSet**, and all hash-based collections.

---

**Problem Definition:**  
Given an array of strings, write a Java program to sort the characters within each string in ascending order and print each sorted string. 
The original array should remain unchanged, and the solution should use Java 8 features such as streams and lambda expressions.

```java
package  functions;
import java.util.Arrays;

public class Java8Practice {
    public static void main(String[] args) {
        String[] arr = {"hello", "zebra", "world"};
        Arrays.stream(arr)
                .map(s -> {
                    char[] chars = s.toCharArray();
                    Arrays.sort(chars);
                    return new String(chars);
                })
                .forEach(System.out::println);

    }
}
```


