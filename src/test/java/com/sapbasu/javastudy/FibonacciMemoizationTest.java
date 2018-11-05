package com.sapbasu.javastudy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class FibonacciMemoizationTest {
  @Test
  public void whenFibCalled_givenNumberOfTerms_fibGenerated() {
    try {
      List<Integer> fibSeries = FibonacciMemoization.fib(5);
      assertTrue(fibSeries.size() == 5);
      assertTrue(fibSeries.get(4) == fibSeries.get(3) + fibSeries.get(2));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
