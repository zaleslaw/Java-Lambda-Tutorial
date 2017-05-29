package Chapter_7_Functional_Programming;


import java.util.function.BiFunction;
import java.util.function.Function;

public class Ex_2_Currying {
    public static void main(String[] args) {
        //https://gist.github.com/timyates/7674005


        // Step 1: Calculate per diem for our IT-week speakers
        CalculatePerDiem calculatePerDiem = new CalculatePerDiem();
        Function<Integer, Double> curriedByFirstArgument = calculatePerDiem.curryFirstArgument(57.16);

        System.out.println(curriedByFirstArgument.apply(5));
        System.out.println(curriedByFirstArgument.apply(3));
        System.out.println(curriedByFirstArgument.apply(10));

        // Step 2: Calculate per diem for different exchange rates
        Function<Double, Double> curriedBySecondArgument = calculatePerDiem.currySecondArgument(10);
        System.out.println(curriedBySecondArgument.apply(56.12));
        System.out.println(curriedBySecondArgument.apply(61.63));

    }





}

class CalculatePerDiem implements CurriedBiFunction<Double, Integer, Double>{

    private static final Double perDiemRate = 10.15;

    @Override
    public Double apply(Double dollarExchangeRate, Integer amountOfDays) {
        return dollarExchangeRate * amountOfDays * perDiemRate;
    }
}


@FunctionalInterface
interface CurriedBiFunction<T, U, R>
        extends BiFunction<T, U, R> {

    default Function<U, R> curryFirstArgument(T t) {
        return u -> apply(t, u);
    }

    default Function<T, R> currySecondArgument(U u) {
        return t -> apply(t, u);
    }
}
