package com.sapbasu.javastudy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SortedListMerge<T> {
  
  public static <T> List<T> mergeSmall(List<T> firstList, List<T> secondList,
      Comparator<T> comp) {
    
    Objects.requireNonNull(firstList, "The first List cannot be null");
    
    Objects.requireNonNull(secondList, "The second List cannot be null");
    
    Objects.requireNonNull(comp, "The comparator cannot be null");
    
    if (firstList.size() < 1 || secondList.size() < 1)
      throw new IllegalArgumentException("Both input lists must have values");
    
    List<T> mergedList = new ArrayList<T>(firstList.size() + secondList.size());
    
    int combinedListSize = firstList.size() + secondList.size();
    
    for (int mergedListPos = 0, firstListPos = 0, secondListPos = 0; mergedListPos < combinedListSize; mergedListPos++) {
      
      if (firstListPos >= firstList.size()) {
        mergedList.addAll(secondList.subList(secondListPos, secondList.size()));
        return mergedList;
      }
      
      if (secondListPos >= secondList.size()) {
        mergedList.addAll(firstList.subList(firstListPos, firstList.size()));
        return mergedList;
      }
      
      if (comp.compare(firstList.get(firstListPos),
          secondList.get(secondListPos)) <= 0) {
        mergedList.add(firstList.get(firstListPos++));
      } else {
        mergedList.add(secondList.get(secondListPos++));
      }
    }
    
    return mergedList;
  }
}
