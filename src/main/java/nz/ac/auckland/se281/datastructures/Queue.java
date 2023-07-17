package nz.ac.auckland.se281.datastructures;

/**
 * A queue implementation of a list.
 *
 * @param <T> The type of the elements in the list.
 */
public class Queue<T> {
  // doubly-linked list, bfs
  protected Node<T> head;
  protected Node<T> tail;

  /** Creates a new queue. */
  public Queue() {
    /*
       Contructor for the class
    */
    this.head = null;
    this.tail = this.head;
  }

  /**
   * Adds an element to the end of the queue. If the queue is empty, the new element will be the
   * head and the tail.
   *
   * @param data The element to add.
   */
  public void enqueue(T data) {
    /*
        Adds an element to the end of the queue. If the queue is empty, the new element will be the
        head and the tail.
    */
    Node<T> newNode = new Node<T>(data);

    if (head == null) {
      head = new Node<T>(data);
      tail = head;
      return;
    }

    tail.setNext(newNode);
    tail = newNode;
  }

  /**
   * Removes the element at the front of the queue and returns that element as the value of this
   * function.
   *
   * @return The element at the front of the queue.
   * @throws NoSuchElementException if this queue is empty.
   */
  public T dequeue() {
    /*
        Removes the element at the front of the queue and returns that element as the value of this
        function.
    */
    Node<T> temp = this.head;

    this.head = this.head.getNext();
    return temp.getData();
  }

  /**
   * Looks at the element at the front of the queue without removing it from the queue.
   *
   * @return The element at the front of the queue.
   * @throws NoSuchElementException if this queue is empty.
   */
  public T peek() {
    /*
        Looks at the element at the front of the queue without removing it from the queue.
    */
    return this.head.getData();
  }

  /**
   * Returns true if this queue contains no elements.
   *
   * @return True if this queue contains no elements.
   */
  public boolean isEmpty() {
    /*
        Returns true if this queue contains no elements.
    */
    if (this.size() == 0) {
      return true;
    }

    return false;
  }

  /**
   * Returns the number of elements in this queue.
   *
   * @return The number of elements in this queue.
   */
  public int size() {
    /*
        Returns the number of elements in this queue.
    */
    int count = 0;
    Node<T> n = this.head;
    while (n != null) {
      count++;
      n = n.getNext();
    }

    return count;
  }
}
