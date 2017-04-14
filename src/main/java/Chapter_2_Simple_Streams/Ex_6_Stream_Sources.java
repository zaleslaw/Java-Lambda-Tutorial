package Chapter_2_Simple_Streams;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by Alexey_Zinovyev on 24.10.2016.
 */
public class Ex_6_Stream_Sources {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Petrograd", "Petersburg", "Leningrad", "Piter");
        Stream<String> s1 = list.stream(); // sized , ordered


        HashSet<String> set = new HashSet<>(list);
        Stream <String> s2 = set.stream (); // sized , distinct

        TreeSet<String> orderedSet = new TreeSet<>(set);
        Stream <String> s3 = set.stream(); // sized , distinct // sorted , ordered*/


        IntStream ints = Arrays.stream(new int[] {1, 2, 3});
        Stream Ints = Arrays.stream(new Integer[] {1, 2, 3});

        Stream<Boolean> flags = Stream.of(true, true, true);

        //flags = Stream.builder().add(true).add(false).build();// play with types and values of different types

        LongStream s = LongStream.range (0, 1000000);


    }
}
