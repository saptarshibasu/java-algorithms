package com.sapbasu.javastudy;

import java.util.ArrayList;
import java.util.List;

public class FibonacciRecursion {
  public static List<Integer> fibonacci(int n) {
    List<Integer> strList = new ArrayList<Integer>(n);
    for (int i = 0; i < n; i++) {
      strList.add(fib(i));
    }
    return strList;
  }
  
  private static Integer fib(int n) {
    if (n <= 0) return 0;
    else if (n == 1) return 1;
    else return fib(n - 1) + fib(n - 2);
  }
}
