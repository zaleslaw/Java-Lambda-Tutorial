package Chapter_2_Simple_Streams;

import java.util.stream.IntStream;

/**
 * Let's run infinite stream
 */
public class Ex_3_Infinite_Stream {
    public static void main(String[] args) {
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
    }
}
