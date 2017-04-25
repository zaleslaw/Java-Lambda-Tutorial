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


6. **First stream**

   Let's start from the collection and print out it

   ```java

        List<String> list = Arrays.asList("usa", " RUSSIA ", "gerMANY", "JApAN");

        // before Java 5
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }


        // after Java 5
        for (String item: list) {
            System.out.println(item);
        }

        // Nothing interesting in Java 8
        list.stream().forEach((String s) -> {System.out.println(s);});

   ```

   Ok, let's do something more complex

   ```java
        // transform all string in lower case, trim them and skip strings with 'j'

        for (String item: list) {
            String newItem = item.toLowerCase().trim();
            if(item.contains("j")){
                continue;
            }
            System.out.println(newItem);
        }

        // or

        list
                .stream()
                .map(e -> e.toLowerCase().trim())
                .filter(p -> !p.contains("j"))
                .forEach(e -> System.out.println(e));

   ```


7. **Lazy stream**

   The Stream was born... but it is dead

   ```java

    Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5, 5, 4)
                   .stream()
                   .filter(e -> e > 4)
                   .map(b -> 1);

    ```


    How to fix it? What's the problem?

    Yes, there's no terminal operation

    Add this to print out mutations

    ```java
     stream.forEach(System.out::println); // <---- TERMINAL OPERATION

    ```

8. **Infinite Stream**

   It's very useful feature in tests - to run it with infinite Stream of something

   ```java

           IntStream.iterate(0, i -> i + 2)
                   .forEach(e -> {
                       try {
                           Thread.sleep(100);
                           System.out.println(e);
                       } catch (InterruptedException e1) {
                           e1.printStackTrace();
                       }

                   });

           System.out.println("It won't be printed");

   ```

   How to limit this generator?

   ```java

    IntStream.iterate(0, i -> i + 2)
                   .limit(10) // add this operation to limit infinite stream
                   .forEach(e -> {
                       try {
                           Thread.sleep(100);
                           System.out.println(e);
                       } catch (InterruptedException e1) {
                           e1.printStackTrace();
                       }

                   });

           System.out.println("It won't be printed"); // <---- it will be printed

   ```

9. **Unsorted stream**

  Assume, we have a stream of doubles which should be sorted

  ```java

   Arrays.asList(2, 3 , 4.2, 13, -1, 3, 8.8)
                  .stream()
                  .sorted()
                  .forEach(e -> System.out.println(e));

  ```
  But we catch an exception java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.Double
  Why? How to fix?

  Be careful with types, prepare data correctly

  ```java

  Arrays.asList(new Double(2), new Double(3) , 4.2, new Double(13), new Double(-1), new Double(3), 8.8)
                  .stream()
                  .sorted()
                  .forEach(e -> System.out.println(e));

  ```

10. **Stream sources**

  Stream sources could be very different. Streams are smart and it can extract metadata from source and keep these properties.

  ```java

          List<String> list = Arrays.asList("Petrograd", "Petersburg", "Leningrad", "Piter");
          Stream<String> s1 = list.stream(); // sized , ordered


          HashSet<String> set = new HashSet<>(list);
          Stream <String> s2 = set.stream (); // sized , distinct

          TreeSet<String> orderedSet = new TreeSet<>(set);
          Stream <String> s3 = set.stream(); // sized , distinct // sorted , ordered*/


          IntStream ints = Arrays.stream(new int[] {1, 2, 3});
          Stream Ints = Arrays.stream(new Integer[] {1, 2, 3});

          Stream<Boolean> flags = Stream.of(true, true, true);

          //flags = Stream.builder().add(true).add(false).build();// play with types and values of different types

          LongStream s = LongStream.range (0, 1000000);

  ```

11. **Migration from Java 7 to Java 8**

  Imagine the Enterprise application with two entities: Department and Employee

  ```java
  @Data
  public class Department {

      private String name;

  // and

  @Data
  public class Employee {

      private int age;
      private String name;
      private String skill;
      private int level;
      private Department department;

  ```

  HR Department hired for us a few developers

  ```java

    private static List<Employee> hireFourEmployees() {

          Department financialDepartment = new Department("Financial");
          Department mobileDepartment = new Department("Mobile");

          return Arrays.asList(
                  new Employee(18, "Petr", "Java", 1, financialDepartment),
                  new Employee(25, "Olga", ".NET", 3, financialDepartment),
                  new Employee(38, "John", "Delphi", 4, mobileDepartment),
                  new Employee(43, "Sergey", "Java", 4, mobileDepartment));
      }
  ```

  Also we decided move our old business logic from Java 7 to lambdas and Streams
  The main idea of this logic: find all departments with skilled employees, distinct them, sort and print out.
  Moreover, the report with average age of employees should be print out too.


  ```java
          List<Employee> employees = hireFourEmployees();
          List<Department> deps = new ArrayList<>();


          for (Employee e: employees) {
              if(e.getLevel()>2){
                  deps.add(e.getDepartment());
              }
          }


          Set<Department> distinctDeps = new HashSet<>();

          for (Department dep : deps) {
              distinctDeps.add(dep);
          }


          ArrayList<Department> resultList = new ArrayList<>(distinctDeps);

          Collections.sort(resultList, new Comparator<Department>() {
                      @Override
                      public int compare(Department obj1, Department obj2) {
                          if (obj1 == obj2) {
                              return 0;
                          }
                          if (obj1 == null) {
                              return -1;
                          }
                          if (obj2 == null) {
                              return 1;
                          }
                          return obj1.getName().compareTo(obj2.getName());

                      }
                  }
          );


          for (Department dep : resultList) {
              System.out.println(dep.getName());
          }


          int sum = 0;
          int counter = 0;
          for(Employee employee : employees) {
              sum += employee.getAge();
              counter++;
          }

          System.out.println("Average age is " + (sum/counter));
  ```

  Let's migrate step by step to the new Universe

  ```java

   List<Employee> employees = hireFourEmployees();

          employees.stream()
                  .filter(e -> e.getLevel() > 2)
                  .map(e -> e.getDepartment())
                  .distinct()
                  .sorted(Comparator.comparing(e -> e.getName()))
                  .forEach(e -> System.out.println(e.getName()));

          //Problem
          //System.out.println("Average age is " + employees.stream().map(e-> e.getAge()).avg()); //??? Where's average?

          //Solution
          System.out.println("Average age is " + employees.stream().mapToInt(Employee::getAge).average().getAsDouble());

          //or
          System.out.println(employees.stream().mapToInt(Employee::getAge).summaryStatistics());

  ```