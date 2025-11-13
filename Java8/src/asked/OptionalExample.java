package asked;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OptionalExample {
    public static void main(String[] args) {
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put("Shrikar", 32);
        for (Map.Entry entry : stringIntegerHashMap.entrySet()) {
            stringIntegerHashMap.put("Shrikar", 33);
            stringIntegerHashMap.put("Dinesh" , 89);
            System.out.println(entry);
        }

    }

}
