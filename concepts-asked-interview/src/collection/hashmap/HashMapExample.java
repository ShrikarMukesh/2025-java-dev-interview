package collection.hashmap;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapExample {
    public static void main(String[] args) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        //Collections.synchronizedMap(hashMap);
        hashMap.put(null, 12);
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
       // concurrentHashMap.put(null, 12);
        System.out.println(hashMap);
        System.out.println(concurrentHashMap);

    }
}
