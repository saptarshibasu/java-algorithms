package com.sapbasu.javastudy;

import java.util.ArrayList;
import java.util.List;
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
  
  /**
   * 
   * Given a String prefix, this function can return the list of Strings that
   * have the input prefix
   * 
   * @param input
   *          String prefix
   * @return
   */
  public List<String> autoComplete(String input) {
    List<String> strList = new ArrayList<String>();
    getLastCharacterNode(input).ifPresent((value) -> {
      if (value.isWordBoundary()) {
        strList.add(input);
      }
      if (value.getChildCount() > 0) {
        getCompleteStrings(value, input, strList);
      }
    });
    return strList;
  }
  
  private void getCompleteStrings(TrieNode node, String prefix,
      List<String> strList) {
    if (node.getChildCount() == 0) {
      strList.add(prefix);
    } else {
      for (int i = 0; i < node.children.length; i++) {
        if (node.children[i] != null) {
          getCompleteStrings(node.children[i],
              prefix + String.valueOf(Character.toChars((i + 'a'))), strList);
        }
      }
    }
  }
  
  private Optional<TrieNode> getLastCharacterNode(String input) {
    TrieNode[] node = new TrieNode[1];
    node[0] = rootNode;
    for (int i = 0; i < input.length(); i++) {
      node[0] = node[0].exists(input.charAt(i)).orElseGet(() -> {
        return null;
      });
      if (node[0] == null) {
        break;
      }
    }
    return Optional.ofNullable(node[0]);
  }
  
  private void add(String input, TrieNode node) {
    Objects.requireNonNull(input, "Input cannot be null");
    Objects.requireNonNull(node, "Input cannot be null");
    
    TrieNode addedNode = node.add(input.charAt(0));
    String remainingString = input.substring(1, input.length());
    if (remainingString.length() > 0) {
      add(remainingString, addedNode);
    } else {
      addedNode.setWordBoundary();
    }
  }
  
  private boolean exists(String input, TrieNode node) {
    
    Objects.requireNonNull(input, "Input cannot be null");
    Objects.requireNonNull(node, "Node cannot be null");
    
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
    private int childCount = 0;
    private boolean wordBoundary = false;
    
    public void setWordBoundary() {
      this.wordBoundary = true;
    }
    
    public void resetWordBoundary() {
      this.wordBoundary = false;
    }
    
    public int getChildCount() {
      return childCount;
    }
    
    public boolean isWordBoundary() {
      return wordBoundary;
    }
    
    public Optional<TrieNode> exists(char input) {
      
      Objects.requireNonNull(input, "Input cannpt be null");
      
      if (!Pattern.matches("[A-Za-z]", String.valueOf(input)))
        throw new IllegalArgumentException(
            "The input must be in the range 'a' to 'z' or 'A' to 'Z'");
      TrieNode node = children[Character.toLowerCase(input) - 'a'];
      return Optional.ofNullable(node);
    }
    
    public TrieNode add(char input) {
      
      Objects.requireNonNull(input, "Input cannpt be null");
      
      if (!Pattern.matches("[A-Za-z]", String.valueOf(input)))
        throw new IllegalArgumentException(
            "The input must be in the range 'a' to 'z' or 'A' to 'Z'");
      if (children[Character.toLowerCase(input) - 'a'] == null) {
        children[Character.toLowerCase(input) - 'a'] = new TrieNode();
        childCount++;
      }
      return children[Character.toLowerCase(input) - 'a'];
    }
    
  }
}
