package com.Mika;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by Mika on 18/08/2018.
 */
public class QuickSortParallelAtomic {

    int amountOfThreads;
    Thread t1 = new Thread();
    Thread t2 = new Thread();
    Thread t3 = new Thread();
    Thread t4 = new Thread();

    AtomicIntegerArray sortedArray;


    public QuickSortParallelAtomic (int threads) {
        this.amountOfThreads = threads;
    }

    int partitionAtomic(AtomicIntegerArray arr, int low, int high) {
        int pivot = arr.get(high);
        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++) {
            // If current element is smaller than or
            // equal to pivot
            if (arr.get(j) <= pivot) {
                i++;
                // swap arr[i] and arr[j]
                int temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        int temp = arr.get((i+1));
        arr.set(((i+1)), arr.get(high));
        arr.set(high, temp);

        return i + 1;
    }

    public void sortParallel(int array[], int low, int high) {
        AtomicIntegerArray arr = new AtomicIntegerArray(array);
//        for (int i = 0; i < arr.length(); i++){
//            System.out.println(arr.get(i));
//        }
        if (low < high) {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partitionAtomic(arr, low, high);
            // Recursively sort elements before
            // partition and after partition
            if (amountOfThreads == 4) {
                t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sort2(arr, low, pi - 1);
                    }
                });
//            System.out.println(threadsUsed);
                t1.start();

                t2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sort2(arr, pi + 1, high);
                    }
                });
//            System.out.println(threadsUsed);
                t2.start();
            } else {
                t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sort(arr, low, pi - 1);
                    }
                });
//            System.out.println(threadsUsed);
                t1.start();

                t2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sort(arr, pi + 1, high);
                    }
                });
//            System.out.println(threadsUsed);
                t2.start();

                try {
                    t1.join();
                    t2.join();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }

            sortedArray = arr;
        }
    }

    void sort(AtomicIntegerArray arr, int low, int high) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partitionAtomic(arr, low, high);
            // Recursively sort elements before
            // partition and after partition
            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);

        }
    }

    void sort2(AtomicIntegerArray arr, int low, int high) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partitionAtomic(arr, low, high);
            // Recursively sort elements before
            // partition and after partition

            t3 = new Thread(new Runnable() {
                @Override
                public void run() {
                    sort(arr, low, pi - 1);
                }
            });
            t3.start();

            t4 = new Thread(new Runnable() {
                @Override
                public void run() {
                    sort(arr, pi + 1, high);
                }
            });
            t4.start();


            try {
                t1.join();
                t2.join();
                t3.join();
                t4.join();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

    public void printDataSet(){
        System.out.print("Dataset ");
        for (int i = 0; i < sortedArray.length(); i++){
            System.out.print(" "+sortedArray.get(i));
        }
    }
}
