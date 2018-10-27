package com.sapbasu.javastudy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.sapbasu.javastudy.support.EmpAgeComparator;
import com.sapbasu.javastudy.support.Employee;

public class SortedArrayMergeTest {
  
  @Test
  public void whenMergeSmallCalled_givenTwoSortedArraysOfEqualSize_oneMergedArrayReturned() {
    try {
      List<Employee> empList1 = new ArrayList<Employee>(5);
      empList1.add(new Employee("1fname31", "1lname31", "1dept2", 31,
          BigDecimal.valueOf(2000.00)));
      empList1.add(new Employee("1fname33", "1lname33", "1dept2", 33,
          BigDecimal.valueOf(900.00)));
      empList1.add(new Employee("1fname35", "1lname35", "1dept2", 35,
          BigDecimal.valueOf(4000.00)));
      empList1.add(new Employee("1fname37", "1lname37", "1dept2", 37,
          BigDecimal.valueOf(2400.00)));
      empList1.add(new Employee("1fname39", "1lname39", "1dept2", 39,
          BigDecimal.valueOf(6000.00)));
      
      List<Employee> empList2 = new ArrayList<Employee>(5);
      empList2.add(new Employee("2fname32", "2lname32", "2dept2", 32,
          BigDecimal.valueOf(2000.00)));
      empList2.add(new Employee("2fname34", "2lname34", "2dept2", 34,
          BigDecimal.valueOf(900.00)));
      empList2.add(new Employee("2fname36", "2lname36", "2dept2", 36,
          BigDecimal.valueOf(4000.00)));
      empList2.add(new Employee("2fname38", "2lname38", "2dept2", 38,
          BigDecimal.valueOf(2400.00)));
      empList2.add(new Employee("2fname40", "2lname40", "2dept2", 40,
          BigDecimal.valueOf(6000.00)));
      
      List<Employee> concatenatedArray = new ArrayList<Employee>(
          empList1.size() + empList2.size());
      concatenatedArray.addAll(empList1);
      concatenatedArray.addAll(empList2);
      
      List<Employee> mergedArray = SortedListMerge.mergeSmall(empList1,
          empList2, new EmpAgeComparator());
      Collections.sort(concatenatedArray, new EmpAgeComparator());
      
      assertEquals(concatenatedArray, mergedArray);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    
  }
  
  @Test
  public void whenMergeSmallCalled_givenTwoSortedArrayWithBiggerFirst_oneMergedArrayReturned() {
    try {
      List<Employee> empList1 = new ArrayList<Employee>(5);
      empList1.add(new Employee("1fname31", "1lname31", "1dept2", 31,
          BigDecimal.valueOf(2000.00)));
      empList1.add(new Employee("1fname33", "1lname33", "1dept2", 33,
          BigDecimal.valueOf(900.00)));
      empList1.add(new Employee("1fname35", "1lname35", "1dept2", 35,
          BigDecimal.valueOf(4000.00)));
      empList1.add(new Employee("1fname37", "1lname37", "1dept2", 37,
          BigDecimal.valueOf(2400.00)));
      empList1.add(new Employee("1fname39", "1lname39", "1dept2", 39,
          BigDecimal.valueOf(6000.00)));
      
      List<Employee> empList2 = new ArrayList<Employee>(5);
      empList2.add(new Employee("2fname32", "2lname32", "2dept2", 32,
          BigDecimal.valueOf(2000.00)));
      empList2.add(new Employee("2fname34", "2lname34", "2dept2", 34,
          BigDecimal.valueOf(900.00)));
      empList2.add(new Employee("2fname36", "2lname36", "2dept2", 36,
          BigDecimal.valueOf(4000.00)));
      
      List<Employee> concatenatedArray = new ArrayList<Employee>(
          empList1.size() + empList2.size());
      concatenatedArray.addAll(empList1);
      concatenatedArray.addAll(empList2);
      
      List<Employee> mergedArray = SortedListMerge.mergeSmall(empList1,
          empList2, new EmpAgeComparator());
      Collections.sort(concatenatedArray, new EmpAgeComparator());
      
      assertEquals(concatenatedArray, mergedArray);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    
  }
  
  @Test
  public void whenMergeSmallCalled_givenTwoSortedArraysWithBiggerSecond_oneMergedArrayReturned() {
    try {
      List<Employee> empList1 = new ArrayList<Employee>(5);
      empList1.add(new Employee("1fname31", "1lname31", "1dept2", 31,
          BigDecimal.valueOf(2000.00)));
      empList1.add(new Employee("1fname33", "1lname33", "1dept2", 33,
          BigDecimal.valueOf(900.00)));
      empList1.add(new Employee("1fname35", "1lname35", "1dept2", 35,
          BigDecimal.valueOf(4000.00)));
      
      List<Employee> empList2 = new ArrayList<Employee>(5);
      empList2.add(new Employee("2fname32", "2lname32", "2dept2", 32,
          BigDecimal.valueOf(2000.00)));
      empList2.add(new Employee("2fname34", "2lname34", "2dept2", 34,
          BigDecimal.valueOf(900.00)));
      empList2.add(new Employee("2fname36", "2lname36", "2dept2", 36,
          BigDecimal.valueOf(4000.00)));
      empList2.add(new Employee("2fname38", "2lname38", "2dept2", 38,
          BigDecimal.valueOf(2400.00)));
      empList2.add(new Employee("2fname40", "2lname40", "2dept2", 40,
          BigDecimal.valueOf(6000.00)));
      
      List<Employee> concatenatedArray = new ArrayList<Employee>(
          empList1.size() + empList2.size());
      concatenatedArray.addAll(empList1);
      concatenatedArray.addAll(empList2);
      
      List<Employee> mergedArray = SortedListMerge.mergeSmall(empList1,
          empList2, new EmpAgeComparator());
      Collections.sort(concatenatedArray, new EmpAgeComparator());
      
      assertEquals(concatenatedArray, mergedArray);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    
  }
}
