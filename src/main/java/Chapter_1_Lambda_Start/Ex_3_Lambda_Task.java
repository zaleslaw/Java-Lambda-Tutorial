package Chapter_1_Lambda_Start;

import Chapter_1_Lambda_Start.beans.BusinessTaskUpdateCustomerAge;

/**
 * Change anonymous class implements Runnable to lambda
 */
public class Ex_3_Lambda_Task {
    public static void main(String[] args) {

        Runnable task = () -> {
            try {
                new BusinessTaskUpdateCustomerAge().updateStateInDB();
                Thread.sleep(1000);
                System.out.println("Massive updates");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };


        executeTask(task);
        executeTask(task);

    }

    private static void executeTask(Runnable threadLogic) {
        new Thread(threadLogic).start();
    }
}
