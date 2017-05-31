package Chapter_6_Collectors;

import Chapter_3_Migration_from_7_to_8.YoungEnterpriseCode;
import Chapter_3_Migration_from_7_to_8.beans.Employee;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alexey_Zinovyev on 29-May-17.
 */
public class Ex_6_Averaging_Summing {
    public static void main(String[] args) {
        List<Employee> employees = YoungEnterpriseCode.hireFourEmployees();

        Double averageDepartmentNameLength = employees.stream()
                .map(Employee::getDepartment)
                .collect(Collectors.averagingInt(d -> d.getName().length()));

        System.out.println(averageDepartmentNameLength);


        Integer sumAge = employees.stream()
                .collect(Collectors.summingInt(d -> d.getAge()));
        // .collect(summingInt(Employee::getAge()));

        System.out.println(sumAge);

    }
}
