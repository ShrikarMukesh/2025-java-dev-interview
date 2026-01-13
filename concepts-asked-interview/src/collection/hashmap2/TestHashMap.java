package collection.hashmap2;

import java.util.HashMap;

public class TestHashMap {
    public static void main(String[] args) {
        HashMap<String, Integer> hashMap = new HashMap();
        hashMap.put("Shrikar", 12);
        hashMap.put("Shrikar", 43);

        System.out.println("Shrikar".hashCode());
        System.out.println("Shrikar".hashCode());
    }
}
