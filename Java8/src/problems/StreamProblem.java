package problems;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Student {
    private String firstName;
    private String lastName;
    private int age;
    private String dept;
    private String place;
    private double grade;

    public Student(String firstName, String lastName, int age, String dept, String place, double grade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.dept = dept;
        this.place = place;
        this.grade = grade;
    }

    // Getters and toString() for easy printing
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public String getDept() { return dept; }
    public String getPlace() { return place; }
    public double getGrade() { return grade; }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", " + age + ", " + dept + ", " + place + ", " + grade;
    }
}

public class StreamProblem {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("Alice", "Johnson", 20, "CS", "New York", 8.5),
                new Student("Bob", "Smith", 22, "ECE", "Los Angeles", 7.9),
                new Student("Carol", "Williams", 21, "ME", "Chicago", 8.2),
                new Student("David", "Brown", 23, "CS", "Houston", 9.1),
                new Student("Eve", "Davis", 20, "EEE", "Miami", 7.5),
                new Student("Frank", "Miller", 21, "CIVIL", "Dallas", 8.0),
                new Student("Grace", "Lee", 22, "CS", "Chicago", 9.3),
                new Student("Hank", "Moore", 20, "ME", "Dallas", 7.8),
                new Student("Ivy", "Clark", 23, "ECE", "Dallas", 8.7),
                new Student("Jack", "Walker", 21, "EEE", "Chicago", 7.2),
                new Student("Kathy", "Hall", 22, "CS", "Austin", 8.9),
                new Student("Leo", "Young", 20, "CIVIL", "Miami", 7.6),
                new Student("Mona", "King", 23, "ME", "Chicago", 8.4),
                new Student("Nina", "Scott", 21, "ECE", "Chicago", 9.0),
                new Student("Oscar", "Green", 22, "EEE", "Miami", 7.7)
        );
        //maimi students
        List<Student> maimiStudents = students
                .stream()
                .filter(std -> std.getPlace().equals("Miami"))
                .filter(student -> student.getGrade() > 7.5)
                .toList();

       // System.out.println(maimiStudents);

        //Top grade Student
      Stream<Student> topGradestudent =
              students.stream()
                      .sorted(Comparator.comparing(Student::getGrade).reversed())
                      .limit(1);
      //student.forEach(System.out::println);

       Student topStudent =  students.stream()
                .max(Comparator.comparing(Student::getGrade))
                .get();
       //System.out.println(topStudent);

       Map<String, List<Student>> deptWiseStudents = students.stream().collect(Collectors.groupingBy(Student::getDept));
       //deptWiseStudents.entrySet().stream().forEach(System.out::println);

        Map<String , Double> stringDoubleMap =
        students.stream()
                .collect(Collectors.groupingBy(Student::getDept, Collectors.averagingDouble(Student::getGrade)));



    }
}
