package codesnippet.problems;

import codesnippet.dto.CharCategory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupCharacters {
    public static void main(String[] args) {
        List<Character> chars = Arrays.asList(
                'A', 'b', '3', 'Z', 'x', '#', '7', 'm', '@'
        );

        Map<Object, List<Character>> map = chars.stream()
                .collect(Collectors.groupingBy(
                        character -> getCharCategory(character)
                ));

        System.out.println(map);
    }


    private static Object getCharCategory(Character character) {

        if (Character.isUpperCase(character)) return CharCategory.UPPERCASE;
        if (Character.isLowerCase(character)) return CharCategory.LOWERCASE;
        if (Character.isDigit(character)) return CharCategory.DIGIT;
        else return CharCategory.OTHER;
    }
}
