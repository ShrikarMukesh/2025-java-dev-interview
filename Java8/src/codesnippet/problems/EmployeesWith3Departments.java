package codesnippet.problems;

import codesnippet.dto.WorkRecord;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeesWith3Departments {
    public static void main(String[] args) {
        List<WorkRecord> records = List.of(
                new WorkRecord("John", "IT"),
                new WorkRecord("John", "Finance"),
                new WorkRecord("John", "HR"),
                new WorkRecord("Alice", "IT"),
                new WorkRecord("Alice", "HR"),
                new WorkRecord("Bob", "Finance"),
                new WorkRecord("David", "IT"),
                new WorkRecord("David", "Finance"),
                new WorkRecord("David", "HR"),
                new WorkRecord("David", "Admin")
        );

        Map<String, List<WorkRecord>> map = records.stream()
                .collect(Collectors.groupingBy(WorkRecord::getDepartment));

    }
}
