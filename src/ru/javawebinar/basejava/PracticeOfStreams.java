package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PracticeOfStreams {
    private static int[] array = new int[]{4, 7, 5, 4, 1, 3};

    public static void main(String[] args) {
        System.out.println(minValue(array));
        System.out.println();
        final List<Integer> collect = Arrays.stream(array).
                boxed().
                collect(Collectors.toList());
        final List<Integer> integers = oddOrEven(collect);
        integers.forEach(System.out::println);
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .sorted()
                .distinct()
                .reduce(0, (result, next) -> result * 10 + next);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Predicate<Integer> filter = i -> i % 2 != 0;
        final int sum = integers.stream()
                .mapToInt(Integer::intValue)
                .sum();
        return integers.stream()
                .filter(sum % 2 == 0 ? filter : filter.negate())
                .collect(Collectors.toList());
    }
}