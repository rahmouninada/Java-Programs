# Doubly Linked List

 ------               ------               ------               ------
|      |   <------>  |      |   <------>  |      |   <------>  |      |
|      |             |      |             |      |             |      |
 ------               ------               ------               ------
    B                sentinal                 A                    B

IMPLEMENTATION:
- sentinal: head
    - previous of sentinal is the last node in the list; next value of last node in list (B) is sentinal
    - sentinal is NOT part of data set

METHODS:
  INSERT
    - in: a double (the data element), and an integer (position index)
    - return: boolean, return true if insert is successful, false otherwise
    - effect: list state altered so that elt is located at the specified index; the list is size bigger by one;
              all elts that were at the specified index or after moved down one slot
    - error: if index is beyond list size range, return false ; valid inserts take place either at a location where
             a list elt already exists, or at the location that is one beyond the last element

  REMOVE
    - in: an integer (the index of the element to take out of the list)
    - return: boolean. return true if the remove is successful, false otherwise
    - effect: list state Node at the specified index is decoupled ; list size decreases by one
    - errors: return false if specified index is not in the list
    
  GET
    - in: an integer, the index of the element item to return
    - return: double, the element stored at index, or Double.NaN
    - effect: no change in state of the list
    - error: return Double.NaN if index not in list

  SIZE
    - in: nothing
    - return: number of elements stored in the list
    - effect: no change to list state

  ISEMPTY
    - in: nothing
    - return: boolean, true if the list has no elements in it, true is size is 0
    - effect: no change to list state

  CLEAR
    - in: nothing
    - return: void
    - effect: list is left with size 0, no elements in it, consists of just the original root Node
