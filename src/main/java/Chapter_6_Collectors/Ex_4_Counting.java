package Chapter_6_Collectors;



import java.util.stream.Collectors;

import java.util.stream.Stream;


/**
 * Created by Alexey_Zinovyev on 29-May-17.
 */
public class Ex_4_Counting {
    public static void main(String[] args) {

        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        long result = stream
                .filter(Ex_4_Counting::isPrime)
                .collect(Collectors.counting());

        System.out.println(result);

    }

    private static boolean isPrime(int number){
        for (int i = 2; i < Math.sqrt(number); i++) {
            if(number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
