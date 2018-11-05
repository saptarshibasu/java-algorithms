package com.sapbasu.javastudy;

import java.util.ArrayList;
import java.util.List;

public class FibonacciMemoization {
  public static List<Integer> fib(int n) {
    List<Integer> fibSeries = new ArrayList<Integer>(n);
    fib(n, fibSeries);
    return fibSeries;
  }
  
  private static void fib(int n, List<Integer> fibSeries) {
    for (int i = 0; i < n; i++) {
      if (i <= 0) fibSeries.add(0);
      else if (i == 1) fibSeries.add(1);
      else fibSeries.add(fibSeries.get(i - 1) + fibSeries.get(i - 2));
    }
  }
}
