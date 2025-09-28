package problems;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

class User{
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + " " + ", age=" + age + "}";
    }
}
public class StreamExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> evenNumbers = numbers.stream().filter(n -> n % 2 == 0).toList();
        System.out.println(evenNumbers);

        Optional<Integer> maxNumber = numbers.stream().max(Integer::compare);
        System.out.println("Max Number" + maxNumber);

        Integer i = numbers.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Total Sum " + i);

        List<String> names = Arrays.asList("Mbape","Alice", "Bob", "Charlie");
        names.stream().map(s -> s.toUpperCase()).forEach(System.out::println);

        List<String> sortedNames = names.stream().sorted().collect(Collectors.toList());
        System.out.println(sortedNames);

        Optional<Integer> anyElement = numbers.stream()
                .findAny();
        System.out.println(anyElement.get());

        List<String> fullNames = Arrays.asList("Shrikar Mukesh","Alice Johnson", "Bob Harris",
                "Charlie Lou");
        List<String> strings = fullNames.stream().map(s -> s.split(" ")[0])
                .collect(Collectors.toList());
        System.out.println("First names" + strings);

        List<User> users = Arrays.asList(
                new User("Shrikar", 25),
                new User("Alice", 30),
                new User("Bob", 25),
                new User("Charlie", 30)
        );

        Map<Integer, List<User>> usersByAge =
                users.stream()
                        .collect(Collectors.groupingBy(User::getAge));
        System.out.println(usersByAge);

       Map<Integer, Long> integerLongMap = users.stream().collect(Collectors.groupingBy(User::getAge, Collectors.counting()));
        System.out.println(integerLongMap);

        List<Integer> limited = numbers.stream()
                .limit(3)
                .collect(Collectors.toList());

    }
}
