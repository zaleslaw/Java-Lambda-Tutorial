package Chapter_6_Collectors;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Alexey_Zinovyev on 29-May-17.
 */
public class Ex_3_Parallel_Collect_to_List {
    public static void main(String[] args) {

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




    }

}
