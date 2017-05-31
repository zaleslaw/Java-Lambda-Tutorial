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
  
12. **Functional Interfaces**

  ```java
  
        Predicate<Integer> isPrime = number -> isEven(number);

        Consumer<Integer> missageConsumer = missage -> System.out.println("Winnie Pooh's missage is " + missage);

        Function<String, Department> strangeConstructor = name -> new Department(name);

        Function<Integer, Integer> addOne = a -> a + 1;

        System.out.println(addOne.apply(1));

        Supplier<Boolean> random = () -> ThreadLocalRandom.current().nextBoolean();

        System.out.println(random.get());

        Arrays.asList(1,2,3,4,5).stream().map(addOne).filter(isPrime).forEach(missageConsumer);
        
         private static boolean isEven(Integer number) {
                if(number % 2 == 0) {
                    return  true;
                }
                return false;
            }
    ```
    
    
13. **Method references**  


  ```java
          Function<String, Department> strangeConstructor = name -> new Department(name);
  
          Function<String, Department> yetOneStrangeConstructor = Department::new; // implicit constructor binding
  
          Arrays.asList("Financial", "Mobile").stream().map(yetOneStrangeConstructor).forEach(System.out::println);
  
  
          //A few MR examples
          BiFunction<String, CharSequence, Boolean> contains = String::contains;
  
          System.out.println(contains.apply("Russia", "Alaska"));
  
          final DoubleUnaryOperator doubleConsumer = Math::abs;
  
          System.out.println(doubleConsumer.applyAsDouble(-3));
  
  
          final Function<Department, String> getName = Department::getName;
  
          System.out.println(getName.apply(strangeConstructor.apply("QA Department"))); // apply(apply(..))
  
  
          //Problem: unclear type in the right - you should specify T
          //final BiFunction<Collection<? extends T>, Comparator<? super T>, T> collectionComparatorTBiFunction = Collections::max;
  
          //Solution
          Function<List<Integer>, Integer> maxElem = Collections::max;
  
          //Or equivalent
          Function<List<Integer>, Integer> maxElemLambda = (numbers) -> Collections.max(numbers);

  ```
  
14. **Composition**  
  
  ```java
  
          List<Integer> fibonacciNumbers = Arrays.asList(1,1,2,3,5,8,13,21);
  
          // Step 1
          fibonacciNumbers.stream().map(e -> e + 1).forEach(System.out::println);
  
          // Step 2: Extract the function
          final Function<Integer, Integer> addOne = e -> e + 1;
          fibonacciNumbers.stream().map(addOne).forEach(System.out::println);
  
          // Step 3: Pipe two functions in direct order
          Function<Integer, Integer> multiplyTen = e -> e * 10;
          fibonacciNumbers.stream().map(addOne.andThen(multiplyTen)).forEach(System.out::println);
  
  
          // Step 4: Pipe two functions in opposite order
          fibonacciNumbers.stream().map(addOne.compose(e-> e*10)).forEach(System.out::println);
  
          // Step 5: Use custom compose
          fibonacciNumbers.stream().map(compose(addOne, multiplyTen)).forEach(System.out::println);
          
          
          public static <Integer> Function<Integer, Integer> compose(Function<Integer,Integer> f, Function<Integer,Integer> g) {
             return x -> f.apply(g.apply(x));
          }         

  ```


15. **Currying**  
  
  ```java
  
  
          // Step 1: Calculate per diem for our IT-week speakers
          CalculatePerDiem calculatePerDiem = new CalculatePerDiem();
          Function<Integer, Double> curriedByFirstArgument = calculatePerDiem.curryFirstArgument(57.16);
  
          System.out.println(curriedByFirstArgument.apply(5));
          System.out.println(curriedByFirstArgument.apply(3));
          System.out.println(curriedByFirstArgument.apply(10));
  
          // Step 2: Calculate per diem for different exchange rates
          Function<Double, Double> curriedBySecondArgument = calculatePerDiem.currySecondArgument(10);
          System.out.println(curriedBySecondArgument.apply(56.12));
          System.out.println(curriedBySecondArgument.apply(61.63));
  
  
  class CalculatePerDiem implements CurriedBiFunction<Double, Integer, Double>{
  
      private static final Double perDiemRate = 10.15;
  
      @Override
      public Double apply(Double dollarExchangeRate, Integer amountOfDays) {
          return dollarExchangeRate * amountOfDays * perDiemRate;
      }
  }
  
  
  @FunctionalInterface
  interface CurriedBiFunction<T, U, R>
          extends BiFunction<T, U, R> {
  
      default Function<U, R> curryFirstArgument(T t) {
          return u -> apply(t, u);
      }
  
      default Function<T, R> currySecondArgument(U u) {
          return t -> apply(t, u);
      }
  }

  ```

