package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A graph that is composed of a set of verticies and edges.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Graph<T extends Comparable<T>> {

  private Set<T> verticies;
  private Set<Edge<T>> edges;
  private HashMap<T, LinkedList<Edge<T>>> verticesEdgesMap;

  /**
   * Creates a new graph.
   *
   * @param verticies The set of verticies in the graph.
   * @param edges The set of edges in the graph.
   */
  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    /*
       Contructor for the class
    */
    this.verticies = verticies;
    this.edges = edges;
    this.verticesEdgesMap = new HashMap<>();
    this.createVerticesEdgesMap();
  }

  /**
   * Creates a map of verticies to edges.
   *
   * <p>For example, if the graph has verticies [A, B, C, D] and edges [(A, B), (A, C), (B, D)],
   * then the map should be:
   *
   * <p>A -> [(A, B), (A, C)]
   */
  public void createVerticesEdgesMap() {
    /*
        Creates a HashMap of verticies to edges
    */
    for (T vertex : verticies) {
      LinkedList<Edge<T>> edgesLinkedList = new LinkedList<>();
      for (Edge<T> edge : this.edges) {
        if (edge.getSource().equals(vertex)) {
          if (edgesLinkedList.isEmpty()) {
            edgesLinkedList.add(edge);
            continue;
          }

          int i = 0;
          Node<Edge<T>> n = edgesLinkedList.head;

          while (n != null) {
            if (edge.getDestination().compareTo(n.getData().getDestination()) < 0) {
              edgesLinkedList.insert(i, edge);
              break;
            }
            if (n.getNext() == null) {
              edgesLinkedList.add(edge);
              break;
            }
            i++;
            n = n.getNext();
          }
        }
      }
      verticesEdgesMap.put(vertex, edgesLinkedList);
    }
  }

  /**
   * Returns the set of roots in the graph.
   *
   * <p>A root is a vertex that has no incoming edges. or if it has incoming edges, all of its
   * incoming edges are from vertices that are in the same equivalence class as it.
   *
   * @return roots
   */
  public Set<T> getRoots() {
    /*
        Returns the set of roots in the graph
    */
    Set<T> roots = new HashSet<T>();
    for (T vertex : verticies) {
      if (roots.contains(vertex)) {
        continue;
      }

      if (this.getOutDegree(vertex) > 0 && this.getInDegree(vertex) <= 0) {
        roots.add(vertex);
      } else {
        if (this.getEquivalenceClass(vertex).size() > 0) {
          roots.add(this.getMin(getEquivalenceClass(vertex)));
        }
      }
      // use linked hashset to maintain sorted order
    }
    LinkedHashSet<T> sortedRoots = this.sortArray(roots);
    return sortedRoots;
  }

  /**
   * Sorts a set of verticies by converting the set to an array, sorting the array, and then
   * converting the array back to a set.
   *
   * @param roots the set of verticies that we want to sort
   * @return sortedRoots
   */
  public LinkedHashSet<T> sortArray(Set<T> roots) {
    /*
        Sorts a set of verticies by converting the set to an
        array, sorting the array, and then converting the array back to a set
    */
    LinkedHashSet<T> sortedRoots = new LinkedHashSet<T>();
    ArrayList<T> rootsArray = new ArrayList<T>();

    for (T vertex : roots) {
      rootsArray.add(vertex);
    }

    Collections.sort(rootsArray);

    for (T vertex : rootsArray) {
      sortedRoots.add(vertex);
    }

    return sortedRoots;
  }

  /**
   * Returns the minimum vertex in an equivalence class using the compareTo method and a for loop.
   *
   * @param equivalenceClass the equivalence class that we want to find the minimum value of
   * @return min
   */
  private T getMin(Set<T> equivalenceClass) {
    T min = equivalenceClass.stream().findFirst().get();

    for (T vertex : equivalenceClass) {
      if (vertex.compareTo(min) < 0) {
        min = vertex;
      }
    }

    return min;
  }

  /**
   * Returns the in degree of a vertex by iterating through the edges and counting the number of
   * edges that have the vertex as their destination.
   *
   * <p>The in degree of a vertex is the number of edges that have the vertex as their destination.
   *
   * @param vertex the vertex that we want to find the in degree of
   * @return inDegree
   */
  private int getInDegree(T vertex) {
    int inDegree = 0;

    for (Edge<T> edge : edges) {
      if (edge.getDestination().equals(vertex)) {
        inDegree++;
      }
    }

    return inDegree;
  }

  /**
   * Returns the out degree of a vertex by iterating through the edges and counting the number of
   * edges that have the vertex as their source.
   *
   * <p>The out degree of a vertex is the number of edges that have the vertex as their source.
   *
   * @param vertex the vertex that we want to find the out degree of
   * @return outDegree
   */
  private int getOutDegree(T vertex) {
    int outDegree = verticesEdgesMap.get(vertex).size();
    return outDegree;
  }

  /**
   * Checks if the graph is reflexive by iterating through the edges and checking if every vertex
   * has an edge to itself.
   *
   * <p>A graph is reflexive if every vertex has an edge to itself.
   *
   * @return true if the graph is reflexive, false otherwise.
   */
  public boolean isReflexive() {
    /*
        Checks if the graph is reflexive by iterating through the edges and checking if every
        vertex has an edge to itself.
    */

    Boolean doesContain;
    for (Entry<T, LinkedList<Edge<T>>> set : verticesEdgesMap.entrySet()) {
      if (set.getValue().isEmpty()) {
        return false;
      }

      Node<Edge<T>> nodeEdge = set.getValue().head;
      doesContain = false;

      while (nodeEdge != null) {
        if (set.getKey().equals(nodeEdge.getData().getDestination())) {
          doesContain = true;
          break;
        }
        nodeEdge = nodeEdge.getNext();
      }
      if (!doesContain) {
        return false;
      }
    }

    return true;
  }

  /**
   * Checks if the graph is symmetric by iterating through the edges and checking if for every edge
   * (A, B), there is also an edge (B, A).
   *
   * <p>A graph is symmetric if for every edge (A, B), there is also an edge (B, A).
   *
   * @return true if the graph is symmetric, false otherwise.
   */
  public boolean isSymmetric() {
    /*
        Checks if the graph is symmetric by iterating through the edges and
        checking if for every edge (A, B), there is also an edge (B, A).
    */
    for (Edge<T> edge : edges) {
      T destination = edge.getDestination();
      LinkedList<Edge<T>> edgesLinkedList = verticesEdgesMap.get(destination);
      Node<Edge<T>> nodeEdge = edgesLinkedList.head;
      Boolean doesContain = false;

      while (nodeEdge != null) {
        if (nodeEdge.getData().getDestination().equals(edge.getSource())) {
          doesContain = true;
          break;
        }
        nodeEdge = nodeEdge.getNext();
      }
      if (!doesContain) {
        return false;
      }
    }

    return true;
  }

  /**
   * Checks if the graph is transitive by iterating through the edges and checking if for every edge
   * (A, B) and (B, C), there is also an edge (A, C).
   *
   * <p>A graph is transitive if for every edge (A, B) and (B, C), there is also an edge (A, C).
   *
   * @return true if the graph is transitive, false otherwise.
   */
  public boolean isTransitive() {
    /*
        Checks if the graph is transitive by iterating through the edges and
        checking if for every edge (A, B) and (B, C), there is also an edge (A, C).
    */
    for (Edge<T> edge : edges) {
      T destination = edge.getDestination();
      LinkedList<Edge<T>> edgesLinkedList = verticesEdgesMap.get(destination);
      Node<Edge<T>> nodeEdge = edgesLinkedList.head;

      while (nodeEdge != null) {
        LinkedList<Edge<T>> edgesLinkedList2 = verticesEdgesMap.get(edge.getSource());
        Node<Edge<T>> nodeEdge2 = edgesLinkedList2.head;
        Boolean doesContain = false;

        while (nodeEdge2 != null) {
          if (nodeEdge2.getData().getDestination().equals(edge.getSource())) {
            doesContain = true;
            break;
          }
          nodeEdge2 = nodeEdge2.getNext();
        }
        if (!doesContain) {
          return false;
        }
        nodeEdge = nodeEdge.getNext();
      }
    }
    return true;
  }

  /**
   * Checks if the graph is anti-symmetric by iterating through the edges and checking if for every
   * edge (A, B), if there is also an edge (B, A), then A = B.
   *
   * <p>A graph is anti-symmetric if for every edge (A, B), if there is also an edge (B, A), then A
   * = B.
   *
   * @return true if the graph is anti-symmetric, false otherwise.
   */
  public boolean isAntiSymmetric() {
    /*
        Checks if the graph is anti-symmetric by iterating through the edges and
        checking if for every edge (A, B), if there is also an edge (B, A), then A = B.
    */
    for (Edge<T> edge : edges) {
      T destination = edge.getDestination();
      LinkedList<Edge<T>> edgesLinkedList = verticesEdgesMap.get(destination);
      Node<Edge<T>> nodeEdge = edgesLinkedList.head;

      while (nodeEdge != null) {
        if (nodeEdge.getData().getDestination().equals(edge.getSource())) {
          if (!nodeEdge.getData().getSource().equals(nodeEdge.getData().getDestination())) {
            return false;
          }
        }
        nodeEdge = nodeEdge.getNext();
      }
    }
    return true;
  }

  /**
   * Checks if the graph is an equivalence relation by checking if it is reflexive, symmetric and
   * transitive (using the methods above).
   *
   * <p>A graph is an equivalence relation if it is reflexive, symmetric and transitive.
   *
   * @return true if the graph is an equivalence relation, false otherwise.
   */
  public boolean isEquivalence() {
    /*
        Checks if the graph is an equivalence relation by checking if it is reflexive, symmetric
        and transitive (using the methods above).
    */
    if (isReflexive() && isSymmetric() && isTransitive()) {
      return true;
    }
    return false;
  }

  /**
   * Gets the equivalence class of a vertex by iterating through the edges and adding the
   * destination of each edge to a set. If the graph is not an equivalence relation or the vertex
   * has no edges, then the equivalence class is empty.
   *
   * <p>The equivalence class of a vertex is the set of all verticies that are equivalent to it.
   *
   * @param vertex the vertex that we want to find the equivalence class of
   * @return equivalenceClass
   */
  public Set<T> getEquivalenceClass(T vertex) {
    /*
        Gets the equivalence class of a vertex by iterating through the edges and adding the
        destination of each edge to a set. If the graph is not an equivalence relation or the
        vertex has no edges, then the equivalence class is empty.
    */

    Set<T> equivalenceClass = new HashSet<T>();
    Node<Edge<T>> nodeEdge = verticesEdgesMap.get(vertex).head;
    if (!isEquivalence() || verticesEdgesMap.get(vertex).isEmpty()) {
      return equivalenceClass;
    }

    while (nodeEdge != null) {
      equivalenceClass.add(nodeEdge.getData().getDestination());
      nodeEdge = nodeEdge.getNext();
    }

    return equivalenceClass;
  }

  /**
   * Performs a breadth first search on the graph using an iterative approach by iterating through
   * the roots and adding them to a queue. Then, while the queue is not empty, dequeue the first
   * element, add it to the bfs list, and add all of its children to the queue. If the queue is
   * empty, then return the bfs list.
   *
   * @return bfs
   */
  public List<T> iterativeBreadthFirstSearch() {
    /*
        Performs the breadth first search of the graph. Using an iterative approach.
    */
    Set<T> roots = this.getRoots();
    List<T> visited = new ArrayList<T>();
    Queue<T> queue = new Queue<T>();
    List<T> bfs = new ArrayList<T>();

    for (T root : roots) {
      queue.enqueue(root);
      visited.add(root);
      while (!queue.isEmpty()) {
        this.getQueue(queue, bfs, visited);
      }
    }

    return bfs;
  }

  /**
   * Iterated through each vertexese neighbouring vertices and adds them to the queue if they have
   * not been visited yet.
   *
   * @param queue the queue to add the vertices to
   * @param bfs the list of vertices in the bfs
   * @param visited the list of vertices that have been visited
   */
  public void getQueue(Queue<T> queue, List<T> bfs, List<T> visited) {
    /*
        Iterated through each vertexese neighbouring vertices and adds them to the queue if they
        have not been visited yet.
    */
    T vertex = (T) queue.dequeue();
    bfs.add(vertex);
    LinkedList<Edge<T>> edgesLinkedList = verticesEdgesMap.get(vertex);
    Node<Edge<T>> nodeEdge = edgesLinkedList.head;

    while (nodeEdge != null) {
      if (!visited.contains(nodeEdge.getData().getDestination())) {
        queue.enqueue(nodeEdge.getData().getDestination());
        visited.add(nodeEdge.getData().getDestination());
      }
      nodeEdge = nodeEdge.getNext();
    }
  }

  /**
   * Performs a depth first search on the graph using an iterative approach by iterating through the
   * roots and adding them to a stack. Then, while the stack is not empty, pop the first element,
   * add it to the dfs list, and add all of its children to the stack. If the stack is empty, then
   * return the dfs list.
   *
   * @return dfs
   */
  public List<T> iterativeDepthFirstSearch() {
    /*
        Performs the depth first search of the graph. Using an iterative approach.
    */
    Set<T> roots = this.getRoots();
    List<T> visited = new ArrayList<T>();
    Stack<T> stack = new Stack<T>();
    List<T> dfs = new ArrayList<T>();

    for (T root : roots) {

      stack.push(root);
      visited.add(root);
      while (!stack.isEmpty()) {
        T vertex = (T) stack.pop();
        dfs.add(vertex);
        LinkedList<Edge<T>> edgesLinkedList = verticesEdgesMap.get(vertex);
        Node<Edge<T>> nodeEdge = edgesLinkedList.tail;

        while (nodeEdge != null) {
          if (!visited.contains(nodeEdge.getData().getDestination())) {
            stack.push(nodeEdge.getData().getDestination());
            visited.add(nodeEdge.getData().getDestination());
          }
          nodeEdge = nodeEdge.getPrev();
        }
      }
    }

    return dfs;
  }

  /**
   * Performs a breadth first search on the graph using a recursive approach by iterating through
   * the roots and adding them to a queue. Then calling the recursive function getRecursiveBfs to
   * perform the actual recursive breadth first search.
   *
   * @return bfs
   */
  public List<T> recursiveBreadthFirstSearch() {
    /*
        Performs the breadth first search of the graph. Using a recursive approach.
    */
    Set<T> roots = this.getRoots();
    List<T> visited = new ArrayList<T>();
    Queue<T> queue = new Queue<T>();
    List<T> bfs = new ArrayList<T>();

    for (T root : roots) {
      queue.enqueue(root);
      visited.add(root);
      for (T x : this.getRecursiveBfs(bfs, visited, queue)) {
        if (!bfs.contains(x)) {
          bfs.add(x);
        }
      }
    }

    return bfs;
  }

  /**
   * The actual function that is calle recursively to perform a breadth first search by iterating
   * through the queue and adding the first element to the bfs list. Then, while the queue is not
   * empty, dequeue the first element, add it to the bfs list, and add all of its children to the
   * queue. If the queue is empty, then return the bfs list.
   *
   * @return bfs
   */
  public List<T> getRecursiveBfs(List<T> bfs, List<T> visited, Queue<T> queue) {
    /*
        Performs the recursive part of the recursice search of the graph.
    */

    while (!queue.isEmpty()) {
      this.getQueue(queue, bfs, visited);
      for (T x : this.getRecursiveBfs(bfs, visited, queue)) {
        if (!bfs.contains(x)) {
          bfs.add(x);
        }
      }
    }
    return bfs;
  }

  /**
   * Performs a depth first search on the graph using a recursive approach by iterating through the
   * roots and adding them to a stack. Then calling the recursive function getRecursiveDfs to
   * perform the actual recursive depth first search.
   *
   * @return dfs
   */
  public List<T> recursiveDepthFirstSearch() {
    /*
        Performs the depth first search of the graph. Using a recursive approach.
    */
    Set<T> roots = this.getRoots();
    List<T> visited = new ArrayList<T>();
    Stack<T> stack = new Stack<T>();
    List<T> dfs = new ArrayList<T>();

    for (T root : roots) {

      stack.push(root);
      visited.add(root);
      for (T x : this.getRecursiveDfs(stack, visited, dfs)) {
        if (!dfs.contains(x)) {
          dfs.add(x);
        }
      }
    }

    return dfs;
  }

  /**
   * The actual function that is calle recursively to perform a depth first search by iterating
   * through the stack and adding the first element to the dfs list. Then, while the stack is not
   * empty, pop the first element, add it to the dfs list, and add all of its children to the stack.
   * If the stack is empty, then return the dfs list.
   *
   * @return dfs
   */
  public List<T> getRecursiveDfs(Stack<T> stack, List<T> visited, List<T> dfs) {
    /*
        Performs the recursive part of the recursive search of the graph.
    */
    while (!stack.isEmpty()) {
      T vertex = (T) stack.pop();
      dfs.add(vertex);
      LinkedList<Edge<T>> edgesLinkedList = verticesEdgesMap.get(vertex);
      Node<Edge<T>> nodeEdge = edgesLinkedList.head;

      while (nodeEdge != null) {
        if (!visited.contains(nodeEdge.getData().getDestination())) {
          stack.push(nodeEdge.getData().getDestination());
          visited.add(nodeEdge.getData().getDestination());
          for (T x : this.getRecursiveDfs(stack, visited, dfs)) {
            if (!dfs.contains(x)) {
              dfs.add(x);
            }
          }
        }
        nodeEdge = nodeEdge.getNext();
      }
    }
    return dfs;
  }
}
