package oops;

import java.util.List;

// 1. Association
// A general relationship between two classes. They use each other but have separate lifecycles.
class Bank {
    private String name;

    public Bank(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}

class Employee {
    private String name;
    
    public Employee(String name) {
        this.name = name;
    }
    
    public String getEmployeeName() {
        return this.name;
    }
}

// Association Example: Employee uses Bank, but neither owns the other.
class AssociationDemo {
    public static void main(String[] args) {
        Bank bank = new Bank("Chase");
        Employee emp = new Employee("John");
        
        System.out.println(emp.getEmployeeName() + " uses " + bank.getName());
    }
}

// 2. Aggregation (Weak Association)
// "Has-A" relationship. The child can exist independently of the parent.
class Department {
    private String name;
    private List<Professor> professors; // Department has professors

    public Department(String name, List<Professor> professors) {
        this.name = name;
        this.professors = professors;
    }
    
    public List<Professor> getProfessors() {
        return professors;
    }
}

class Professor {
    private String name;

    public Professor(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}

// Aggregation Example: If Department is destroyed, Professor still exists.
class AggregationDemo {
    public static void main(String[] args) {
        Professor p1 = new Professor("Dr. Smith");
        Professor p2 = new Professor("Dr. Jones");
        
        Department dept = new Department("Computer Science", List.of(p1, p2));
        
        System.out.println("Department has professors: " + dept.getProfessors().size());
        // p1 and p2 still exist here even if dept is nullified
    }
}

// 3. Composition (Strong Association)
// "Part-Of" relationship. The child cannot exist without the parent.
class Car {
    private final Engine engine; // Car owns the Engine

    public Car() {
        this.engine = new Engine(); // Engine is created when Car is created
    }
    
    public void drive() {
        engine.start();
        System.out.println("Car is moving");
    }
}

class Engine {
    public void start() {
        System.out.println("Engine started");
    }
}

// Composition Example: If Car is destroyed, Engine is also destroyed (conceptually).
class CompositionDemo {
    public static void main(String[] args) {
        Car car = new Car();
        car.drive();
        // There is no reference to 'engine' outside of 'car'.
    }
}

public class RelationshipExamples {
    public static void main(String[] args) {
        System.out.println("--- Association ---");
        new AssociationDemo().main(null);
        
        System.out.println("\n--- Aggregation ---");
        new AggregationDemo().main(null);
        
        System.out.println("\n--- Composition ---");
        new CompositionDemo().main(null);
    }
}
