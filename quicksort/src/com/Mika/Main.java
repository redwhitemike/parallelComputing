package com.Mika;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public void run() {

        /**
         * we can use this method to view the sorted lists
         * "nameofclass".printDataSet()
         */

        /**
         * there is a test method implemented
         */

        int[] list = {10, 100, 1000, 10000};
//        int[] list = {10};


        for (int k : list) {
            List<Integer> dataSetList = getDataSet(k);
            int[] compareDataSet = new int[dataSetList.size()];
            int[] dataSet1 = new int[dataSetList.size()];
            int[] dataSet2 = new int[dataSetList.size()];
            int[] dataSet3 = new int[dataSetList.size()];
            int[] dataSet4 = new int[dataSetList.size()];
            int[] dataSet5 = new int[dataSetList.size()];
            int[] dataSet6 = new int[dataSetList.size()];
            int[] dataSet7 = new int[dataSetList.size()];

            //convert list to array so that it can be used in the serial method
            for (int i = 0; i < dataSetList.size(); i++) {
                compareDataSet[i] = dataSetList.get(i);
                dataSet1[i] = dataSetList.get(i);
                dataSet2[i] = dataSetList.get(i);
                dataSet3[i] = dataSetList.get(i);
                dataSet4[i] = dataSetList.get(i);
                dataSet5[i] = dataSetList.get(i);
                dataSet6[i] = dataSetList.get(i);
                dataSet7[i] = dataSetList.get(i);
            }

            int hi1 = dataSet1.length - 1;
            int lo1 = 0;

            int hi2 = dataSet2.length - 1;
            int lo2 = 0;

            int hi3 = dataSet3.length - 1;
            int lo3 = 0;

            int hi4 = dataSet4.length - 1;
            int lo4 = 0;

            int hi5 = dataSet5.length - 1;
            int lo5 = 0;

            int hi6 = dataSet6.length - 1;
            int lo6 = 0;

            int hi7 = dataSet7.length - 1;
            int lo7 = 0;

            Arrays.sort(compareDataSet);
            /**
             * serial code
             */
            QuickSort quickSort = new QuickSort();
            //start of measuring time
            long startTime = System.nanoTime();
            quickSort.sort(dataSet1, lo1, hi1);
            //end of measuring time
            long endTime = System.nanoTime();
            long totalTime = endTime - startTime;
            System.out.println("total time serial " + k + " = " + totalTime);
//            testDataSet(compareDataSet, dataSet1);

            /**
             * threads and locks with 2 threads
             */
            int threadsFirst = 2;
            QuickSortParallel parallelQuick = new QuickSortParallel(threadsFirst);
            //start of measuring time
            long startTimeParallel = System.nanoTime();
            parallelQuick.sortParallel(dataSet2, lo2, hi2);
            //end of measuring time
            long endTimeParallel = System.nanoTime();
            long totalTimeParallel = endTimeParallel - startTimeParallel;
            System.out.println("total time parallel(" + threadsFirst + " threads) " + k + " = " + totalTimeParallel);
//            parallelQuick.printDataSet();
//            testDataSet(compareDataSet, parallelQuick.getArray());

            /**
             * threads and locks with 4 threads
             */
            int threadsSecond = 4;
            QuickSortParallel parallelQuick2 = new QuickSortParallel(threadsSecond);
            //start of measuring time
            long startTimeParallel2 = System.nanoTime();
            parallelQuick2.sortParallel(dataSet3, lo3, hi3);
            //end of measuring time
            long endTimeParallel2 = System.nanoTime();
            long totalTimeParallel2 = endTimeParallel2 - startTimeParallel2;
            System.out.println("total time parallel(" + threadsSecond + " threads) " + k + " = " + totalTimeParallel2);
//            parallelQuick2.printDataSet();
//            testDataSet(compareDataSet, parallelQuick2.getArray());

            /**
             * threads and locks (AtomicArray) with 2 threads
             */
            int threadsFirstAtomic = 2;
            QuickSortParallelAtomic parallelQuickAtomic = new QuickSortParallelAtomic(threadsFirst);
            //start of measuring time
            long startTimeParallelAtomic = System.nanoTime();
            parallelQuickAtomic.sortParallel(dataSet4, lo4, hi4);
            //end of measuring time
            long endTimeParallelAtomic = System.nanoTime();
            long totalTimeParallelAtomic = endTimeParallelAtomic - startTimeParallelAtomic;
            System.out.println("total time parallel Atomic (" + threadsFirstAtomic + " threads) " + k + " = " + totalTimeParallelAtomic);


            /**
             * threads and locks (AtomicArray) with 4 threads
             */
            int threadsSecondAtomic2 = 4;
            QuickSortParallelAtomic parallelQuickAtomic2 = new QuickSortParallelAtomic(threadsSecond);
            //start of measuring time
            long startTimeParallelAtomic2 = System.nanoTime();
            parallelQuickAtomic2.sortParallel(dataSet5, lo5, hi5);
            //end of measuring time
            long endTimeParallelAtomic2 = System.nanoTime();
            long totalTimeParallelAtomic2 = endTimeParallelAtomic2 - startTimeParallelAtomic2;
            System.out.println("total time parallel Atomic (" + threadsSecondAtomic2 + " threads) " + k + " = " + totalTimeParallelAtomic2);


            /**
             * thread pool with 2 threads
             */
            int threadsFirstThreadPool = 2;
            QuickThread quickThread = new QuickThread(threadsFirstThreadPool);
            //start of measuring time
            long startTimeThreadPool = System.nanoTime();
            quickThread.sortParallel(dataSet6, lo6, hi6);
            //end of measuring time
            long endTimeThreadPool   = System.nanoTime();
            long totalTimeThreadPool = endTimeThreadPool - startTimeThreadPool;
            System.out.println("total time thread pool ("+threadsFirstThreadPool+" threads) "+k+" = "+totalTimeThreadPool);
//            quickThread.printDataSet();
//            testDataSet(compareDataSet, quickThread.getArray());

            /**
             * thread pool with 4 threads
             */
            int threadsSecondThreadPool = 4;
            QuickThread quickThread2 = new QuickThread(threadsSecondThreadPool);
            //start of measuring time
            long startTimeThreadPool2 = System.nanoTime();
            quickThread2.sortParallel(dataSet7, lo7, hi7);
            //end of measuring time
            long endTimeThreadPool2   = System.nanoTime();
            long totalTimeThreadPool2 = endTimeThreadPool2 - startTimeThreadPool2;
            System.out.println("total time thread pool ("+threadsSecondThreadPool+" threads) "+k+" = "+totalTimeThreadPool2);
//            quickThread2.printDataSet();
//            testDataSet(compareDataSet, quickThread2.getArray());
            printSeperationLine();
        }
    }

    public List<Integer> getDataSet(int size) {

        Path currentRelativePath = Paths.get("");
        String userPath = currentRelativePath.toAbsolutePath().toString();
        Path filePath = Paths.get(userPath + "/testsets/" + size + "test.txt");
        Scanner scanner = null;

        try {
            scanner = new Scanner(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Integer> integers = new ArrayList<>();

        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                integers.add(scanner.nextInt());
            } else {
                scanner.next();
            }
        }
        return integers;
    }

    public void printSeperationLine() {
        System.out.println("-------------------------------------------------------------");
    }

    public void testDataSet(int[] compare, int[] dataSet) {
        for (int i = 0; i < dataSet.length; i++){
            if (compare[i] != dataSet[i]) {
                System.out.print("false");
            }
        }
    }
}
