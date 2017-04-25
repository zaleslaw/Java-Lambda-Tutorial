package Chapter_3_Migration_from_7_to_8.beans;

public class Department {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Department(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                '}';
    }

}