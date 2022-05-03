package com.cwt.symbolinfinitemedian.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;



import java.util.List;



class MedianCalculatorTest {

    @Test
    void calculateMedian() {
        // Arrange
        List<Double> prices = List.of(3.0, 4.0, 2.0, 1.0, 5.0, 8.0, 2.5);

        // Act
        double median = MedianCalculator.calculateInfiniteMedian(prices);

        // Assert
        assertEquals(3.0, median);
    }

}