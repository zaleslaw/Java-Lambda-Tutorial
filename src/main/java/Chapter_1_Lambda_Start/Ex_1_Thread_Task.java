package Chapter_1_Lambda_Start;


import Chapter_1_Lambda_Start.beans.BusinessTaskUpdateCustomerAgeOnThread;

/**
 * Run two tasks and call public method from Business Task interface: no limits between infrastructure code and business logic
 */
public class Ex_1_Thread_Task {
    public static void main(String[] args) {

        BusinessTaskUpdateCustomerAgeOnThread task1 = new BusinessTaskUpdateCustomerAgeOnThread();
        BusinessTaskUpdateCustomerAgeOnThread task2 = new BusinessTaskUpdateCustomerAgeOnThread();
        task1.start();
        task2.start();
        task2.updateStateInDB();
        task2.run();

    }
}
