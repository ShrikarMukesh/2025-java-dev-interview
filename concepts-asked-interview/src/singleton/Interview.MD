## Enum Singleton (Java)

An **enum-based Singleton** is the **most robust and recommended way** to implement a singleton in Java.

---

## Basic Implementation

```java
public enum Singleton {
    INSTANCE;

    public void doWork() {
        System.out.println("Singleton working");
    }
}
```

Usage:

```java
Singleton.INSTANCE.doWork();
```

---

## Why Enum Singleton Is the Best (Key Reasons)

### 1. **Thread-safe by design**

* Enum instances are created by the JVM during class loading
* Class loading is **intrinsically thread-safe**
* No `synchronized`, no locks, no double-checked locking

---

### 2. **Safe from Serialization issues**

Traditional singleton breaks like this:

```java
ObjectInputStream -> new instance created
```

Enum singleton:

* JVM guarantees **only one instance**
* No need for `readResolve()`

---

### 3. **Safe from Reflection attacks**

This breaks normal singletons:

```java
constructor.setAccessible(true);
```

Enum constructors:

* Cannot be invoked via reflection
* JVM throws `IllegalArgumentException`

---

### 4. **Simple and clean**

* Minimal code
* No boilerplate
* Impossible to misuse

---

## Comparison with Other Singleton Approaches

| Approach               | Thread-safe | Serialization safe | Reflection safe | Complexity |
| ---------------------- | ----------- | ------------------ | --------------- | ---------- |
| Eager Singleton        | ✅           | ❌                  | ❌               | Low        |
| Lazy + synchronized    | ✅           | ❌                  | ❌               | Medium     |
| Double-checked locking | ✅           | ❌                  | ❌               | High       |
| Static Inner Class     | ✅           | ❌                  | ❌               | Medium     |
| **Enum Singleton**     | ✅           | ✅                  | ✅               | **Lowest** |

---

## Example: Enum Singleton with State

```java
public enum ConfigManager {
    INSTANCE;

    private final Map<String, String> config = new HashMap<>();

    public void put(String key, String value) {
        config.put(key, value);
    }

    public String get(String key) {
        return config.get(key);
    }
}
```

Usage:

```java
ConfigManager.INSTANCE.put("timeout", "30s");
```

⚠️ **Note**: State inside the enum must still be **thread-safe** if mutated.

---

## When NOT to Use Enum Singleton

Enum singleton is excellent, but avoid it when:

1. **Lazy initialization with parameters is required**

   ```java
   INSTANCE(config) // ❌ not possible
   ```

2. **Frameworks require a normal class**

    * Some DI frameworks
    * Some proxy-based libraries

3. **You need inheritance**

    * Enums cannot extend classes

---

## Interview-Ready One-Liner

> An enum singleton is the safest way to implement a singleton in Java because it is inherently thread-safe, serialization-safe, and protected against reflection attacks by the JVM.

---

## Common Interview Follow-up (Be Ready)

**Q: Why does Joshua Bloch recommend enum singleton?**
**A:** Because it eliminates all traditional singleton pitfalls—thread safety, serialization, and reflection—using JVM guarantees instead of developer-managed code.

---

## Senior Tip (Enterprise / Spring Boot)

In **Spring Boot**:

* Prefer **Spring beans** (`@Singleton` scope by default)
* Use enum singleton only for:

    * Utility singletons
    * JVM-wide constants
    * Stateless helpers

---

If you want next, I can explain:

* Why enum constructors are special in JVM
* Bytecode-level explanation
* Enum singleton vs Spring singleton
* How enum singleton behaves in class loaders
* Interview coding traps around singleton

Tell me what you want to go deeper into.
