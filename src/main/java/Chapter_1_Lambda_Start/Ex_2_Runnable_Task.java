package Chapter_1_Lambda_Start;

import Chapter_1_Lambda_Start.beans.BusinessTaskUpdateCustomerAge;

/**
 * Move thread running code into method executeTask
 */
public class Ex_2_Runnable_Task {
    public static void main(String[] args) {

        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    new BusinessTaskUpdateCustomerAge().updateStateInDB();
                    Thread.sleep(1000);
                    System.out.println("Massive updates");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };


        executeTask(task);
        executeTask(task);

    }

    private static void executeTask(Runnable threadLogic) {
        new Thread(threadLogic).start();
    }
}
