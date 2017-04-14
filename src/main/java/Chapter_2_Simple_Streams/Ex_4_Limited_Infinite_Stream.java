package Chapter_2_Simple_Streams;

import java.util.stream.IntStream;

/**
 * Sometimes the limit should be reached
 */
public class Ex_4_Limited_Infinite_Stream {
    public static void main(String[] args) {
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
    }
}
