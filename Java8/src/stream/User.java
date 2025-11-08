package stream;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class User {
    private String name;
    private int age;
    private int salary;
    private String dept;

    public User(String name, int age, int salary, String dept) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", dept='" + dept + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && salary == user.salary && Objects.equals(name, user.name) && Objects.equals(dept, user.dept);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, salary, dept);
    }


    public static List<User> getUsers(){
        List<User> users = Arrays.asList(
                new User("Alice", 25, 2345, "HR"),
                new User("Bob", 30,73837, "HR"),
                new User("Charlie", 25, 98983, "DEV"),
                new User("David", 30, 7878, "DEV"),
                new User("Eve", 28, 90909, "TESTING")
        );
        return users;
    }
}