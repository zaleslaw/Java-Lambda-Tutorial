package Chapter_2_Simple_Streams;


import java.util.Arrays;
import java.util.List;

/**
 * Created by zaleslaw on 14.04.17.
 */
public class Ex_1_First_Stream {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("usa", " RUSSIA ", "gerMANY", "JApAN");

        // for evolution
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }


        for (String item: list) {
            System.out.println(item);
        }

        // Nothing interesting
        System.out.println(list.stream().count());

        // transform all string in lower case, trim them and skip strings with 'j'

        for (String item: list) {
            String newItem = item.toLowerCase().trim();
            if(item.contains("j")){
                continue;
            }
            System.out.println(newItem);
        }

        // or

        list
                .stream()
                .map(e -> e.toLowerCase().trim())
                .filter(p -> !p.contains("j"))
                .forEach(e -> System.out.println(e));

    }
}
