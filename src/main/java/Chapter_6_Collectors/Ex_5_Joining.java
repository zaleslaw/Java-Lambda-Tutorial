package Chapter_6_Collectors;

import Chapter_3_Migration_from_7_to_8.YoungEnterpriseCode;
import Chapter_3_Migration_from_7_to_8.beans.Employee;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alexey_Zinovyev on 29-May-17.
 */
public class Ex_5_Joining {
    public static void main(String[] args) {

        final List<Employee> employees = YoungEnterpriseCode.hireFourEmployees();
        String names = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining());

        String  delimitedNames = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining("# "));

        String  prefixedNames = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(" $ ", "Our interns: ",  ". Pay them!"));

        System.out.println("Joined names:  "  + names);
        System.out.println("Joined,  delimited names:  "  + delimitedNames);
        System.out.println(prefixedNames);
    }
}
