package Chapter_1_Lambda_Start;


import Chapter_1_Lambda_Start.beans.BusinessTask;
import Chapter_1_Lambda_Start.beans.BusinessTaskUpdateCustomerAge;

/**
 * Add default method to BusinessTask interface and try to call this method from the client code
 */
public class Ex_5_Anonymous_Lambda_Task_With_Default_Method {
    public static void main(String[] args) {

        executeTwoTasks(() -> {
            try {


                // STEP 1

                /*new BusinessTask() {
                    @Override
                    public void updateStateInDB() {
                        // you can skip this implementation
                    }
                }.updateStateInDBByDefault();*/

                // STEP 2 But this is too long and unclear, replace with lambda

                ((BusinessTask) () -> {
                }).updateStateInDBByDefault();


                // STEP 3 Call this method from the child class 'BusinessTaskUpdateCustomerAge'
                new BusinessTaskUpdateCustomerAge().updateStateInDBByDefault();

                Thread.sleep(1000);
                System.out.println("Massive updates");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


    }

    private static void executeTwoTasks(Runnable threadLogic) {
        new Thread(threadLogic).start();
        new Thread(threadLogic).start();
    }
}