16. **Operations**  
  
  ```java
  
  public class Developer {
      private String name;
      private Map<String, Integer> skillMatrix; @Getter
      
      
        List<Integer> ints = Arrays.asList(1, -1, 2, -2, 3, -3);

        // Step 1: Transformation
        ints.stream().map(e -> Math.abs(e)).forEach(System.out::println);

        // Step 2: Method reference usage
        ints.stream().map(Math::abs).forEach(System.out::println);

        // Step 3: Specialized map
        System.out.println(ints.stream().mapToInt(Math::abs).max().getAsInt());

        // Step 4: More interesting features
        System.out.println(ints.stream().mapToDouble(Math::cos).summaryStatistics());      


        // FLAT MAP
        
        Map<String,Integer> backendSkillMatrix = new HashMap<>();
        backendSkillMatrix.put("Java", 3);
        backendSkillMatrix.put("Scala", 2);
        backendSkillMatrix.put("Kotlin", 2);
        Developer backendDev = new Developer("Teodor", backendSkillMatrix);


        Map<String,Integer> frontendSkillMatrix = new HashMap<>();
        frontendSkillMatrix.put("React", 80);
        frontendSkillMatrix.put("Angular 10", 1);
        frontendSkillMatrix.put("Kotlin", 4);
        Developer frontendDev = new Developer("Dinesh", frontendSkillMatrix);


        List<Developer> projectTeam = new ArrayList<>();
        projectTeam.add(backendDev);
        projectTeam.add(frontendDev);

        // Step 1: Print all skills with level more than 1

        List<String> result = projectTeam.stream()
                .map(e -> e.getSkillMatrix().entrySet())
                .flatMap(set -> set.stream())
                //.peek(System.out::println)
                .filter(item -> item.getValue() > 1)
                .map(item -> item.getKey())
                .distinct()
                .collect(Collectors.toList());

        System.out.println(result);




        // Step 2: Specialized FlatMap: sum of distinct values only
        String[][] arrayOfArrays = {{"1", "2"}, {"3", "4"}, {"1", "4", "1"}};

        LongStream longStream = Arrays.stream(arrayOfArrays)
                .flatMapToLong(innerArray -> Arrays.stream(innerArray)
                        .mapToLong(Long::new).distinct()); // <--- it doesn't solve the problem

        /*// Step 3: Possible solution
        longStream = Arrays.stream(arrayOfArrays)
                .flatMapToLong(innerArray -> Arrays.stream(innerArray)
                        .mapToLong(Long::new))
                .distinct();*/

        // Step 4: peek for debug purposes
        System.out.println(longStream.peek(System.out::println).sum());

        // Step 5: Print out all Strings by chars
        Stream.of("Scala", "Java",  "Kotlin")
                .flatMap(name ->  IntStream.range(0, name.length())
                        .mapToObj(name::charAt))
                .forEach(System.out::println);        
        

       // REDUCE POWER
       
        Stream<Integer> ints = Stream.of(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17);
        int sum = 0;
        //ints.forEach(e -> { sum+= e});
        System.out.println(sum);

        //ok, maybe through array

        int[] sumResult = {0};
        //ints.forEach(e -> sumResult[0] += e);
        System.out.println(sumResult); // typical mistake, we forgot about real type

        // yeah!!!!!

        /*ints.parallel()
                .forEach(e -> sumResult[0] += e);*/
        System.out.println(sumResult[0]); // repeat 10 times and you print different results sometimes
        // WHY???? Who knows that?


        System.out.println(ints.reduce(0, (x,y) -> x + y));

        // for primitive
        System.out.println(ints.mapToInt(i -> i).sum());
        // with collectors
        System.out.println(ints.collect(summingInt(i->i.intValue())));
        // with method references
        System.out.println(ints.reduce(0, Integer::sum));
        System.out.println(ints.collect(summingInt(Integer::intValue)));       
        
        
        
        //SORT POWER
        
        List<Employee> employees = YoungEnterpriseCode.hireFourEmployees();
        employees.forEach(System.out::println);



        System.out.println("Sorting by skill level");
        Comparator<Employee> bySkillLevelDesc = new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o2.getLevel() - o1.getLevel();
            }
        };
        Collections.sort(employees, bySkillLevelDesc);
        employees.forEach(System.out::println);


        System.out.println("Sorting by age");
        Comparator<Employee> byAge = (o1, o2) -> o1.getAge() - o2.getAge();
        Collections.sort(employees, byAge);
        employees.forEach(System.out::println);


        System.out.println("Sorting by skill in reverse order");
        Comparator<Employee> bySkillReversed = Comparator.comparing(Employee::getSkill).reversed();
        Collections.sort(employees, bySkillReversed);
        employees.forEach(System.out::println);


        System.out.println("Complex sorting: by level and by name");
        Collections.sort(employees, comparingInt(Employee::getLevel).reversed().thenComparing(Employee::getName));
        employees.forEach(System.out::println);       


  ```
  
