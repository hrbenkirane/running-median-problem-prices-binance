package com.cwt.symbolinfinitemedian.util;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

@Service
public class MedianCalculator {
    public static double calculateInfiniteMedian(List<Double> prices) {

        double[] pricesArray = new double[prices.size()];

        double[] minHeap = new double[prices.size()];
        double[] maxHeap = new double[prices.size()];

        int minHeapSize = 0;
        int maxHeapSize = 0;

        double currentMedian = 0;

        for (int index = 0; index < prices.size(); index++) {
            pricesArray[index] = prices.get(index);
            if (pricesArray[index] < currentMedian) {
                maxHeap[maxHeapSize++] = pricesArray[index];
                // making sure the max heap has maximum value at the top
                if (maxHeap[maxHeapSize - 1] > maxHeap[0]) {
                    swap(maxHeap, maxHeapSize - 1, 0);
                }
            } else {
                minHeap[minHeapSize++] = pricesArray[index];
                // making sure the min heap has minimum value at the top
                if (minHeap[minHeapSize - 1] < minHeap[0]) {
                    swap(minHeap, minHeapSize - 1, 0);
                }
            }

            // if the difference is more than one
            if (Math.abs(maxHeapSize - minHeapSize) > 1) {
                if (maxHeapSize > minHeapSize) {
                    swap(maxHeap, maxHeapSize - 1, 0);
                    minHeap[minHeapSize++] = maxHeap[--maxHeapSize];
                    swap(minHeap, 0, minHeapSize - 1);
                    buildMaxHeap(maxHeap, maxHeapSize);
                } else {
                    swap(minHeap, minHeapSize - 1, 0);
                    maxHeap[maxHeapSize++] = minHeap[--minHeapSize];
                    swap(maxHeap, 0, maxHeapSize - 1);
                    buildMinHeap(minHeap, minHeapSize);
                }
            }

            // calculate the median
            if (maxHeapSize == minHeapSize) {
                currentMedian = (minHeap[0] + maxHeap[0]);
                currentMedian = currentMedian / 2;
            } else if (maxHeapSize > minHeapSize) {
                currentMedian = maxHeap[0];
            } else {
                currentMedian = minHeap[0];
            }
        }
        return currentMedian;

    }

    static void buildMaxHeap(double[] input, int heapSize) {
        int depth = (heapSize - 1) / 2;
        for (int i = depth; i >= 0; i--) {
            maxHeapify(input, i, heapSize);
        }
    }

    static void maxHeapify(double[] input, int i, int heapSize) {
        int leftChildIndex = 2 * i + 1;
        int rightChildIndex = 2 * i + 2;

        // find the largest
        int largest = i;

        if (leftChildIndex < heapSize && input[leftChildIndex] > input[largest]) {
            largest = leftChildIndex;
        }

        if (rightChildIndex < heapSize && input[rightChildIndex] > input[largest]) {
            largest = rightChildIndex;
        }

        if (largest != i) {
            //swap
            swap(input, i, largest);
            //recursive call
            maxHeapify(input, largest, heapSize);
        }
    }

    static void buildMinHeap(double[] input, int heapSize) {
        int depth = (heapSize - 1) / 2;
        for (int i = depth; i >= 0; i--) {
            minHeapify(input, i, heapSize);
        }
    }

    static void minHeapify(double[] input, int i, int heapSize) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // find the smallest
        int smallest = i;

        if (left < heapSize && input[left] < input[smallest]) {
            smallest = left;
        }

        if (right < heapSize && input[right] < input[smallest]) {
            smallest = right;
        }

        if (smallest != i) {
            //swap
            swap(input, i, smallest);
            //recursive call
            minHeapify(input, smallest, heapSize);
        }
    }

    static void swap(double[] input, int indexOne, int indexTwo) {
        if (indexOne == indexTwo)
            return;
        double temp = input[indexOne];
        input[indexOne] = input[indexTwo];
        input[indexTwo] = temp;
    }

    // calculate median using Priority Queues

    public static void runningMedian(List<Double> prices) {

        PriorityQueue<Double> minQueue = new PriorityQueue<>(Comparator.reverseOrder()); // max value will be at the top
        PriorityQueue<Double> maxQueue = new PriorityQueue<>(); // minimum value will be at the top

        double median = 0;

        for (int i = 0; i < prices.size(); i++) {
            //compare and put value
            if (median >= prices.get(i)) {
                minQueue.offer(prices.get(i));

                // re-balance
                if (minQueue.size() - maxQueue.size() > 1) {
                    maxQueue.offer(minQueue.poll());
                }
            } else {
                maxQueue.offer(prices.get(i));

                // re-balance
                if (maxQueue.size() - minQueue.size() > 1) {
                    minQueue.offer(maxQueue.poll());
                }
            }

            median = getMedian(minQueue, maxQueue);
            System.out.println(median);
        }

    }

    private static double getMedian(Queue<Double> minQueue, Queue<Double> maxQueue) {
        double median;
        if (minQueue.size() == maxQueue.size()) {
            median = (minQueue.peek() + maxQueue.peek());
            median/=2;
        } else {
            median = minQueue.size() > maxQueue.size() ? minQueue.peek() : maxQueue.peek();
        }
        return median;
    }


}
