package Chapter_1_Lambda_Start;

import Chapter_1_Lambda_Start.beans.BusinessTaskUpdateCustomerAge;

/**
 * Remove Runnable variable and join two task execution into one
 */
public class Ex_4_Anonymous_Lambda_Task {
    public static void main(String[] args) {

        executeTwoTasks(() -> {
            try {
                new BusinessTaskUpdateCustomerAge().updateStateInDB();
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
