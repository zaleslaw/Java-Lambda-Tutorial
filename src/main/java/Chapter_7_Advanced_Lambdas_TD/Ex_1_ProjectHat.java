package Chapter_7_Advanced_Lambdas_TD;


import Chapter_3_Migration_from_7_to_8.beans.Department;
import Chapter_3_Migration_from_7_to_8.beans.Employee;
import Chapter_7_Advanced_Lambdas_TD.ex_1.Hirable;
import Chapter_7_Advanced_Lambdas_TD.ex_1.Project;

public class Ex_1_ProjectHat {
    public static void main(String[] args) {

        Department hrDepartment = new Department("HR");
        Department backendDepartment = new Department("Backend");


        Employee you =  new Employee(18, "Petr", "Java", 1, null);
        Employee hr =  new Employee(25, "Olga", "HR", 3, hrDepartment);
        Employee yourBoss = new Employee(38, "John", "Delphi", 4, backendDepartment);


        /*Project project = new Hirable() {
            @Override
            public Project interview(Employee candidate, Employee hr, Employee teamLead) {
                return new Project(candidate.getSkill() + " " + hr.getAge() + " " + teamLead.getDepartment());
            }

        }.interview(you, hr, yourBoss);*/


        Project project = ((Hirable) (candidate, hr1, teamLead) -> new Project(candidate.getSkill() + " " + hr1.getAge() + " " + teamLead.getDepartment())).interview(you, hr, yourBoss);

        System.out.println(project.getName());


    }
}
