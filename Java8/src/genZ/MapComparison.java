package genZ;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapComparison {

    public static void main(String[] args) {
        // 1. HashMap Example
        // - Implementation: Hash Table
        // - Order: No guarantee of order (neither insertion nor sorted)
        // - Null Keys: Allows one null key
        // - Performance: O(1) for get/put (average case)
        System.out.println("--- HashMap (Unordered) ---");
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Zebra", 10);
        hashMap.put("Apple", 5);
        hashMap.put("Monkey", 20);
        hashMap.put(null, 0); // Null key is allowed

        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // 2. TreeMap Example
        // - Implementation: Red-Black Tree (Self-balancing BST)
        // - Order: Sorted according to natural ordering of keys or a custom Comparator
        // - Null Keys: Does NOT allow null keys (throws NullPointerException)
        // - Performance: O(log n) for get/put
        System.out.println("\n--- TreeMap (Sorted) ---");
        Map<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("Zebra", 10);
        treeMap.put("Apple", 5);
        treeMap.put("Monkey", 20);
        // treeMap.put(null, 0); // This would throw NullPointerException

        for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
        // Custom Comparator Example
        System.out.println("\n--- TreeMap (Reverse Order) ---");
        Map<String, Integer> reverseTreeMap = new TreeMap<>((s1, s2) -> s2.compareTo(s1));
        reverseTreeMap.put("Zebra", 10);
        reverseTreeMap.put("Apple", 5);
        reverseTreeMap.putAll(treeMap);
        
        reverseTreeMap.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
