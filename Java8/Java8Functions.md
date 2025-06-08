
# 🚀 Java 8 Important Features and Functions

Java 8 introduced powerful features that revolutionized the way Java applications are written by enabling functional programming and modern APIs.

---

## ✅ 1. Lambda Expressions
**Purpose**: Enable functional programming and simplify anonymous class syntax.

```java
List<String> names = Arrays.asList("Amit", "Neha", "Raj");
names.forEach(name -> System.out.println(name));
```

---

## ✅ 2. Functional Interfaces
**Purpose**: Interfaces with a single abstract method used with Lambda expressions.

### Common Functional Interfaces:
| Interface             | Abstract Method      | Use Case                       |
|----------------------|----------------------|--------------------------------|
| `Predicate<T>`       | `boolean test(T t)`  | Condition checking             |
| `Function<T, R>`     | `R apply(T t)`       | Transform input to output      |
| `Consumer<T>`        | `void accept(T t)`   | Perform operation, no return   |
| `Supplier<T>`        | `T get()`            | Returns result, no input       |
| `UnaryOperator<T>`   | `T apply(T t)`       | One input, same output         |
| `BinaryOperator<T>`  | `T apply(T t1, T t2)`| Two same-type inputs/outputs   |

---

## ✅ 3. Stream API
**Purpose**: Functional-style operations on collections.

```java
List<String> names = Arrays.asList("Amit", "Neha", "Raj");

names.stream()
     .filter(name -> name.startsWith("A"))
     .map(String::toUpperCase)
     .forEach(System.out::println);
```

### Key Stream Operations:
- `filter()`
- `map()`
- `collect()`
- `sorted()`
- `reduce()`

---

## ✅ 4. Default & Static Methods in Interfaces
**Purpose**: Interfaces can now contain method implementations.

```java
interface MyInterface {
    default void show() {
        System.out.println("Default implementation");
    }

    static void print() {
        System.out.println("Static method");
    }
}
```

---

## ✅ 5. Method References
**Purpose**: Short-hand for lambda expressions.

### Types:
- Static Method → `ClassName::staticMethod`
- Instance Method → `object::instanceMethod`
- Constructor → `ClassName::new`

```java
List<String> list = Arrays.asList("A", "B", "C");
list.forEach(System.out::println);
```

---

## ✅ 6. Optional Class
**Purpose**: Avoid `NullPointerException` using optional containers.

```java
Optional<String> name = Optional.ofNullable(getName());
name.ifPresent(n -> System.out.println(n));
```

---

## ✅ 7. New Date & Time API (java.time)
**Purpose**: Better immutable date/time classes.

```java
LocalDate today = LocalDate.now();
LocalDate dob = LocalDate.of(1994, Month.MAY, 19);

Period age = Period.between(dob, today);
System.out.println("Age: " + age.getYears());
```

### Classes:
- `LocalDate`, `LocalTime`, `LocalDateTime`
- `ZonedDateTime`, `Period`, `Duration`

---

## 🧠 Summary Table

| Feature               | Description                               |
|-----------------------|-------------------------------------------|
| Lambda Expressions    | Write concise anonymous functions         |
| Functional Interfaces | Used with Lambdas (Predicate, Function...)|
| Stream API            | Functional collection processing          |
| Default Methods       | Interfaces can have implementation        |
| Method References     | Simplified Lambda syntax                  |
| Optional              | Avoid `null` values safely                |
| Date/Time API         | Immutable & modern date/time handling     |

---

> Designed for Java developers preparing for interviews or upgrading to Java 8.

---

📌 **Note**: Practice each feature with real-world examples to gain a solid understanding before using them in production systems.