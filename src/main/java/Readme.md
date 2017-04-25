1. **Run Ugly Business Task**

   Define interface BusinessTask

   ```java
   public interface BusinessTask {
       void updateStateInDB();
   }
   ```

   Also let's implement it with VERY UGLY class

   ```java

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
   ```

   Create two task and run them with threads

   ```java
           BusinessTaskUpdateCustomerAgeOnThread task1 = new BusinessTaskUpdateCustomerAgeOnThread();
           BusinessTaskUpdateCustomerAgeOnThread task2 = new BusinessTaskUpdateCustomerAgeOnThread();
           task1.start();
           task2.start();
           task2.updateStateInDB();
           task2.run();
   ```



2. **Cut logic from infrastructure logic**

   Implement interface BusinessTask with other class

   ```java
   public class BusinessTaskUpdateCustomerAge implements BusinessTask {
       @Override
       public void updateStateInDB() {
           System.out.println("We are read StateFromDB" );
       }
   }
   ```

   Extract logic to Runnable and run this AIC with Thread

   ```java
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
   ```

   As you can see, IDEA tries to help you revert Runnable to lambda

3. **First lambda**

   Change anonymous class implementing Runnable to lambda

   ```java
          Runnable task = () -> {
               try {
                   new BusinessTaskUpdateCustomerAge().updateStateInDB();
                   Thread.sleep(1000);
                   System.out.println("Massive updates");
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           };
   ```

   We can dive deeper and remove mention about Runnable in our code.

   ```java

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

   ```

   Also, the logic was separated from infrastructure code and can be changed very easy

4. **Default methods in interfaces**

   Let's add method with implementation to BusinessTask interface

   ```java

    default void updateStateInDBByDefault() {
        System.out.println("I'm non-abstract method in interface!!!");
    }
   ```

   Let's call this method in client code

   ```java

                   new BusinessTask() {
                       @Override
                       public void updateStateInDB() {
                           // you can skip this implementation
                       }
                   }.updateStateInDBByDefault();

   ```

   Of course, it can be replaced with lambda

   ```java
   ((BusinessTask) () -> {}).updateStateInDBByDefault();
   ```

   And it can be called from its implementation, BusinessTaskUpdateCustomerAge

   ```java
   //it can be overridden or not
   new BusinessTaskUpdateCustomerAge().updateStateInDBByDefault();
   ```

5. **Dirty lambdas and their scopes**

   So, imagine indy variable pi with value 3.14 and one small Runnable task to change that

   ```java
           double pi = 3.14; // final can be removed...but it will be 'effectively' final for Java compiler


           //STEP 1: Let's rectify the PI number
           Runnable normalTask = () -> {
               double rectification = 0.0015;
               System.out.println("Result " + (pi + rectification));
           };

           new Thread(normalTask).start();

   ```

   Let's add some internal effect on the PI number

   ```java
           Runnable abnormalTask = () -> {
               pi+= 0.0015;
               System.out.println("Result " + pi);
           };

   ```

   Compiler tell us that all variables (from outer scope) which are used in lambda should be final or effectively final

   It doesn't depends on place where you will change the variable value. Compiler is smart enough.

   ```java

           Runnable problemTask = () -> {
               double rectification = 0.0015;
               System.out.println("Result " + (pi + rectification));
           };

           Thread t = new Thread(problemTask);
           t.start();
           t.join();

           pi+=0.000092;  // special place
           System.out.println(pi);

   ```

   Possible solution is the immutable counterpart of data which are used in lambda

   ```java
           double finalPi = pi; // should be copied out of lambda
           Runnable coolTask = () -> {
               double rectification = 0.0015;
               System.out.println("Result " + (finalPi + rectification));
           };

           Thread t = new Thread(coolTask);
           t.start();
           t.join();

           pi+=0.000092;
           System.out.println(pi);
   ```

   Ok, but what about pi variable (it lost its updates from lambda). Could we hack it?
   Yes, we could. With one-element array (works correctly sometimes)

   ```java

           final double[] piArray = {3.14};
           Runnable hackedTask = () -> {
               piArray[0] += 0.0015;
               System.out.println("Result " + piArray[0]);
           };
           new Thread(hackedTask).start();

   ```

   Don't do that (I hate tips from IDEA)