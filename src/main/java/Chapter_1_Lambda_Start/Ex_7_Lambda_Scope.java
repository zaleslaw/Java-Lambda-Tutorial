package Chapter_1_Lambda_Start;

import Chapter_1_Lambda_Start.beans.ParentClass;

import java.util.Date;
import java.util.function.Predicate;


/**
 * Strange example: TODO: Fix all explanations
 */
public class Ex_7_Lambda_Scope extends ParentClass {

    private double e = Math.E;
    private static double pi = Math.PI;


    public static void main(String[] args) {
        Predicate<Integer> predicate = (date) -> {
            if (zeroTime.compareTo(new Date(date)) == pi) {
                return true;
            }
            return false;
        };


        pi = 0; //it's not effectively final
        System.out.println(predicate.test(0));

        Ex_7_Lambda_Scope e = new Ex_7_Lambda_Scope();
        e.setFamousMathematician("Galua");
        int addition = 1;
        e.testMethod(addition);
        addition = 12;


    }

    public void testMethod(int addition){
        Predicate<String> predicate = (name) -> {
            //addition = 1;
            e = 0;
            e = addition;
            pi+= addition;
            if (famousMathematician.equals(name)) {

                return true;
            }
            return false;
        };

        System.out.println(predicate.test("Koshi"));
    }
}


