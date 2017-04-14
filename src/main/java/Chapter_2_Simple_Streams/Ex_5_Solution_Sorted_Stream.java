package Chapter_2_Simple_Streams;

import java.util.Arrays;

/**
 * Wrap to Double
 * Listen to types
 */
public class Ex_5_Solution_Sorted_Stream {
    public static void main(String[] args) {
        Arrays.asList(new Double(2), new Double(3) , 4.2, new Double(13), new Double(-1), new Double(3), 8.8)
                .stream()
                .sorted()
                .forEach(e -> System.out.println(e));
    }
}
