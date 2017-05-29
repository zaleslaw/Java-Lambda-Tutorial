package Chapter_5_Collectors;

import Chapter_3_Migration_from_7_to_8.YoungEnterpriseCode;
import Chapter_3_Migration_from_7_to_8.beans.Department;
import Chapter_3_Migration_from_7_to_8.beans.Employee;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;

/**
 * Created by Alexey_Zinovyev on 29-May-17.
 */
public class Ex_7_Max_Min {
    public static void main(String[] args) {
        List<Employee> employees = YoungEnterpriseCode.hireFourEmployees();

        // Mix optional and static imports
        Optional<Department> result = employees.stream()
                .map(Employee::getDepartment)
                .collect(maxBy(comparing(Department::getName)));

        System.out.println("Max is " + result.get());


        result = employees.stream()
                .map(Employee::getDepartment)
                .collect(minBy(comparing(Department::getName)));

        System.out.println("Min is " + result.get());

    }
}
