package nz.ac.auckland.se281.datastructures;

/**
 * A stack is a data structure that follows the LIFO (last in, first out) principle. This means that
 * the last element added to the stack will be the first element removed from the stack.
 *
 * @param <T> The type of each element in the stack.
 */
public class Stack<T> {
  private Node<T> head;

  // dfs
  /** Creates a new stack. */
  public Stack() {
    /*
       Contructor for the class
    */
    this.head = null;
  }

  /**
   * Pushes an item onto the top of this stack.
   *
   * @param data The item to be pushed onto this stack.
   */
  public void push(T data) {
    /*
        Pushes an item onto the top of this stack.
    */
    Node<T> newNode = new Node<T>(data);

    newNode.setNext(this.head);
    this.head = newNode;
  }

  /**
   * Removes the object at the top of this stack and returns that object as the value of this
   * function.
   *
   * @return The object at the top of this stack.
   * @throws NoSuchElementException if this stack is empty.
   */
  public T pop() {
    /*
        Removes the object at the top of this stack and returns that object as the value of this
        function.
    */
    Node<T> temp = this.head;

    this.head = this.head.getNext();
    return temp.getData();
  }

  /**
   * Looks at the object at the top of this stack without removing it from the stack.
   *
   * @return The object at the top of this stack.
   * @throws NoSuchElementException if this stack is empty.
   */
  public T peek() {
    /*
        Looks at the object at the top of this stack without removing it from the stack.
    */
    return this.head.getData();
  }

  /**
   * Returns the number of elements in this stack.
   *
   * @return The number of elements in this stack.
   */
  public int size() {
    /*
        Returns the number of elements in this stack.
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
   * Returns true if this stack contains no elements.
   *
   * @return true if this stack contains no elements.
   */
  public boolean isEmpty() {
    /*
        Returns true if this stack contains no elements.
    */
    if (this.size() == 0) {
      return true;
    }

    return false;
  }
}
