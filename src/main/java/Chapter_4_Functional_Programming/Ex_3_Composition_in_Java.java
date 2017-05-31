package Chapter_4_Functional_Programming;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Overview of function composition in Java 8
 */
public class Ex_3_Composition_in_Java {
    public static void main(String[] args) {
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


    }

    public static <Integer> Function<Integer, Integer> compose(Function<Integer,Integer> f, Function<Integer,Integer> g) {
        return x -> f.apply(g.apply(x));
    }
}
