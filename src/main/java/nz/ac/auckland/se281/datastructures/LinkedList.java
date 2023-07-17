package nz.ac.auckland.se281.datastructures;

/**
 * A linked list implementation of a list.
 *
 * @param <T> The type of the elements in the list.
 */
public class LinkedList<T> {
  protected Node<T> head;
  protected Node<T> tail;

  /** Creates a new linked list. */
  public LinkedList() {
    /*
       Contructor for the class
    */
    this.head = null;
    this.tail = this.head;
  }

  /**
   * Adds an element to the end of the linked list. If the list is empty, the new element will be
   * the head and the tail.
   */
  public void add(T data) {
    /*
        Adds an element to the end of the linked list. If the list is empty, the new element will be
        the head and the tail.
    */
    Node<T> newNode = new Node<T>(data);
    if (head == null) {
      head = newNode;
      tail = head;
      head.setPrev(null);
      tail.setNext(null);
      return;
    }
    newNode.setPrev(tail);
    tail.setNext(newNode);
    tail = newNode;
  }

  /**
   * Inserts an element at the specified index by traversing the list and checking the index of each
   * element. If the index of the element is equal to the specified index, the element is inserted
   * at that index and the element that was previously at that index is moved to the next index.
   *
   * @param index The index to insert the element at.
   * @param data The element to insert.
   * @throws IndexOutOfBoundsException if the index is out of range.
   * @throws NullPointerException if the element is null.
   */
  public void insert(int index, T data) throws IndexOutOfBoundsException {
    /*
        Inserts an element at the specified index by traversing the list
        and checking the index of each element. If the index of the element
        is equal to the specified index, the element is inserted
        at that index and the element that was previously at that index is
        moved to the next index.
    */
    if (index < 0 | index > this.size()) {
      throw new IndexOutOfBoundsException();
    }

    int i = 0;
    Node<T> n = this.head;
    Node<T> newNode = new Node<T>(data);

    if (index == 0) {
      this.head.setPrev(newNode);
      newNode.setNext(this.head);
      this.head = newNode;
      head.setPrev(null);
      return;
    }

    while (n != null) {
      if (i == (index - 1)) {
        if (index == this.size()) {
          newNode.setPrev(tail);
          tail.setNext(newNode);
          tail = tail.getNext();
          return;
        }
        n.getNext().setPrev(newNode);
        newNode.setNext(n.getNext());
        newNode.setPrev(n);
        n.setNext(newNode);
        return;
      }
      i++;
      n = n.getNext();
    }
  }

  /**
   * Returns the number of elements in this list by traversing the list and counting the number of
   * elements.
   *
   * @return The number of elements in this list.
   */
  public int size() {
    /*
        Returns the number of elements in this list by traversing
        the list and counting the number of elements.
    */
    int count = 0;
    Node<T> n = this.head;
    while (n != null) {
      count++;
      n = n.getNext();
    }

    return count;
  }

  /**
   * Returns true if this list contains no elements.
   *
   * @return True if this list contains no elements.
   */
  public boolean isEmpty() {
    /*
        Returns true if this list contains no elements.
    */
    if (this.size() == 0) {
      return true;
    }

    return false;
  }
}
