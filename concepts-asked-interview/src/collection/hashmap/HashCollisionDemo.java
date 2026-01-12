package collection.hashmap;

public class HashCollisionDemo {

    // A class with a deliberately bad hashCode implementation to force collisions
    static class BadHashKey {
        String name;
        int id;

        public BadHashKey(String name, int id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public int hashCode() {
            // Always return the same hash code to force all entries into the same bucket
            return 1;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            BadHashKey that = (BadHashKey) obj;
            return id == that.id && name.equals(that.name);
        }

        @Override
        public String toString() {
            return "Key{" + name + "-" + id + "}";
        }
    }

    public static void main(String[] args) {
        CustomHashMap<BadHashKey, String> map = new CustomHashMap<>();

        BadHashKey key1 = new BadHashKey("KeyOne", 1);
        BadHashKey key2 = new BadHashKey("KeyTwo", 2);
        BadHashKey key3 = new BadHashKey("KeyThree", 3);

        System.out.println("Adding entries with colliding hash codes...");
        
        // All these keys have hashCode() = 1, so they will go to the same bucket (index 1)
        map.put(key1, "Value 1");
        System.out.println("Added " + key1 + " -> Value 1");
        
        map.put(key2, "Value 2");
        System.out.println("Added " + key2 + " -> Value 2");
        
        map.put(key3, "Value 3");
        System.out.println("Added " + key3 + " -> Value 3");

        System.out.println("\nRetrieving values (should work despite collisions):");
        System.out.println("Get " + key1 + ": " + map.get(key1));
        System.out.println("Get " + key2 + ": " + map.get(key2));
        System.out.println("Get " + key3 + ": " + map.get(key3));

        System.out.println("\nMap size: " + map.size());
        
        // Verify that they are indeed distinct entries
        System.out.println("\nUpdating one of the colliding keys...");
        map.put(key2, "Updated Value 2");
        System.out.println("Get " + key2 + ": " + map.get(key2));
        System.out.println("Get " + key1 + ": " + map.get(key1)); // Should remain unchanged
    }
}
