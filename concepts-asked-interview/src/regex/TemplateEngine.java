package regex;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateEngine {
    public static void main(String[] args) {
        String str = "My name is {name} I am from {place} and working in {company}";
        //Pattern pattern = Pattern.compile("\\{(.+?)\\}");
        Pattern pattern = Pattern.compile("\\{(.+?)\\}");
        Matcher matcher = pattern.matcher(str);
        StringBuilder sb = new StringBuilder();
        Map<String, String> values = Map.of(
                "name", "Shrikar",
                "place", "Bangalore",
                "company", "Cognizant"
        );
        while (matcher.find()){
            String key = matcher.group(1);
            // matcher.group(0) is the full match: "{name}"
            // matcher.group(1) is the first captured group: "name"
            System.out.println("Found key: " + matcher.group(1));
            System.out.println("Found key: " + matcher.group(0));
            String replacement = values.getOrDefault(key, matcher.group(0));
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        System.out.println(sb.toString());
    }
}
