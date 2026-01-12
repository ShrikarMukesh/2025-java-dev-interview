package collection.hashmap;

import java.util.Objects;

/**
 * A simple custom implementation of a HashMap to understand its internal workings.
 * This implementation uses an array of linked lists (chaining) to handle collisions.
 *
 * @param <K> The type of keys maintained by this map
 * @param <V> The type of mapped values
 */
public class CustomHashMap<K, V> {

    // The default initial capacity - MUST be a power of two.
    private static final int DEFAULT_CAPACITY = 16;

    // The load factor used when none specified in constructor.
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    // The array of buckets. Each bucket is a linked list of Entry objects.
    public Entry<K, V>[] table;

    // The number of key-value mappings contained in this map.
    private int size;

    // The next size value at which to resize (capacity * load factor).
    private int threshold;

    /**
     * Constructs an empty CustomHashMap with the default initial capacity (16) and the default load factor (0.75).
     */
    public CustomHashMap() {
        this.table = new Entry[DEFAULT_CAPACITY];
        this.threshold = (int) (DEFAULT_CAPACITY * DEFAULT_LOAD_FACTOR);
        this.size = 0;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     */
    public void put(K key, V value) {
        // 1. Calculate hash of the key
        int hash = hash(key);

        // 2. Calculate index in the table based on the hash
        int index = getIndex(hash, table.length);

        // 3. Check if there is already an entry at that index
        Entry<K, V> existing = table[index];

        if (existing == null) {
            // No collision, just add the new entry
            table[index] = new Entry<>(key, value, null);
            size++;
        } else {
            // Collision handling (Chaining)
            // Iterate through the linked list at this bucket
            while (true) {
                if (Objects.equals(existing.key, key)) {
                    // Key already exists, update the value
                    existing.value = value;
                    return;
                }
                if (existing.next == null) {
                    // Reached end of list, add new entry
                    existing.next = new Entry<>(key, value, null);
                    size++;
                    return;
                }
                existing = existing.next;
            }
        }

        // 4. Check if resizing is needed
        if (size > threshold) {
            resize();
        }
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or null if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    public V get(K key) {
        int hash = hash(key);
        int index = getIndex(hash, table.length);

        Entry<K, V> current = table[index];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key key whose mapping is to be removed from the map
     */
    public void remove(K key) {
        int hash = hash(key);
        int index = getIndex(hash, table.length);

        Entry<K, V> current = table[index];
        Entry<K, V> prev = null;

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                if (prev == null) {
                    // Removing the head of the list
                    table[index] = current.next;
                } else {
                    // Removing from middle or end
                    prev.next = current.next;
                }
                size--;
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
        return size;
    }

    // --- Helper Methods ---

    /**
     * Computes a hash code for the key.
     * This implementation uses the key's hashCode() and applies a simple transformation
     * to spread the bits (similar to Java's HashMap).
     */
    private int hash(K key) {
        return (key == null) ? 0 : Math.abs(key.hashCode());
    }

    /**
     * Calculates the index in the table for a given hash.
     * Uses the modulo operator.
     */
    private int getIndex(int hash, int capacity) {
        return hash % capacity;
    }

    /**
     * Resizes the table when the threshold is exceeded.
     * Doubles the capacity and rehashes all existing entries.
     */
    private void resize() {
        int newCapacity = table.length * 2;
        Entry<K, V>[] newTable = new Entry[newCapacity];

        // Rehash all entries
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                Entry<K, V> next = entry.next; // Save next reference

                int hash = hash(entry.key);
                int index = getIndex(hash, newCapacity);

                // Insert into new table (at head of list)
                entry.next = newTable[index];
                newTable[index] = entry;

                entry = next;
            }
        }

        table = newTable;
        threshold = (int) (newCapacity * DEFAULT_LOAD_FACTOR);
    }

    /**
     * Internal class to represent a key-value pair.
     * It acts as a node in a linked list.
     */
    public static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    // --- Main method for testing ---
    public static void main(String[] args) {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();

        System.out.println("Putting values...");
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
        map.put("Four", 4); // This might cause a collision depending on hash codes

        System.out.println("Size: " + map.size()); // Expected: 4

        System.out.println("Getting values...");
        System.out.println("One: " + map.get("One"));   // Expected: 1
        System.out.println("Two: " + map.get("Two"));   // Expected: 2
        System.out.println("Five: " + map.get("Five")); // Expected: null

        System.out.println("Updating value for 'Two'...");
        map.put("Two", 22);
        System.out.println("Two: " + map.get("Two"));   // Expected: 22

        System.out.println("Removing 'Three'...");
        map.remove("Three");
        System.out.println("Size: " + map.size());      // Expected: 3
        System.out.println("Three: " + map.get("Three")); // Expected: null
    }
}
