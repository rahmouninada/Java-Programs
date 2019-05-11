/*
* Binary Search Tree Implementation in Java
* Classes: BST_Node.java, BST.java
* Interface: BST_Interface.java
*/

package src.BinarySearchTree;

/* BST INTERFACE */
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

/* BST NODE CLASS */
public class BST_Node {
	String data;
	BST_Node left;
	BST_Node right;

	BST_Node(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public BST_Node getLeft() {
		return left;
	}

	public BST_Node getRight() {
		return right;
	}

	public boolean insertNode(String s) {
		if (this.data.compareTo(s) == 0) {
			return false;
		} else if (this.data.compareTo(s) < 0) {
			if (!this.hasRight()) {
				this.right = new BST_Node(s);
				return true;
			} else {
				return right.insertNode(s);
			}
		} else {
			if (!this.hasLeft()) {
				this.left = new BST_Node(s);
				return true;
			} else {
				return left.insertNode(s);
			}
		}
	}

	public int getHeight(BST_Node node) {
		if (node == null) {
			return 0;
		} else {
			int LH = getHeight(node.left);
			int RH = getHeight(node.right);

			if (LH > RH) {
				return LH ;
			}
			return RH;
		}
	}

	public boolean containsNode(String s) {
		if (this.data.compareTo(s) == 0) {
			return true;
		} else if (this.data.compareTo(s) < 0) {
			if (this.hasRight()) { 
				return right.containsNode(s);
			} else {
				return false;
			}
		} else {
			if (this.hasLeft()) {
				return left.containsNode(s);
			} else {
				return false;
			}
		}
	}

	public boolean removeNode(String s, BST_Node parent) { 
		if (this.data.compareTo(s) < 0 ) {
			if (right != null) {
				return right.removeNode(s, this);
			} else {
				return false;
			}
		} else if (this.data.compareTo(s) > 0) {
			if (this.hasLeft()) {
				return left.removeNode(s, this);
			} else {
				return false;
			}
		} else {
			if (!this.hasLeft() && !this.hasRight()) {
				if (parent.left == this) {
					parent.left = null;
					return true;
				} else if (parent.right == this) {
					parent.right = null;
					return true;
				}
			} else if (!this.hasLeft() & right != null) {
				if (parent.left == this) {
					parent.left = this.right;
					return true;
				} else if (parent.right == this) {
					parent.right = this.right;
					return true;
				}
			} else {
				this.data = this.left.findMax().data;
				return this.left.removeNode(this.data, this);
			}
			return true;
		}
		
	}

	public BST_Node findMax() {

		if (!this.hasRight()) {
			return this;
		} else {
			return this.right.findMax();
		}

	}

	public BST_Node findMin() {
		if (!this.hasLeft()) {
			return this;
		} else {
			return this.left.findMax();
		}
	}

	// Helper Methods:
	public boolean hasLeft() {
		if (this.getLeft() == null) {
			return false;
		}
		return true;
	}

	public boolean hasRight() {
		if (this.getRight() == null) {
			return false;
		}
		return true;
	}
	
	public String toString() {
		return "Data: " + this.data + ", Left: " + ((this.left != null) ? left.data : "null") + ",Right: "
				+ ((this.right != null) ? right.data : "null");
	}
}

/* BST CLASS */
public class BST implements BST_Interface {
	public BST_Node root;
	int size;

	public BST() {
		size = 0;
		root = null;
	}

	public BST_Node getRoot() { // used for testing, please leave as is
		return root;
	}

	public String findMin() {
		if (this.size() == 0) { // if tree is empty, return null
			return null;
		} else if (this.size() == 1) {
			return root.getData();
		} else {
			return root.findMin().getData();
		}
	}

	public boolean insert(String s) {
		if (this.size() == 0) {
			this.root = new BST_Node(s);
			size++;
			return true;
		}
		if (this.contains(s)) { // if s is already in tree, no change & return false
			return false;
		} else {
			size++;
			return root.insertNode(s);
		}
	}

	public int height() {
		if (this.empty()) { // if tree is empty return -1
			return -1;
		} else {
			return root.getHeight(root);
		}
	}

	public boolean contains(String s) {
		if (root != null && root.getData().compareTo(s) == 0) { // if root is the string, return true
			return true;
		} else {
			return root.containsNode(s);
		}
	}

	public boolean remove(String s) {
		if (root == null ) {
			return false;
		} else {
			if (root.data.compareTo(s) == 0) {
				BST_Node node = new BST_Node (root.data);
				node.left = root;
				boolean r = root.removeNode(s, node);
				root = node.left;
				size--; 
				return r;
			} else {
				if (root.removeNode(s, null)) {
					size--;
					return true;
				}
				return false;
			}
		}
		
	}

	public String findMax() {
		if (this.size() == 0) { // if tree is empty, return null
			return null;
		} else if (this.size() == 1) {
			return root.getData();
		} else {
			return root.findMax().getData();
		}
	}

	public boolean empty() { // returns true if empty
		if (this.size() == 0) {
			return true;
		}
		return false;
	}

	public int size() { // returns number of elements stored in tree
		return size;
	}
 }
