package com.sapbasu.javastudy.support;

import java.math.BigDecimal;

public class Employee {
  
  private String firstName;
  private String lastName;
  private String department;
  private int age;
  private BigDecimal salary;
  
  public Employee(String firstName, String lastName, String department, int age,
      BigDecimal salary) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.department = department;
    this.age = age;
    this.salary = salary;
  }
  
  public String getFirstName() {
    return firstName;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public String getLastName() {
    return lastName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String getDepartment() {
    return department;
  }
  
  public void setDepartment(String department) {
    this.department = department;
  }
  
  public int getAge() {
    return age;
  }
  
  public void setAge(int age) {
    this.age = age;
  }
  
  public BigDecimal getSalary() {
    return salary;
  }
  
  public void setSalary(BigDecimal salary) {
    this.salary = salary;
  }
  
  @Override
  public String toString() {
    return "Employee [firstName=" + firstName + ", lastName=" + lastName
        + ", department=" + department + ", age=" + age + ", salary=" + salary
        + "]";
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + age;
    result = prime * result
        + ((department == null) ? 0 : department.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((salary == null) ? 0 : salary.hashCode());
    return result;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Employee other = (Employee) obj;
    if (age != other.age) return false;
    if (department == null) {
      if (other.department != null) return false;
    } else if (!department.equals(other.department)) return false;
    if (firstName == null) {
      if (other.firstName != null) return false;
    } else if (!firstName.equals(other.firstName)) return false;
    if (lastName == null) {
      if (other.lastName != null) return false;
    } else if (!lastName.equals(other.lastName)) return false;
    if (salary == null) {
      if (other.salary != null) return false;
    } else if (!salary.equals(other.salary)) return false;
    return true;
  }
  
}
