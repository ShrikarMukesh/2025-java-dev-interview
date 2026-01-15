package singleton;

import java.util.HashMap;
import java.util.Map;

public enum ConfigManager {
    INSTANCE;
    private final Map<String, String> config = new HashMap<>();

    private void put(String key, String value){
        config.put(key, value);
    }

    public String get(String key) {
        return config.get(key);
    }

    //ConfigManager.INSTANCE.put("timeout", "30s");
    // Note: State inside the enum must still be thread-safe if mutated.
}
