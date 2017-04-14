package Chapter_1_Lambda_Start.beans;


/**
 * Very bad designed class
 */
public class BusinessTaskUpdateCustomerAgeOnThread extends Thread implements BusinessTask {
    @Override
    public void run(){
        try {
            updateStateInDB();
            Thread.sleep(1000);
            System.out.println("Massive updates");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * In this class are mixed thread logic and business logic
     */
    @Override
    public void updateStateInDB() {
        System.out.println("We are read StateFromDB" );
    }
}
