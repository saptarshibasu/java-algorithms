package com.sapbasu.javastudy;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * 
 * This is an implementation of Trie data structure.
 * 
 */
public class Trie {
  private final TrieNode rootNode = new TrieNode();
  
  /**
   * 
   * The method adds a {@code String} to the Trie data structure.
   * 
   * @param input
   *          The input {@code String} to be added
   * 
   */
  public void add(String input) {
    add(input, rootNode);
  }
  
  /**
   * 
   * The method searches for a String in the Trie data structure.
   * 
   * @param input
   *          The input {@code String} to be searched
   * @return The return value is true if the input value is present in the data
   *         structure. Otherwise, the return value is false.
   * 
   */
  public boolean exists(String input) {
    Objects.requireNonNull(input, "Input cannot be null");
    
    return exists(input, rootNode);
  }
  
  private void add(String input, TrieNode node) {
    Objects.requireNonNull(input, "Input cannot be null");
    Objects.requireNonNull(node, "Input cannot be null");
    
    TrieNode addedNode = node.add(input.charAt(0));
    String remainingString = input.substring(1, input.length());
    if (remainingString.length() > 0) {
      add(remainingString, addedNode);
    }
  }
  
  private boolean exists(String input, TrieNode node) {
    
    Objects.requireNonNull(input, "Input cannot be null");
    Objects.requireNonNull(node, "Input cannot be null");
    
    if (input.length() == 0) return true;
    
    String remainingString = input.substring(1, input.length());
    TrieNode[] returnedNode = new TrieNode[1];
    node.exists(input.charAt(0)).ifPresent((value) -> {
      returnedNode[0] = value;
    });
    
    if (returnedNode[0] == null) return false;
    else return exists(remainingString, returnedNode[0]);
  }
  
  private class TrieNode {
    
    public static final int ALPHABET_SIZE = 26;
    
    private TrieNode[] children = new TrieNode[ALPHABET_SIZE];
    private boolean leafNode = true;
    private int childCount = 0;
    
    public TrieNode() {
      Arrays.fill(children, null);
    }
    
    public Optional<TrieNode> exists(char input) {
      
      Objects.requireNonNull(input, "Input cannpt be null");
      
      if (!Pattern.matches("[A-Za-z]", String.valueOf(input)))
        throw new IllegalArgumentException(
            "The input must be in the range 'a' to 'z' or 'A' to 'Z'");
      TrieNode node = children[Character.toLowerCase(input) - 'a'];
      return Optional.ofNullable(node);
    }
    
    public boolean isLeafNode() {
      return leafNode;
    }
    
    public TrieNode add(char input) {
      
      Objects.requireNonNull(input, "Input cannpt be null");
      
      if (!Pattern.matches("[A-Za-z]", String.valueOf(input)))
        throw new IllegalArgumentException(
            "The input must be in the range 'a' to 'z' or 'A' to 'Z'");
      leafNode = false;
      if (children[Character.toLowerCase(input) - 'a'] == null) {
        children[Character.toLowerCase(input) - 'a'] = new TrieNode();
        childCount++;
      }
      return children[Character.toLowerCase(input) - 'a'];
    }
    
  }
}
