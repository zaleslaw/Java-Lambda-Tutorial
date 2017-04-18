package Chapter_3_Migration_from_7_to_8;

import Chapter_3_Migration_from_7_to_8.beans.Department;
import Chapter_3_Migration_from_7_to_8.beans.Employee;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class YoungEnterpriseCode {

    public static void main(String[] args) {
        List<Employee> employees = hireFourEmployees();

        employees.stream()
                .filter(e -> e.getLevel() > 2)
                .map(e -> e.getDepartment())
                .distinct()
                .sorted(Comparator.comparing(e -> e.getName()))
                .forEach(e -> System.out.println(e.getName()));
    }

    private static List<Employee> hireFourEmployees() {

        Department financialDepartment = new Department("Financial");
        Department mobileDepartment = new Department("Mobile");

        return Arrays.asList(
                new Employee(18, "Petr", "Java", 1, financialDepartment),
                new Employee(25, "Olga", ".NET", 3, financialDepartment),
                new Employee(38, "John", "Delphi", 4, mobileDepartment),
                new Employee(43, "Sergey", "Java", 4, mobileDepartment));
    }
}


