package com.Mika;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Mika on 18/08/2018.
 */
public class QuickThread {

    static int amountOfThreads = 2;

    int[] sortedArray;

    private static ExecutorService pool;

    public QuickThread(int threads) {
        amountOfThreads = threads;
        pool = Executors.newFixedThreadPool(amountOfThreads);
    }

    public int[] getArray(){
        return sortedArray;
    }

    int partition(int arr[], int low, int high) {
        int pivot = arr[high];
        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++) {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j] <= pivot) {
                i++;

                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    public void sortParallel(int arr[], int low, int high) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high);
            // Recursively sort elements before
            // partition and after partition
            if (amountOfThreads == 4) {
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        sort2(arr, low, pi - 1);
                    }
                });

                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        sort2(arr, pi + 1, high);
                    }
                });
            } else {
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        sort(arr, low, pi - 1);
                    }
                });

                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        sort(arr, pi + 1, high);
                    }
                });
//            System.out.println(threadsUsed);


            }
        }
        sortedArray = arr;
    }

    void sort(int arr[], int low, int high) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high);
            // Recursively sort elements before
            // partition and after partition
            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);

        }
    }

    void sort2(int arr[], int low, int high) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high);
            // Recursively sort elements before
            // partition and after partition

            pool.execute(new Runnable() {
                @Override
                public void run() {
                    sort(arr, low, pi - 1);
                }
            });

            pool.execute(new Runnable() {
                @Override
                public void run() {
                    sort(arr, pi + 1, high);
                }
            });
        }
    }

    public void printDataSet(){
        System.out.print("Dataset ");
        for (int i = 0; i < sortedArray.length; i++){
            System.out.print(" "+ sortedArray[i]);
        }
    }
}
