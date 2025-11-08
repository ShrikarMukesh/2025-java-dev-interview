package genZ;

import stream.User;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupByExample {
    public static void main(String[] args) {
        List<User> userList = User.getUsers();
        Map<String, List<User>> result =
                userList.stream().collect(Collectors.groupingBy(User::getDept));
        System.out.println(result);
    }
}
