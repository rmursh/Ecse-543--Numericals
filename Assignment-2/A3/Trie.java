package a3posted;

//  Name: Tamima Rahman

//  Student ID: 260673581

//  COMP 250 - Introduction to Computer Science - Fall 2016

//  Assignment #3 - Question 1


import java.util.*;

/*
 *  Trie class.  Each node is associated with a prefix of some key 
 *  stored in the trie.   (Any string is a prefix of itself.)
 */

public class Trie
{
	private TrieNode root;

	// Empty trie has just a root node.  All the children are null.

	public Trie() 
	{
		root = new TrieNode();
	}

	public TrieNode getRoot(){
		return root;
	}

	
	/*
	 * Insert key into the trie.  First, find the longest 
	 * prefix of a key that is already in the trie (use getPrefixNode() below). 
	 * Then, add TrieNode(s) such that the key is inserted 
	 * according to the specification in PDF.
	 */
	public void insert(String key)
	{
		 //  ADD YOUR CODE BELOW HERE
		 if (key.equals(""))
		 {
			 return;
		 }
	     TrieNode currentNode=getPrefixNode(key);
	     int count=currentNode.depth;
	     while(count< key.length()){
	    	currentNode = currentNode.createChild(key.charAt(count));
	    	count++; 
	     }
		 
	     currentNode.setEndOfKey(true);	

		//  ADD YOUR ABOVE HERE
	}

	// insert each key in the list (keys)

	public void loadKeys(ArrayList<String> keys)
	{
		for (int i = 0; i < keys.size(); i++)
		{
			insert(keys.get(i));
		}
		return;
	}

	/*
	 * Given an input key, return the TrieNode corresponding the longest prefix that is found. 
	 * If no prefix is found, return the root. 
	 * In the example in the PDF, running getPrefixNode("any") should return the
	 * dashed node under "n", since "an" is the longest prefix of "any" in the trie. 
	 */
	private TrieNode getPrefixNode(String key)
	{
		//   ADD YOUR CODE BELOW HERE
		if (key.equals(""))
		{
		   return root;
		}
		int count = 0;
		String check = "";
		TrieNode currentNode = root;
		
        while(currentNode.getChild(key.charAt(count)) != null ){
        	
        	currentNode=currentNode.getChild(key.charAt(count));
        	check += key.charAt(count);
        	if(check.equals(key)){
        		break;
        	}
        	count++;
        }
       
		return currentNode;	
	    //  REPLACE THIS STUB
		//
		//   ADD YOUR CODE ABOVE HERE

	}

	/*
	 * Similar to getPrefixNode() but now return the prefix as a String, rather than as a TrieNode.
	 */

	public String getPrefix(String key)
	{
		return getPrefixNode(key).toString();
	}

	
	/*
	 *  Return true if key is contained in the trie (i.e. it was added by insert), false otherwise.
	 *  Hint:  any string is a prefix of itself, so you can use getPrefixNode().
	 */
	public boolean contains(String key)
	{  
		//   ADD YOUR CODE BELOW HERE

		if(getPrefix(key).equals(key) && getPrefixNode(key).isEndOfKey()){
			return true;
		}else{
			return false;
			
		}
		
		//   ADD YOUR CODE ABOVE HERE
	}

	/*
	 *  Return a list of all keys in the trie that have the given prefix. 
	 */
	public ArrayList<String> getAllPrefixMatches( String prefix )
	{
		//  ADD YOUR CODE BELOW 
	    TrieNode commonNode = getPrefixNode(prefix);
	    //String common=getPrefix(prefix);	
	    ArrayList<String> stringList = new ArrayList<String>();
	    if(commonNode.depth == prefix.length()){
	    	treeTraversal(commonNode, stringList);
	    	
	    }
	    

		//  ADD YOUR CODE ABOVE HERE

		return stringList;
		
	}
	
	//This is a helper method for traversing a tree using recursion

