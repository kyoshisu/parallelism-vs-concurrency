package ru.example.parallelismvsconcurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StreamBenchmark {

    public static void main(String[] args) {
        System.out.println("Генерация 1 000 000 случайных чисел");
        List<Integer> numbers = new ArrayList<>(1_000_000);
        Random random = new Random();
        for (int i = 0; i < 1_000_000; i++) {
            numbers.add(random.nextInt(1000));
        }

        long startSequential = System.nanoTime();

        long sumSequential = numbers.stream()
                .filter(n -> n % 2 == 0)
                .mapToLong(n -> (long) n * 2)
                .sum();

        long endSequential = System.nanoTime();
        long durationSequential = (endSequential - startSequential) / 1_000_000;

        long startParallel = System.nanoTime();

        long sumParallel = numbers.parallelStream()
                .filter(n -> n % 2 == 0)
                .mapToLong(n -> (long) n * 2)
                .sum();

        long endParallel = System.nanoTime();
        long durationParallel = (endParallel - startParallel) / 1_000_000;

        System.out.println("\nРезультаты:");
        System.out.println("Сумма (последовательно):" + sumSequential);
        System.out.println("Сумма (параллельно):      " + sumParallel);
        System.out.println("Результаты совпадают: " + (sumSequential == sumParallel));

        System.out.println("\nВремя выполнения:");
        System.out.println("Обычный stream():      " + durationSequential + " мс");
        System.out.println("parallelStream():      " + durationParallel + " мс");

        if (durationSequential < durationParallel) {
            System.out.println("Последовательный поток оказался быстрее.");
        } else {
            System.out.println("Параллельный поток оказался быстрее.");
        }
    }
}