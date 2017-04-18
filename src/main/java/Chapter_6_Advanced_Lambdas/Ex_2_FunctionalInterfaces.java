package Chapter_6_Advanced_Lambdas;


import Chapter_3_Migration_from_7_to_8.beans.Department;


import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


public class Ex_2_FunctionalInterfaces {
    public static void main(String[] args) {
        Predicate<Integer> isPrime = number -> isEven(number);

        Consumer<Integer> missageConsumer = missage -> System.out.println("Winnie Pooh's missage is " + missage);

        Function<String, Department> strangeConstructor = name -> new Department(name);

        Function<Integer, Integer> addOne = a -> a + 1;

        System.out.println(addOne.apply(1));

        Supplier<Boolean> random = () -> ThreadLocalRandom.current().nextBoolean();

        System.out.println(random.get());

        Arrays.asList(1,2,3,4,5).stream().map(addOne).filter(isPrime).forEach(missageConsumer);


    }

    private static boolean isEven(Integer number) {
        if(number % 2 == 0) {
            return  true;
        }
        return false;
    }
}
