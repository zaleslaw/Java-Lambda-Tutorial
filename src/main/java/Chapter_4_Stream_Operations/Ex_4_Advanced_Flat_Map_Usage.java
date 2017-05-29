package Chapter_4_Stream_Operations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.function.IntUnaryOperator.identity;

/**
 * Created by Alexey_Zinovyev on 29-May-17.
 */
public class Ex_4_Advanced_Flat_Map_Usage {
    public static void main(String[] args) {
        // Step 5: TODO: Generators and flatMap

        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            for (int j = 0; j < i; j++) {
                list.add(i);
            }
        }
        for(Integer i: list){
            System.out.println(i);
        }

        IntStream intStream = IntStream.rangeClosed(1, 5)
                .flatMap(i -> IntStream.iterate(i, identity()).limit(i));

/*      IntStream.rangeClosed(1, 4) creates a stream of int from 1 to 4, inclusive
        IntStream.iterate(i, identity()).limit(i) creates a stream of length i of int i - so applied to i = 4 it creates a stream: 4, 4, 4, 4
        flatMap "flattens" the stream and "concatenates" it to the original stream
        */


        intStream.forEach(System.out::println);

/*        // Step 6: TODO: Strange FlatMap
        System.out.println("Flat map");
        Function<? super Integer, ? extends Stream<?>> function = e -> IntStream.range(0, e).boxed();
        ints.stream().map(Math::abs).flatMap(function).forEach(System.out::println);*/
    }
}
