package com.sapbasu.javastudy;

public class Unicode {
  public static void main(String argv[]) {
    String str="😂s🍆";
    System.out.println(str.length());
    System.out.println(str.substring(0, str.indexOf('s')));
  }
}
