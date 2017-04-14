package Chapter_2_Simple_Streams;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Chain of intermediate operations
 */
public class Ex_2_Lazy_Stream {
    public static void main(String[] args) {
        Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5, 5, 4)
                .stream()
                .filter(e -> e > 4)
                .map(b -> 1);

        //Nothing happened

        stream.forEach(System.out::println); // <---- TERMINAL OPERATION


    }
}
