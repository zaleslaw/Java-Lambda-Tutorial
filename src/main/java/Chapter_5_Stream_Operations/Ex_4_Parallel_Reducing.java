package Chapter_5_Stream_Operations;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * When using the following reduce method,
 * each thread accumulates the partial results using the accumulator.
 * At the end, the combiner is used to combine the partial results from all threads to get the result.
 */
public class Ex_4_Parallel_Reducing {
    public static void main(String[] args) {
        IntStream ints = IntStream.range(1, 10);
        int sum = 0;
        sum = ints.parallel().reduce(0, (partialSum, newNumber) -> {
            System.out.println("Partial sum is " + partialSum + " new number is " + newNumber + " calculated in Thread " + Thread.currentThread().getName());
            return partialSum + newNumber;
        });
        System.out.println(sum);


        Stream<String> stream = Stream.of("I", "the", "love", "and", "house", "wife", "dog", "cat", "hate", "eat", "build");
        BiFunction<String, String, String> partialJoin = (partialString, newString) -> {

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("PS = " + partialString + " ELEM = " + newString + " BY " + Thread.currentThread().getName());
            return String.join(" ", partialString, newString);
        };
        BinaryOperator<String> threadJoin = (firstString, secondString) -> {

            System.out.println(firstString + " vs " + secondString + " BY " + Thread.currentThread().getName());

            if(firstString.length() == secondString.length())
                return String.join(" ", firstString, secondString);
            else if(firstString.length() > secondString.length())
                return firstString;
            return secondString;

        };
        String result = stream.parallel().reduce("#Wow# ", partialJoin, threadJoin);
        System.out.println(result);
    }
}
