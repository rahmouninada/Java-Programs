/* Doubly Linked List Implementation in Java
* Classes: LinkedListImpl.java, Node.java
* Interface: LIST_Interface.java
*/

package src.LinkedList;

/* LIST INTERFACE */
public interface LIST_Interface {
    public boolean insert(double elt, int index);
    public boolean remove(int index);
    public double get(int index);
    public int size();
    public boolean isEmpty();
    public void clear();
}

/* NODE CLASS: */
public class Node { // Node Object (Objects in list)
    public double data;
    public Node next; // links this node to the next Node in the List
	public Node prev; // links this node to previous node

	public Node(double data) {
	    this.data = data;
		this.next = null;
		this.prev = null;
	}

	public String toString() { // prints next and previous of a Node
		return "data: " + data + "\thasNext: " + (next != null) + "\t\thasPrev: " + (prev != null);
	}
}

/* LINKED LIST CLASS: */
public class LinkedListImpl implements LIST_Interface {
    Node sentinel; // entry point to linked list (head)

    public LinkedListImpl() {
        sentinel = new Node(0); // Note: root's data NOT a true part of data set

    public Node getRoot() { // used to grab start of linked list easily
        return sentinel;
	}

    public void clear() {
        this.sentinel.next = null;
        this.sentinel.prev = null;
	}

    public boolean isEmpty() {
        if (sentinel.next == null) {
            return true;
        }
        return false;
    }
    
    public int size() {
        if (this.isEmpty()) {
            return 0;
        } else if (sentinel.prev.equals(sentinel.next)) {
            return 1;
        } else {
            Node currentNode = sentinel.next;
            int counter = 1;
            while (currentNode.next != sentinel) {
                currentNode = currentNode.next;
                counter++;
            }
            return counter;
        }
    }
    
    public boolean insert(double elt, int index) {
        Node newNode = new Node(elt);
		Node currentNode = sentinel;

		if (index < 0 || index > this.size()) {
            return false;
		} else if (this.size() == 0) {
			sentinel.next = newNode;
			sentinel.prev = newNode;
			newNode.prev = sentinel;
			newNode.next = sentinel;
			return true;
		} else if (index == 0) {
			sentinel.next.prev = newNode;
			newNode.next = sentinel.next;
			sentinel.next = newNode;
			newNode.prev = sentinel;
			return true;
		} else {
			for (int i = 0; i < this.size() + 1; i++) {
				if (i == index) {
					currentNode.next.prev = newNode;
					newNode.next = currentNode.next;
					currentNode.next = newNode;
					newNode.prev = currentNode;
					break;
				}
				currentNode = currentNode.next;
			}
			return true;
		}
	}

	public boolean remove(int index) {

		if (index >= this.size() || index < 0 || this.isEmpty()) {
			return false;
		} else if (this.size() == 1 & index == 0) {
			sentinel.next = null;
			sentinel.prev = null;
			return true;
		} else {
			Node currentNode = sentinel.next;
			for (int i = 0; i < this.size(); i++) {
				if (i == index) {
					currentNode.prev.next = currentNode.next;
					currentNode.next.prev = currentNode.prev;
					return true;
				}
				currentNode = currentNode.next;
			}
			return true;
		}
	}

	public double get(int index) {
		if (index >= this.size() || index < 0) {
			return Double.NaN;
		} else {
			Node currentNode = sentinel.next;
			for (int i = 0; i < this.size(); i++) {
				if (i == index) {
					return currentNode.data;
				}
				currentNode = currentNode.next;
			}
			return Double.NaN;
		}
	}

}
