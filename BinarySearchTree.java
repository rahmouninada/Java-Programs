/*
* Binary Search Tree Implementation in Java
* Classes: BST_Node.java, BST.java
* Interface: BST_Interface.java
*/

package src.BinarySearchTree;

/* BST Interface */
public interface BST_Interface {
		  public boolean insert(String s);
		  public boolean remove(String s);
		  public String findMin();
		  public String findMax();
		  public boolean empty();
		  public boolean contains(String s);
		  public int size();
		  public int height();
		  public BST_Node getRoot();
}
