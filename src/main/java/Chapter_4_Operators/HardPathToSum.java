package Chapter_4_Operators;

import java.util.stream.Stream;

import static java.util.stream.Collectors.summingInt;

/**
 * A few approaches to sum ints along array
 */
public class HardPathToSum {
    public static void main(String[] args) {
        Stream<Integer> ints = Stream.of(1,2,3,4,5,6,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);
        int sum = 0;
        //ints.forEach(e -> { sum+= e});
        System.out.println(sum);

        //ok, maybe through array

        int[] sumResult = {0};
        //ints.forEach(e -> sumResult[0] += e);
        System.out.println(sumResult); // typical mistake, we forgot about real type

        // yeah!!!!!

        /*ints.parallel()
                .forEach(e -> sumResult[0] += e);*/
        System.out.println(sumResult[0]); // repeat 10 times and you print different results sometimes
        // WHY???? Who knows that?


        System.out.println(ints.reduce(0, (x,y) -> x + y));

        // for primitive
        System.out.println(ints.mapToInt(i -> i).sum());
        // with collectors
        System.out.println(ints.collect(summingInt(i->i.intValue())));
        // with method references
        System.out.println(ints.reduce(0, Integer::sum));
        System.out.println(ints.collect(summingInt(Integer::intValue)));
    }
}
