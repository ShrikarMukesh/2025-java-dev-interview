package basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Student{

    private int id;
    private String name;
    private String email;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Student(int id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }
}

class Sorter {

    static class AgeSorter implements Comparator<Student>{
        @Override
        public int compare(Student o1, Student o2) {
            return o1.getAge() - o2.getAge();
        }
    }
    static class NameSorter implements Comparator<Student>{

        @Override
        public int compare(Student s1, Student s2) {
            return s1.getName().compareTo(s2.getName());
        }
    }
}
public class ComapartorExmp {
    public static void main(String[] args) {

        Student s1 = new Student(1, "Shrikar", "shrikar@gmail.com", 25);
        Student s2 = new Student(2, "Shashikant", "Shashikant@gmail.com", 15);
        Student s3 = new Student(3, "Rohan", "Rohan@gmail.com", 35);
        Student s4 = new Student(4, "Madarsha", "Madarsha@gmail.com", 45);
        Student s5 = new Student(5, "Revan", "Revan@gmail.com", 55);
        ArrayList<Student> students = new ArrayList<>();
        students.add(s4); students.add(s1);
        students.add(s3); students.add(s5);students.add(s2);

        System.out.println("List of students");
        System.out.println(students);
        System.out.println("Sorted students based on age");
        Collections.sort(students,new Sorter.AgeSorter());
        System.out.println(students);

        System.out.println("Sorted students based on Name");
        Collections.sort(students,new Sorter.NameSorter());
        System.out.println(students);


    }
}