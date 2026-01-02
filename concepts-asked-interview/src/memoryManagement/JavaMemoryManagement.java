package memoryManagement;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.time.LocalDate;

class Person{

    private int age;
    private String name;
    private LocalDate dateOfBirth;

}
public class JavaMemoryManagement {
    public static void main(String[] args) {
        WeakReference<Person> weakReference  = new WeakReference<>(new Person());
        SoftReference<Person> personSoftReference = new SoftReference<>(new Person());
    }
}
