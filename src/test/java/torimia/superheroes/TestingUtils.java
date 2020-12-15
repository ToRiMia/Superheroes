package torimia.superheroes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TestingUtils {

    public static <T> List<T> createListOf(int number, Supplier<T> s) {
        List<T> list = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            list.add(s.get());
        }
        return list;
    }
}
