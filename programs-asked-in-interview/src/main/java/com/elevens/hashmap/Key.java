package com.elevens.hashmap;

import java.util.HashMap;

class Key {
    private String name;

    public Key(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return 0;  // Force bucket 0 for every key
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj; // Different objects are never equal
    }

    @Override
    public String toString() {
        return name;
    }
}

class Test {
    public static void main(String[] args) {
        HashMap<Key, Integer> map = new HashMap<>();

        map.put(new Key("A"), 1);
        map.put(new Key("B"), 2);
        map.put(new Key("C"), 3);

        System.out.println(map);
    }
}
