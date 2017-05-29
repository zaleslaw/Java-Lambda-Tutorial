package Chapter_4_Stream_Operations;

import java.util.Map;
import java.util.Set;

/**
 * Created by Alexey_Zinovyev on 29-May-17.
 */
public class Developer {
    private String name;
    private Map<String, Integer> skillMatrix;

    public Developer(String name, Map<String, Integer> skillMatrix) {
        this.name = name;
        this.skillMatrix = skillMatrix;
    }

    public Map<String, Integer> getSkillMatrix() {
        return skillMatrix;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "name='" + name + '\'' +
                ", skillMatrix=" + skillMatrix +
                '}';
    }
}
