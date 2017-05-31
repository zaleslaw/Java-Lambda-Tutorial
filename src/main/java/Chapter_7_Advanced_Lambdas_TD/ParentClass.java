package Chapter_7_Advanced_Lambdas_TD;

import java.util.Date;

public class ParentClass {

    static {
        zeroTime = new Date(0);
    }

    {
        famousMathematician = "Évariste Galois";
    }

    protected String famousMathematician;
    protected static Date zeroTime;

    public void setFamousMathematician(String famousMathematician) {
        this.famousMathematician = famousMathematician;
    }
}