package com.Mika;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mika on 18/08/2018.
 * this code is an implementation of the code found on https://stackoverflow.com/questions/3425126/java-parallelizing-quick-sort-via-multi-threading
 */
public class QuickSortThreadPool implements Runnable {

    public static int MAX_THREADS = Runtime.getRuntime().availableProcessors();
    static final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

    final int[] my_array;
    final int start, end;

    private final int minParitionSize;

    public QuickSortThreadPool(int minParitionSize, int numberOfThreads ,int[] array, int start, int end) {
        this.minParitionSize = minParitionSize;
        this.my_array = array;
        this.start = start;
        this.end = end;
        MAX_THREADS = numberOfThreads;
    }

    public void run() {
        quicksort(my_array, start, end);
    }

    public void quicksort(int[] array, int start, int end) {
        int len = end - start + 1;

        if (len <= 1)
            return;

        int pivot_index = medianOfThree(array, start, end);
        int pivotValue = array[pivot_index];

        swap(array, pivot_index, end);

        int storeIndex = start;
        for (int i = start; i < end; i++) {
            if (array[i] <= pivotValue) {
                swap(array, i, storeIndex);
                storeIndex++;
            }
        }

        swap(array, storeIndex, end);

        if (len > minParitionSize) {

            QuickSortThreadPool quick = new QuickSortThreadPool(minParitionSize, MAX_THREADS ,array, start, storeIndex - 1);
            Future<?> future = executor.submit(quick);
            quicksort(array, storeIndex + 1, end);

            try {
                future.get(1000, TimeUnit.SECONDS);
            } catch (Exception ex) {
                ex.getMessage();
            }
        } else {
            quicksort(array, start, storeIndex - 1);
            quicksort(array, storeIndex + 1, end);
        }
    }

    void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public int medianOfThree(int[] intArray, int left, int right) {
        int center = (left + right) / 2;

        if (intArray[left] > intArray[center])
            swap(intArray, left, center);

        if (intArray[left] > intArray[right])
            swap(intArray, left, right);

        if (intArray[center] > intArray[right])
            swap(intArray, center, right);

        swap(intArray, center, right - 1);
        return intArray[right - 1];
    }

//    public void shutdown(){
//        executor.shutdownNow();
//    }
}


