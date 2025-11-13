package com.interview.demo;

import java.util.HashMap;
import java.util.Map;

public class Interview {
    public static void main(String[] args) {
        String template = "Hello, {name}! Your order {orderId} is confirmed and will be delivered to {address}.";
        Map<String, String> values = new HashMap<>();
        values.put("name", "Vishal");
        values.put("orderId", "ORD98765");
        values.put("address", "Hyderabad, Telangana");
        String[] strArray = template.split(" ");
        //System.out.println(template.indexOf("name"));
        for (String str : strArray) {
            for (Map.Entry<String,String> entry : values.entrySet()) {
                String value = entry.getKey();
                System.out.println();str.contains(value);
            }
        }
    }
}
