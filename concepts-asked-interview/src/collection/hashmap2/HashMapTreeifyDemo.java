import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class HashMapTreeifyDemo {

    // Constants from OpenJDK HashMap (for explanation)
    // TREEIFY_THRESHOLD = 8
    // UNTREEIFY_THRESHOLD = 6
    // MIN_TREEIFY_CAPACITY = 64

    // Key with controllable hashCode so we can force collisions into the same bucket
    static class Key {
        final int id;
        final int forcedHash; // we return this from hashCode()

        Key(int id, int forcedHash) {
            this.id = id;
            this.forcedHash = forcedHash;
        }

        @Override
        public int hashCode() {
            return forcedHash;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;
            Key other = (Key) o;
            return this.id == other.id;
        }

        @Override
        public String toString() {
            return "Key(" + id + ",h=" + forcedHash + ")";
        }
    }

    // Utility to access HashMap's internal table and return the class name of the node in a given bucket
    static String bucketNodeClassName(HashMap<?, ?> map, int index) throws Exception {
        Field tableField = HashMap.class.getDeclaredField("table");
        tableField.setAccessible(true);
        Object[] table = (Object[]) tableField.get(map);
        if (table == null) return "<table==null>";
        Object node = table[index];
        if (node == null) return "<empty>";
        return node.getClass().getName();
    }

    // Count nodes in a bucket by following pointers via reflection
    static int countBucketNodes(HashMap<?, ?> map, int index) throws Exception {
        Field tableField = HashMap.class.getDeclaredField("table");
        tableField.setAccessible(true);
        Object[] table = (Object[]) tableField.get(map);
        if (table == null) return 0;
        Object node = table[index];
        if (node == null) return 0;
        int count = 0;

        // Two possible shapes:
        // 1) A regular linked list of Node objects: Node has a 'next' field.
        // 2) A treeified bucket where table[index] holds a TreeBin object which has a 'first' field
        //    that points to the first TreeNode in a linked list. TreeNode objects have 'next'.
        Class<?> nodeClass = node.getClass();
        // If this is a TreeBin (name contains TreeBin), get the 'first' field instead
        if (nodeClass.getName().endsWith("$TreeBin")) {
            Field firstField = nodeClass.getDeclaredField("first");
            firstField.setAccessible(true);
            Object first = firstField.get(node);
            Object cur = first;
            while (cur != null) {
                count++;
                Field nextField = cur.getClass().getDeclaredField("next");
                nextField.setAccessible(true);
                cur = nextField.get(cur);
            }
            return count;
        }

        // Otherwise treat node as a regular Node and follow 'next'
        Object cur = node;
        while (cur != null) {
            count++;
            Field nextField = cur.getClass().getDeclaredField("next");
            nextField.setAccessible(true);
            cur = nextField.get(cur);
        }
        return count;
    }

    // Compute bucket index for a given forcedHash and table length
    static int bucketIndexForHash(int forcedHash, int tableLength) {
        return (tableLength - 1) & forcedHash;
    }

    public static void main(String[] args) throws Exception {
        // We'll create a HashMap with initial capacity 64 to allow treeification (MIN_TREEIFY_CAPACITY = 64)
        Map<Key, String> map = new HashMap<>(64);
        HashMap<Key, String> hm = (HashMap<Key, String>) map;

        // We choose an index to collide into. For simplicity pick index 3.
        int desiredIndex = 3;

        // To ensure keys map to the same index for table length 64, their forcedHash should have the same lower 6 bits.
        // We'll pick forcedHash values as desiredIndex + k*64 so lower 6 bits equal desiredIndex.

        System.out.println("Initial map created with initial capacity 64.");

        // Before any puts the internal table is null
        Field tableField = HashMap.class.getDeclaredField("table");
        tableField.setAccessible(true);
        Object[] tableBefore = (Object[]) tableField.get(hm);
        System.out.println("Internal table before first put: " + (tableBefore == null ? "null" : tableBefore.length));

        // Insert keys that collide in the same bucket
        for (int i = 1; i <= 9; i++) {
            int forcedHash = desiredIndex + (i - 1) * 64; // same lower 6 bits => same bucket index for capacity 64
            Key k = new Key(i, forcedHash);
            map.put(k, "V" + i);
            // After the first put table will be initialized
            Object[] table = (Object[]) tableField.get(hm);
            int len = table == null ? 0 : table.length;
            int idx = len == 0 ? -1 : bucketIndexForHash(forcedHash, len);
            String nodeClass = (len == 0 || idx < 0) ? "<no-bucket>" : bucketNodeClassName(hm, idx);
            int cnt = (len == 0 || idx < 0) ? 0 : countBucketNodes(hm, idx);
            System.out.printf("After put %d: tableLen=%d, bucketIndex=%d, bucketClass=%s, bucketCount=%d\n",
                    i, len, idx, nodeClass, cnt);
        }

        System.out.println("\nNow remove entries one by one and observe if the tree untreeifies:\n");

        // Remove entries to try to drop below UNTREEIFY_THRESHOLD (=6)
        for (int i = 9; i >= 1; i--) {
            int forcedHash = desiredIndex + (i - 1) * 64;
            Key k = new Key(i, forcedHash);
            String removed = map.remove(k);
            Object[] table = (Object[]) tableField.get(hm);
            int len = table == null ? 0 : table.length;
            int idx = len == 0 ? -1 : bucketIndexForHash(forcedHash, len);
            String nodeClass = (len == 0 || idx < 0) ? "<no-bucket>" : bucketNodeClassName(hm, idx);
            int cnt = (len == 0 || idx < 0) ? 0 : countBucketNodes(hm, idx);
            System.out.printf("After remove %d: removed=%s, tableLen=%d, bucketIndex=%d, bucketClass=%s, bucketCount=%d\n",
                    i, removed, len, idx, nodeClass, cnt);
        }

        System.out.println("\nDemo finished.");
    }
}

