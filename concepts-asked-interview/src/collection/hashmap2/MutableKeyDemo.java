package collection.hashmap2;

import java.util.HashMap;
import java.util.Map;

public class MutableKeyDemo {

    // A simple mutable key class where hashCode()/equals() depend on 'name'
    static class Person {
        String name; // mutable field used for hashCode/equals

        Person(String name) {
            this.name = name; // constructor
        }

        @Override
        public int hashCode() {
            // hashCode depends only on name (so changing name changes hashCode)
            return name == null ? 0 : name.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            // standard equals comparing names
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Person other = (Person) obj;
            if (name == null && other.name == null) return true;
            if (name == null || other.name == null) return false;
            return name.equals(other.name);
        }

        @Override
        public String toString() {
            // helpful printout so we can see the current name
            return "Person{name='" + name + "'}";
        }
    }

    public static void main(String[] args) {
        // Create a HashMap with Person keys
        Map<Person, String> map = new HashMap<>();

        // Create a Person with name "Alice"
        Person p = new Person("Alice");

        // Put the person as key into the map
        map.put(p, "Engineer");
        // At this point: map.hashCode uses p.hashCode() which is "Alice".hashCode()
        // The entry is stored in the bucket computed from that hash.

        // Getting the value with the same object works now
        System.out.println("Before mutation: map.get(p) = " + map.get(p));
        // -> prints "Engineer"

        // Mutate the field used by hashCode()
        p.name = "Bob"; // change name AFTER the key has been inserted
        // Now p.hashCode() reflects "Bob".hashCode(), not "Alice".hashCode()

        // Print mutated key
        System.out.println("After mutation: p = " + p);

        // Try to get the value using the same object reference 'p'
        System.out.println("map.get(p) = " + map.get(p));
        // Expected: null (because map looks in a different bucket computed from new hash)

        // containsKey will also typically return false
        System.out.println("map.containsKey(p) = " + map.containsKey(p));

        // But the entry still exists in the map's internal bucket (you can see it by iterating)
        System.out.println("Iterating map entries to show the stored key and value:");
        for (Map.Entry<Person, String> entry : map.entrySet()) {
            System.out.println(" entry key=" + entry.getKey() + ", value=" + entry.getValue());
            // This shows the entry with key name "Bob" (because we mutated the same object),
            // but lookup by key fails because the map searches the wrong bucket.
        }

        // Trying to get by creating a new Person with name "Bob" also fails:
        System.out.println("map.get(new Person(\"Bob\")) = " + map.get(new Person("Bob")));
        // Why? Because the entry is stored in bucket for "Alice".hashCode(), and lookup for "Bob"
        // searches bucket for "Bob".hashCode().

        // Attempting to remove using the mutated key will usually fail:
        System.out.println("map.remove(p) = " + map.remove(p)); // typically null (remove didn't find it)
        System.out.println("map.size() after remove attempt = " + map.size()); // likely still 1

        // The only straightforward way to recover: iterate entries to find the entry and remove by reference
        Person foundKey = null;
        for (Person key : map.keySet()) {
            if (key == p) { // identity check; same object reference
                foundKey = key;
                break;
            }
        }
        if (foundKey != null) {
            // Remove using the actual key reference found in the bucket chain
            map.remove(foundKey);
            System.out.println("Removed by iterating and matching reference; new size = " + map.size());
        } else {
            System.out.println("Could not find the original key reference by iterating (unexpected).");
        }
    }
}
