package nz.ac.auckland.se281.datastructures;

/**
 * A node class for a doubly linked list.
 *
 * @param <T> The type of the data stored in the node.
 */
public class Node<T> {
  private T data;
  private Node<T> next;
  private Node<T> prev;

  /**
   * Creates a new node with the specified data.
   *
   * @param data The data to store in the node.
   */
  public Node(T data) {
    /*
       Constructor for the class
    */
    this.data = data;
    this.next = null;
    this.prev = null;
  }

  /**
   * Gets the data stored in the node.
   *
   * @return The data stored in the node.
   */
  public T getData() {
    /*
        Gets the data stored in the node.
    */
    return this.data;
  }

  /**
   * Sets the data stored in the node.
   *
   * @param data The data to store in the node.
   */
  public void setData(T data) {
    /*
        Sets the data stored in the node.
    */
    this.data = data;
  }

  /**
   * Gets the next node in the list.
   *
   * @return The next node in the list.
   */
  public Node<T> getNext() {
    /*
        Gets the next node in the list.
    */
    return next;
  }

  /**
   * Sets the next node in the list.
   *
   * @param next The next node in the list.
   */
  public void setNext(Node<T> next) {
    /*
        Sets the next node in the list.
    */
    this.next = next;
  }

  /**
   * Gets the previous node in the list.
   *
   * @return The previous node in the list.
   */
  public Node<T> getPrev() {
    /*
        Gets the previous node in the list.
    */
    return prev;
  }

  /**
   * Sets the previous node in the list.
   *
   * @param prev The previous node in the list.
   */
  public void setPrev(Node<T> prev) {
    /*
        Sets the previous node in the list.
    */
    this.prev = prev;
  }

  /**
   * Returns a string representation of the node.
   *
   * @return A string representation of the node.
   */
  @Override
  public String toString() {
    /*
        Returns a string representation of the node.
    */
    return data.toString();
  }
}
