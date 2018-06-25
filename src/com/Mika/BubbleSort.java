package com.Mika;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mika on 06/06/2017.
 */
public class BubbleSort {

    public void bubbleSortSerial(int[] arr) {
        long startTime = System.currentTimeMillis();
        int n = arr.length;
        int temp = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (arr[j - 1] > arr[j]) {
                    //swap elements
                    temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("total time serial = "+totalTime);
//        printArray(arr);
    }

    public void bubbleSortParallel2(int[] arr) {
        int threads = 2;

        for (int i = 0; i < arr.length / threads; i++) {

        }
    }

    public void bubbleSortParallelImproved(int[] arr) {
        long startTime = System.currentTimeMillis();

        int n = arr.length; //length of the array to sort
        // amount of threads
        int amountOfThreads = 2;
        ExecutorService executor = Executors.newFixedThreadPool(amountOfThreads);

        for (int i = 0; i < n - 1; i++) {
            if (i % 2 == 0) { // i even
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < n / 2; j++) {
                            final int tmp = j;
                            if (arr[2 * tmp] > arr[2 * tmp + 1]) {
                                int temp = arr[2 * tmp];
                                arr[2 * tmp] = arr[2 * tmp + 1];
                                arr[2 * tmp + 1] = temp;
                            }
                        }
                    }
                });
            } else { // i odd
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        for (int k = 0; k < n / 2 - 1; k++) {
                            final int tmp = k;
                            if (arr[2 * tmp + 1] > arr[2 * tmp + 2]) {
                                int temp = arr[2 * tmp + 1];
                                arr[2 * tmp + 1] = arr[2 * tmp + 2];
                                arr[2 * tmp + 2] = temp;
                            }
                        }
                    }
                });
            }
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("total time improved parallel = "+totalTime);
//        printArray(arr);
    }

    public void bubbleSortParallel(int[] arr) {
        long startTime = System.currentTimeMillis();

        int n = arr.length; //length of the array to sort
        // amount of threads
        ExecutorService executor = Executors.newFixedThreadPool(2);



        for (int i = 0; i < n - 1; i++) {
            if (i % 2 == 0) { // i even

                for (int j = 0; j < n / 2; j++) {
                    final int tmp = j;
                    executor.submit(new Runnable() {
                        public void run() {
                            if (arr[2 * tmp] > arr[2 * tmp + 1]) {
                                int temp = arr[2 * tmp];
                                arr[2 * tmp] = arr[2 * tmp + 1];
                                arr[2 * tmp + 1] = temp;
                            }
                        }
                    });
                }
            } else { // i odd
                for (int k = 0; k < n / 2 - 1; k++) {
                    final int tmp = k;
                    executor.submit(new Runnable() {
                        public void run() {
                            if (arr[2 * tmp + 1] > arr[2 * tmp + 2]) {
                                int temp = arr[2 * tmp + 1];
                                arr[2 * tmp + 1] = arr[2 * tmp + 2];
                                arr[2 * tmp + 2] = temp;
                            }
//                                swap(2*tmp+1,2*tmp+2);
                        }
                    });
                }
            }
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("total time parallel = "+totalTime);
//        printArray(arr);
    }

    void swap(int a, int b) {
        int temp = a;
        a = b;
        b = temp;
    }

    public void printArray(int[] list) {
        for (int i = 0; i < list.length; i++) {
            System.out.println(list[i]);
        }
    }
}