17. **Collectors**  
  
  ```java
        List<Employee> employees = hireFourEmployees();

        List<Object> departmentNames = employees.stream()
                .filter(e -> e.getLevel() > 2)
                .map(e -> e.getDepartment())
                .distinct()
                .sorted(Comparator.comparing(e -> e.getName()))
                .collect(Collectors.toList());

        System.out.println(departmentNames.getClass());
        departmentNames.forEach(System.out::println);

            /*
            So, what concrete type (subclass) of List is being used here? Are there any guarantees?
If you look at the documentation of Collectors#toList(), it states that -
"There are no guarantees on the type, mutability, serializability, or thread-safety of the List returned".
If you want a particular implementation to be returned, you can use Collectors#toCollection(Supplier) instead.
             */


        List departmentNames2 = employees.stream()
                .filter(e -> e.getLevel() > 2)
                .map(e -> e.getDepartment())
                .distinct()
                .sorted(Comparator.comparing(e -> e.getName()))
                .collect(Collectors.toCollection(LinkedList::new));

        System.out.println(departmentNames2.getClass());
        departmentNames2.forEach(System.out::println);


    
        // TO MAP
    
        List<Employee> employees = YoungEnterpriseCode.hireFourEmployees();
        
        // Get pairs: Department - Employee Name
        Map<String, Department> result = employees.stream()
                .filter(e -> e.getLevel() > 2)
                .sorted(Comparator.comparing(e -> e.getName()))
                .collect(Collectors.toMap(Employee::getName, Employee::getDepartment));

        System.out.println(result.getClass());
        result.forEach((k, v) -> System.out.println(k + " " + v));
        
        
        // STRANGE PARALLEL COLLECT TO LIST
        
        IntStream intStream = IntStream.range(0, 10001);

        BiConsumer<ArrayList<Integer>, ArrayList<Integer>> parallelCombiner = (integers, integers2) -> {
            // filter even numbers
            integers.addAll(integers2.stream().filter(i -> i % 20 == 0).collect(Collectors.toList()));
            System.out.println("Parallel merge by " + Thread.currentThread().getName());
        };

        ArrayList<Integer> result = intStream
                .parallel()
                .map(i -> i * 10)
                .peek(e -> {
                    System.out.println(e + " handled by " + Thread.currentThread().getName());
                })
                .collect(ArrayList::new, ArrayList::add, parallelCombiner);


        System.out.println(result);     // [0, 10, 20, 30, 40, 50, === effect due to first portion before first merge
        System.out.println(result.size());

        // COUNTING
        
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        long result = stream
                .filter(Ex_4_Counting::isPrime)
                .collect(Collectors.counting());

        System.out.println(result);

    }

    private static boolean isPrime(int number){
        for (int i = 2; i < Math.sqrt(number); i++) {
            if(number % i == 0) {
                return false;
            }
        }
        return true;
    }

        // JOINING
        
        final List<Employee> employees = YoungEnterpriseCode.hireFourEmployees();
        String names = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining());

        String  delimitedNames = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining("# "));

        String  prefixedNames = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(" $ ", "Our interns: ",  ". Pay them!"));

        System.out.println("Joined names:  "  + names);
        System.out.println("Joined,  delimited names:  "  + delimitedNames);
        System.out.println(prefixedNames);        

        // SUMMING
        
        List<Employee> employees = YoungEnterpriseCode.hireFourEmployees();

        Double averageDepartmentNameLength = employees.stream()
                .map(Employee::getDepartment)
                .collect(Collectors.averagingInt(d -> d.getName().length()));

        System.out.println(averageDepartmentNameLength);


        Integer sumAge = employees.stream()
                .collect(Collectors.summingInt(d -> d.getAge()));
        // .collect(summingInt(Employee::getAge()));

        System.out.println(sumAge);

        // MAX MIN
        
        List<Employee> employees = YoungEnterpriseCode.hireFourEmployees();

        // Mix optional and static imports
        Optional<Department> result = employees.stream()
                .map(Employee::getDepartment)
                .collect(maxBy(comparing(Department::getName)));

        System.out.println("Max is " + result.get());


        result = employees.stream()
                .map(Employee::getDepartment)
                .collect(minBy(comparing(Department::getName)));

        System.out.println("Min is " + result.get());

        // GROUPING + MAPPING + PARTITION
        
        List<Employee> employees = YoungEnterpriseCode.hireFourEmployees();


        // Step 1: Group by Department
        Map<Department, List<Employee>> groupedByDepartment = employees.stream()
                .collect(groupingBy(Employee::getDepartment));

        System.out.println(groupedByDepartment);

        // Step 2: Group and count employees by department
        Map<Department, Long> countByDepartment = employees.stream()
                .collect(groupingBy(Employee::getDepartment, Collectors.counting()));

        System.out.println(countByDepartment);

        // Step 3: Join and print out skills of employees grouped by theirs departments
        Map<Department, String> skillsByDepartment = employees.stream()
                .collect(groupingBy(Employee::getDepartment, Collectors.mapping(Employee::getSkill, Collectors.joining(" & "))));

        System.out.println(skillsByDepartment);

        // Step 4: Partition developer names by binary criteria: level
        Map<Boolean, String> groupByLevel = employees.stream()
                .collect(partitioningBy(e -> e.getLevel() > 2, mapping(Employee::getName, Collectors.joining("_"))));

        System.out.println(groupByLevel);


  ```