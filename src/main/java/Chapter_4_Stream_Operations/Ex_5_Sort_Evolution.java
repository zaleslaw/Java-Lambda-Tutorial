package Chapter_4_Stream_Operations;

import Chapter_3_Migration_from_7_to_8.YoungEnterpriseCode;
import Chapter_3_Migration_from_7_to_8.beans.Employee;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparingInt;

/**
 * Magic of sorting
 */
public class Ex_5_Sort_Evolution {
    public static void main(String[] args) {
        List<Employee> employees = YoungEnterpriseCode.hireFourEmployees();
        employees.forEach(System.out::println);



        System.out.println("Sorting by skill level");
        Comparator<Employee> bySkillLevelDesc = new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o2.getLevel() - o1.getLevel();
            }
        };
        Collections.sort(employees, bySkillLevelDesc);
        employees.forEach(System.out::println);


        System.out.println("Sorting by age");
        Comparator<Employee> byAge = (o1, o2) -> o1.getAge() - o2.getAge();
        Collections.sort(employees, byAge);
        employees.forEach(System.out::println);


        System.out.println("Sorting by skill in reverse order");
        Comparator<Employee> bySkillReversed = Comparator.comparing(Employee::getSkill).reversed();
        Collections.sort(employees, bySkillReversed);
        employees.forEach(System.out::println);


        System.out.println("Complex sorting: by level and by name");
        Collections.sort(employees, comparingInt(Employee::getLevel).reversed().thenComparing(Employee::getName));
        employees.forEach(System.out::println);

    }
}
