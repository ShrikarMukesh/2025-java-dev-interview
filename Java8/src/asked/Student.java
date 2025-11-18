package asked;

import java.util.*;
import java.util.stream.Collectors;

class Student {
    String name;
    String grade;

    public Student(String name, String grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() { return name; }
    public String getGrade() { return grade; }

    @Override
    public String toString() {
        return name + " - " + grade;
    }

    public static void main(String[] args) {
        Map<String, Integer> rank = new HashMap<>();
        rank.put("A+", 1);
        rank.put("A", 2);
        rank.put("B+", 3);
        rank.put("B", 4);
        rank.put("C", 5);

        List<Student> list = Arrays.asList(
                new Student("Shrikar", "A+"),
                new Student("Dinesh", "A"),
                new Student("Kumar", "B"),
                new Student("Arun", "C"),
                new Student("Sachin", "C+"),
                new Student("Rohan", "B+")

        );

        List<Student> sorted =
                list.stream()
                        .sorted(Comparator.comparingInt(
                                s -> rank.getOrDefault(s.getGrade().trim(), Integer.MAX_VALUE)
                        ))
                        .collect(Collectors.toList());

        System.out.println(sorted);
    }
}