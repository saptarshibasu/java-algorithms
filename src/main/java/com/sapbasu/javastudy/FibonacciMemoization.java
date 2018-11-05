package com.sapbasu.javastudy;

import java.util.ArrayList;
import java.util.List;

/**
 * The last two Fibonacci numbers are retrieved from the cache and thus the time
 * complexity is reduced to n
 *
 */
public class FibonacciMemoization {
  public static List<Integer> fibonacci(int n) {
    List<Integer> fibSeries = new ArrayList<Integer>(n);
    for (int i = 0; i < n; i++) {
      fibSeries.add(fib(i, fibSeries));
    }
    return fibSeries;
  }
  
  private static int fib(int n, List<Integer> fibSeries) {
    if (n <= 0) return 0;
    else if (n == 1) return 1;
    else if (fibSeries.size() > n) return fibSeries.get(n);
    else {
      return fib(n - 1, fibSeries) + fib(n - 2, fibSeries);
    }
  }
}
