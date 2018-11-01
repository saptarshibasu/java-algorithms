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
    } catch(Exception e) {
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
    } catch(Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
