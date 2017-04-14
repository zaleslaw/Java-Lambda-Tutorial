package Chapter_3_Migration_from_7_to_8;

import Chapter_3_Migration_from_7_to_8.beans.Department;
import Chapter_3_Migration_from_7_to_8.beans.Employee;

import java.util.*;

public class OldEnterpriseCode {

    public static void main(String[] args) {
        List<Employee> employees = hireFourEmployees();
        List<Department> deps = new ArrayList<>();


        for (Employee e: employees) {
            if(e.getLevel()>2){
                deps.add(e.getDepartment());
            }
        }


        Set<Department> distinctDeps = new HashSet<>();

        for (Department dep : deps) {
            distinctDeps.add(dep);
        }


        ArrayList<Department> resultList = new ArrayList<>(distinctDeps);

        Collections.sort(resultList, new Comparator<Department>() {
                    @Override
                    public int compare(Department obj1, Department obj2) {
                        if (obj1 == obj2) {
                            return 0;
                        }
                        if (obj1 == null) {
                            return -1;
                        }
                        if (obj2 == null) {
                            return 1;
                        }
                        return obj1.getName().compareTo(obj2.getName());

                    }
                }
        );


        for (Department dep : resultList) {
            System.out.println(dep.getName());
        }
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
