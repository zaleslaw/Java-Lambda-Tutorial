package Chapter_1_Lambda_Start;

/**
 * Final or effectively final variables
 */
public class Ex_6_DirtyLambdas {
    public static void main(String[] args) throws InterruptedException {

        double pi = 3.14; // final can be removed...but it will be 'effectively' final for Java compiler


        //STEP 1: Let's rectify the PI number
        Runnable normalTask = () -> {
            double rectification = 0.0015;
            System.out.println("Result " + (pi + rectification));
        };

        new Thread(normalTask).start();


        // STEP 2: Let's add some internal effect on the PI number
/*
        Runnable abnormalTask = () -> {
            pi+= 0.0015;
            System.out.println("Result " + pi);
        };
*/

        // STEP 3: Problem: remove final modifier on 'pi' variable, change it after .join() call
        // Lambda will be not compiled due to 'pi' limitation: it should be effectively final

/*        Runnable problemTask = () -> {
            double rectification = 0.0015;
            System.out.println("Result " + (pi + rectification));
        };

        Thread t = new Thread(problemTask);
        t.start();
        t.join();
        pi+=0.000092;
        System.out.println(pi);*/

        // STEP 4: Solution

/*        double finalPi = pi; // should be copied out of lambda
        Runnable coolTask = () -> {
            double rectification = 0.0015;
            System.out.println("Result " + (finalPi + rectification));
        };

        Thread t = new Thread(coolTask);
        t.start();
        t.join();
        pi+=0.000092;
        System.out.println(pi);*/

        // 3.14_00_92 WOW!!! Changes from lambda were lost!! Why???

        // STEP 5: Let's hack it according IDEA tip with one element array
        /*final double[] piArray = {3.14};
        Runnable hackedTask = () -> {
            piArray[0] += 0.0015;
            System.out.println("Result " + piArray[0]);
        };
        new Thread(hackedTask).start();*/



    }
}
