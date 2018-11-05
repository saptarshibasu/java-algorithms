package com.sapbasu.javastudy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class FibonacciRecursionTest {
  @Test
  public void whenFibonacciInvoked_givenNumbeOfTerms_returnFibonacciSeries() {
    try {
      List<Integer> fibSeries = FibonacciRecursion.fibonacci(5);
      assertEquals(fibSeries.size(), 5);
      assertEquals(fibSeries.get(4), Integer.valueOf(fibSeries.get(3) + fibSeries.get(2)));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
