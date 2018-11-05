package com.sapbasu.javastudy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrieTest {
  
  @Test
  public void whenStringsSearched_givenStringsAdded_stringIsFound() {
    try {
      Trie trie = new Trie();
      trie.add("Delhi");
      trie.add("Chennai");
      
      assertTrue(trie.exists("Delhi"));
      assertTrue(trie.exists("Chennai"));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void whenStringsSearched_givenStringsNotAdded_stringIsNotFound() {
    try {
      Trie trie = new Trie();
      trie.add("Delhi");
      trie.add("Chennai");
      
      assertTrue(!trie.exists("Kolkata"));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @Test
  public void whenAutoCompleteInvoked_givenStringPrefix_stringListReturned() {
    try {
      Trie trie = new Trie();
      trie.add("Delhi");
      trie.add("Chennai");
      trie.add("Dehradun");
      trie.add("Deogarh");
      trie.add("Chandigarh");
      assertTrue(trie.autoComplete("De").size() == 3);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Test
  public void whenAutoCompleteInvoked_givenCompleteStringPrefix_stringListWithSingleStringReturned() {
    try {
      Trie trie = new Trie();
      trie.add("Delhi");
      trie.add("Chennai");
      trie.add("Dehradun");
      trie.add("Deogarh");
      trie.add("Chandigarh");
      assertTrue(trie.autoComplete("Delhi").size() == 1);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
