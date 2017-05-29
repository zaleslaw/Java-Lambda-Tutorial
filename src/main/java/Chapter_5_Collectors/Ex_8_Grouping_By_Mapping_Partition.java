package Chapter_5_Collectors;

import Chapter_3_Migration_from_7_to_8.YoungEnterpriseCode;
import Chapter_3_Migration_from_7_to_8.beans.Department;
import Chapter_3_Migration_from_7_to_8.beans.Employee;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

/**
 * Created by Alexey_Zinovyev on 29-May-17.
 */
public class Ex_8_Grouping_By_Mapping_Partition {
    public static void main(String[] args) {
        List<Employee> employees = YoungEnterpriseCode.hireFourEmployees();


        // Step 1: Group by Department
        Map<Department, List<Employee>> groupedByDepartment = employees.stream()
                .collect(groupingBy(Employee::getDepartment));

        System.out.println(groupedByDepartment);

        // Step 2: Group and count employees by department
        Map<Department, Long> countByDepartment = employees.stream()
                .collect(groupingBy(Employee::getDepartment, Collectors.counting()));

        System.out.println(countByDepartment);

        // Step 3: Join and print out skills of employees grouped by theirs departments
        Map<Department, String> skillsByDepartment = employees.stream()
                .collect(groupingBy(Employee::getDepartment, Collectors.mapping(Employee::getSkill, Collectors.joining(" & "))));

        System.out.println(skillsByDepartment);

        // Step 4: Partition developer names by binary criteria: level
        Map<Boolean, String> groupByLevel = employees.stream()
                .collect(partitioningBy(e -> e.getLevel() > 2, mapping(Employee::getName, Collectors.joining("_"))));

        System.out.println(groupByLevel);

    }
}
