package collection.hashmap;

import java.lang.reflect.Field;
import java.util.HashMap;

class CollidingKey {
    private final int id;

    public CollidingKey(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        // Force collision: all keys go to same bucket
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CollidingKey)) return false;
        CollidingKey other = (CollidingKey) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Key-" + id;
    }
}

public class HashMapTreeifyUntreeifyDemo {
    public static void main(String[] args) throws Exception {
        HashMap<CollidingKey, String> map = new HashMap<>();

        // Insert colliding keys (force treeify)
        for (int i = 1; i <= 12; i++) {
            map.put(new CollidingKey(i), "Value-" + i);
        }

        System.out.println("After inserting 12 colliding keys:");
        checkBucketType(map);

        // Now remove keys to trigger UN-TREEIFY (below 6 elements in the bucket)
        for (int i = 1; i <= 7; i++) {
            map.remove(new CollidingKey(i));
            System.out.println("Removed Key-" + i);
            checkBucketType(map);
        }
    }

    // Reflection helper to check the bucket type
    private static void checkBucketType(HashMap<?, ?> map) throws Exception {
        Field tableField = HashMap.class.getDeclaredField("table");
        tableField.setAccessible(true);
        Object[] table = (Object[]) tableField.get(map);

        for (Object bucket : table) {
            if (bucket != null) {
                System.out.println("👉 Bucket class: " + bucket.getClass().getName());
            }
        }
    }
}
