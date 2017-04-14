package Chapter_2_Simple_Streams;

import java.util.Arrays;

/**
 * Exception in thread "main" java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.Double
 * Why?
 * How to fix?
 */
public class Ex_5_Problem_Broken_Sorted_Stream {
    public static void main(String[] args) {
        Arrays.asList(2, 3 , 4.2, 13, -1, 3, 8.8)
                .stream()
                .sorted()
                .forEach(e -> System.out.println(e));
    }
}
