import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

public class Main {
    private static int[] array;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable sum = () -> {
            long sumMultiThread = 0;
            for (int i = 0; i < array.length; i++) {
                sumMultiThread += array[i];
            }
            return sumMultiThread;
        };

        Callable count = () -> {
            long countMultiThread = 0;
            for (int i = 0; i < array.length; i++) {
                countMultiThread += 1;
            }
            return countMultiThread;
        };

        createArray(200000000);

        System.out.println("Среднее арифметичекое однопоточное - " + oneThread());

        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        long starting = System.currentTimeMillis();
        Future<Long> task1 = threadPool.submit(sum);
        Future<Long> task2 = threadPool.submit(count);

        System.out.println("Среднее арифметичекое многопоточное - " + (task1.get() / task2.get()));

        long ending = System.currentTimeMillis();
        System.out.println("затраченное время многопоточное - " + (ending - starting));

        threadPool.shutdown();
    }

    public static void createArray(Integer length) {
        array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = ThreadLocalRandom.current().nextInt(1, 31);
        }
    }

    public static long oneThread() {
        long starting = System.currentTimeMillis();
        long sumOneThread = 0;
        for (int i = 0; i < array.length; i++) {
            sumOneThread += array[i];
        }
        long countOneThread = 0;
        for (int i = 0; i < array.length; i++) {
            countOneThread += 1;
        }
        long ending = System.currentTimeMillis();
        System.out.println("затраченное время однопоточное - " + (ending - starting));
        return sumOneThread / countOneThread;
    }
}

/*
При колличестве элементов 2000 однопоточное решение быстрее многопоточного, но при большой длине массива - 200000000,
многопоточное оказывается быстрее.
 */