	public void treeTraversal(TrieNode root, ArrayList<String> stringOfChildren){
		
		if(root.isEndOfKey()){
			stringOfChildren.add(root.toString());	
		}
		
		for(int i=0; i < root.children.length; i++){
		if(root.children[i] != null){	
			treeTraversal(root.children[i], stringOfChildren);
		}
			
		}
		
		
		
	}
	/*
	 *  A node in a Trie (prefix) tree.  
	 *  It contains an array of children: one for each possible character.
	 *  The ith child of a node corresponds to character (char)i
	 *  which is the UNICODE (and ASCII) value of i. 
	 *  Similarly the index of character c is (int)c.
	 *  So children[97] = children[ (int) 'a']  would contain the child for 'a' 
	 *  since (char)97 == 'a'   and  (int)'a' == 97.
	 * 
	 * References:
	 * -For all unicode charactors, see http://unicode.org/charts
	 *  in particular, the ascii characters are listed at http://unicode.org/charts/PDF/U0000.pdf
	 * -For ascii table, see http://www.asciitable.com/
	 * -For basic idea of converting (casting) from one type to another, see 
	 *  any intro to Java book (index "primitive type conversions"), or google
	 *  that phrase.   We will cover casting of reference types when get to the
	 *  Object Oriented Design part of this course.
	 */

	private class TrieNode
	{
		/*  
		 *   Highest allowable character index is NUMCHILDREN-1
		 *   (assuming one-byte ASCII i.e. "extended ASCII")
		 *   
		 *   NUMCHILDREN is constant (static and final)
		 *   To access it, write "TrieNode.NUMCHILDREN"
		 */

		public static final int NUMCHILDREN = 256;

		private TrieNode   parent;
		private TrieNode[] children;
		private int        depth;            // 0 for root, 1 for root's children, 2 for their children, etc..
		private char       charInParent;    // Character associated with edge between this node and its parent.
		        			        		// See comment above for relationship between an index in 0 to 255 and a char value.
		private boolean endOfKey;   // Set to true if prefix associated with this node is also a key.

		// Constructor for new, empty node with NUMCHILDREN children.  All the children are null. 

		public TrieNode()
		{
			children = new TrieNode[NUMCHILDREN];
			endOfKey = false;
			depth = 0; 
			charInParent = (char)0; 
		}

		
		/*
		 *  Add a child to current node.  The child is associated with the character specified by
		 *  the method parameter.  Make sure you set all fields in the child node.
		 *  
		 *  To implement this method, see the comment above the inner class TrieNode declaration.  
		 */
		public TrieNode createChild(char  c) 
		{	   
			TrieNode child = new TrieNode();
			// ADD YOUR CODE BELOW HERE
            child.parent = this;
            child.children = new TrieNode[NUMCHILDREN];
            child.depth = this.depth+1;
            child.charInParent = c; 
            children[(int)c] = child;
            
			// ADD YOUR CODE ABOVE HERE

			return child;
		}

		// Get the child node associated with a given character, i.e. that character is "on" 
		// the edge from this node to the child.  The child could be null.  

		public TrieNode getChild(char c) 
		{
			return children[ c ];
		}

		// Test whether the path from the root to this node is a key in the trie.  
		// Return true if it is, false if it is prefix but not a key.

		public boolean isEndOfKey() 
		{
			return endOfKey;
		}

		// Set to true for the node associated with the last character of an input word

		public void setEndOfKey(boolean endOfKey)
		{
			this.endOfKey = endOfKey;
		}

		/*  
		 *  Return the prefix (as a String) associated with this node.  This prefix
         *  is defined by descending from the root to this node.  However, you will
         *  find it is easier to implement by ascending from the node to the root,
         *  composing the prefix string from its last character to its first.  
		 *
		 *  This overrides the default toString() method.
		 */

		public String toString()
		{
			// ADD YOUR CODE BELOW HERE
			if(this.depth == 0){
				
				return "";
			}
			String result = "";
			TrieNode currentNode = this;
			result += currentNode.charInParent;
			while(currentNode.parent.depth != 0){
				currentNode = currentNode.parent;
				result += currentNode.charInParent;
			}
			
			return reverse(result);
			
			// ADD YOUR CODE ABOVE HERE
		}
//		This is a helper method for toString which 
//		reverses a string, as we are traversing from node to root
		private String reverse(String input){
		    char[] in = input.toCharArray();
		    int begin=0;
		    int end=in.length-1;
		    char temp;
		    while(end>begin){
		        temp = in[begin];
		        in[begin]=in[end];
		        in[end] = temp;
		        end--;
		        begin++;
		    }
		    return new String(in);
		}

	}


}

