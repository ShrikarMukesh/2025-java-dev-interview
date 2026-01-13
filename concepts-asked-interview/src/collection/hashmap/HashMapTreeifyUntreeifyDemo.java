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

        System.out.println("After inserting 12 colliding keys: size=" + map.size());
        checkBucketTypeAndCount(map);

        // Now remove keys to trigger UN-TREEIFY (below 6 elements in the bucket)
        for (int i = 1; i <= 7; i++) {
            map.remove(new CollidingKey(i));
            System.out.println("Removed Key-" + i + "; size=" + map.size());
            checkBucketTypeAndCount(map);
        }
    }

    // Reflection helper to check the bucket type and count nodes in the first non-empty bucket
    private static void checkBucketTypeAndCount(HashMap<?, ?> map) throws Exception {
        try {
            Field tableField = HashMap.class.getDeclaredField("table");
            tableField.setAccessible(true);
            Object[] table = (Object[]) tableField.get(map);

            if (table == null) {
                System.out.println("Internal table is null (no buckets allocated yet)");
                return;
            }

            int nonEmptyBuckets = 0;
            for (Object bucket : table) {
                if (bucket != null) {
                    nonEmptyBuckets++;
                }
            }

            System.out.println("Non-empty bucket count: " + nonEmptyBuckets);

            for (Object bucket : table) {
                if (bucket != null) {
                    int cnt = countNodes(bucket);
                    System.out.println("👉 Bucket class: " + bucket.getClass().getName() + ", nodeCount=" + cnt);
                }
            }
        } catch (java.lang.reflect.InaccessibleObjectException e) {
            System.err.println("Reflection access to java.util.HashMap internals is blocked by the Java module system.");
            System.err.println("To run this demo, re-run the JVM with the following option:");
            System.err.println("  --add-opens java.base/java.util=ALL-UNNAMED");
            System.err.println("Example:");
            System.err.println("  javac HashMapTreeifyUntreeifyDemo.java");
            System.err.println("  java --add-opens java.base/java.util=ALL-UNNAMED collection.hashmap.HashMapTreeifyUntreeifyDemo");
            throw e;
        }
    }

    // Count nodes by following 'next' references (works for Node and TreeNode)
    private static int countNodes(Object bucket) throws Exception {
        int count = 0;
        Object cur = bucket;
        while (cur != null) {
            count++;
            try {
                Field nextField = cur.getClass().getDeclaredField("next");
                nextField.setAccessible(true);
                cur = nextField.get(cur);
            } catch (NoSuchFieldException nsfe) {
                // Some wrapper (older/newer impl) might use a different shape; try to find 'first' field (TreeBin)
                try {
                    Field firstField = cur.getClass().getDeclaredField("first");
                    firstField.setAccessible(true);
                    cur = firstField.get(cur);
                } catch (NoSuchFieldException nsfe2) {
                    break;
                }
            }
        }
        return count;
    }
}
