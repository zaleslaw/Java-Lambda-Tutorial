package Chapter_1_Lambda_Start.beans;

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