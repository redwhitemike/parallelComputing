package com.Mika;

/**
 * Created by Mika on 17/08/2018.
 */
public class QuickSortParallel {

    int amountOfThreads = 0;
    Thread t1;
    Thread t2;
    Thread t3;
    Thread t4;

    int[] sortedArray;

    public QuickSortParallel(int threads) {
        this.amountOfThreads = threads;
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
        }

        sortedArray = arr;
    }

    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
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
        for (int i = 0; i < sortedArray.length; i++){
            System.out.print(" "+sortedArray[i]);
        }
    }
}
