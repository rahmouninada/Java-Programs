/*
* Minimum Binary Heap Implementation in Java
* Classes: EntryPair.java, MinBinHeap.java
* Interfaces: EntryPair_Interface.java, Heap_Interface.java
*/

package src.MinBinHeap;

/* ENTRY PAIR INTERFACE */
public interface EntryPair_Interface {
    String getValue();
    int getPriority();
}

/* HEAP INTERFACE */
public interface Heap_Interface {
    void insert(EntryPair entry);
    void delMin();
    EntryPair getMin();
    int size();
    void build(EntryPair [] entries);
    EntryPair[] getHeap();
}

/* ENTRY PAIR CLASS */
public class EntryPair implements EntryPair_Interface {
	public String value;
	public int priority;

	public EntryPair(String Value, int aPriority) {
		value = Value;
		priority = aPriority;
	}

	public String getValue() {
		return value;
	}

	public int getPriority() {
		return priority;
	}
}

/* MINIMUM BINARY HEAP CLASS *
public class MinBinHeap implements Heap_Interface {
	private EntryPair[] array; // load this array
	private int size;
	private static final int arraySize = 10000; // Everything in the array will initially be null ; build out from array[1]

	public MinBinHeap() {
		this.array = new EntryPair[arraySize];
		array[0] = new EntryPair(null, -100000); // 0th will be unused for simplicity of child/ parent computations
	}

	public void build(EntryPair[] entries) {
		if (entries == null) {
			return;
		}
		size = entries.length;

		for (int i = 0; i < entries.length; i++) {
			array[i + 1] = entries[i];
		}

		if (entries.length == 1) {
			return;
		}

		int i = (int) Math.floor((size) / 2);
		
		while (i > 0) {
			bubbleDown(i);
			i--;
		}
	}

	public void bubbleDown(int index) {
		int parent = index;

		if (hasChild(parent) == -1) { // no children
			return;
		} else if (hasChild(parent) == 0) { // left child only
			int child = parent * 2;
			if (array[child].getPriority() < array[parent].getPriority()) {
				EntryPair temp = array[parent];
				array[parent] = array[child];
				array[child] = temp;
			} else {
				return;
			}
		} else { // both left and right children
			int left = parent * 2;
			int right = (parent * 2) + 1;
			if (array[left].getPriority() < array[right].getPriority()) { // if left is less than right
				if (array[left].getPriority() < array[parent].getPriority()) { // if left is less than parent
					EntryPair temp = array[parent];
					array[parent] = array[left];
					array[left] = temp;
					bubbleDown(left);
					return;
				} else {
					return;
				}
			} else { // if right is less than left
				if (array[right].getPriority() < array[parent].getPriority()) { // if right is less than parent
					EntryPair temp = array[parent];
					array[parent] = array[right];
					array[right] = temp;
					bubbleDown(right);
					return;
				} else {
					return;
				}
			}
		}
	}

	public int hasChild(int index) {
		if ((index * 2) > size) { // if no children
			return -1;
		} else if ((index * 2) + 1 > size) { // if has only left child
			return 0;
		} else {
			return 1; // both children
		}
	}

	public void insert(EntryPair entry) {
		if (size == 0) {
			array[1] = entry;
			size++;
			return;
		}

		int index = size + 1;
		int parent = (int) index / 2;
		array[index] = entry;
		size++;

		while (parent > 0) {
			if (array[index].getPriority() > array[parent].getPriority()) { // if index > parent (right spot!)
				break;
			} else { // switch if not in right spot
				EntryPair temp = array[index];
				array[index] = array[parent];
				array[parent] = temp;
				index = parent;
				parent = (int) index / 2;
			}
		}

	}

	public void delMin() {
		if (size == 1) {
			array[1] = null;
			size = 0;
			return;
		}

		array[1] = array[size];
		array[size] = null;
		size--;

		int index = 1;
		int leftChild = index * 2;
		int rightChild = (index * 2) + 1;

		while (index < size + 1) {
			if (array[leftChild] != null) {
				if (array[rightChild] != null) {
					// if parent has both L & R children:
					if (array[index].getPriority() < array[leftChild].getPriority() // if index < both children
							&& array[index].getPriority() < array[rightChild].getPriority()) {
						break;
					} else if (array[index].getPriority() > array[leftChild].getPriority() // if index > both children,
																							// swap w smaller one
							&& array[index].getPriority() > array[rightChild].getPriority()) {
						if (array[leftChild].getPriority() < array[rightChild].getPriority()) { // if L child is less
							EntryPair temp = array[index];
							array[index] = array[index * 2];
							array[index * 2] = temp;
							index = index * 2;
							leftChild = index * 2;
							rightChild = (index * 2) + 1;
							continue;
						} else { // if R child is less
							EntryPair temp = array[index];
							array[index] = array[(index * 2) + 1];
							array[(index * 2) + 1] = temp;
							index = (index * 2) + 1;
							leftChild = index * 2;
							rightChild = (index * 2) + 1;
							continue;
						}
					} else if (array[index].getPriority() > array[leftChild].getPriority()) { // if index is only
						EntryPair temp = array[index]; // greater than L, swap
						array[index] = array[index * 2];
						array[index * 2] = temp;
						index = index * 2;
						leftChild = index * 2;
						rightChild = (index * 2) + 1;
						continue;
					} else { // if index is only greater than R, swap
						EntryPair temp = array[index];
						array[index] = array[(index * 2) + 1];
						array[(index * 2) + 1] = temp;
						index = index * 2;
						leftChild = index * 2;
						rightChild = (index * 2) + 1;
						continue;
					}

				}
				// if parent has only L child:
				if (array[index].getPriority() > array[leftChild].getPriority()) { // if L child is less, swap
					EntryPair temp = array[index];
					array[index] = array[index * 2];
					array[index * 2] = temp;
					index = index * 2;
					leftChild = index * 2;
					rightChild = (index * 2) + 1;
					continue;
				}
				break;
			}
			// if parent has no children:
			break;
		}
	}

	public EntryPair getMin() {
		if (size == 0) {
			return null;
		}
		return array[1];
	}

	public int size() {
		return size; // returns 0 if heap is empty
	}
}
