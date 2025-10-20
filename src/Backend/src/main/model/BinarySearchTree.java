package Backend.src.main.model;
import java.lang.Comparable;

public class BinarySearchTree<T extends java.lang.Comparable<T>> implements SortedCollection<T> {
  //Reference to the root node of the binary search tree
  protected BSTNode<T> root;

  /**
   * Performs the naive binary search tree insert algorithm to recursively insert the provided
   * newNode (which has already been initialized with a data value) into the provided tree/subtree.
   * When the provided subtree is null, this method does nothing.
   */
  protected void insertHelper(BSTNode<T> newNode, BSTNode<T> subtree) {
    //If the subtree is null, the node in the parameter becomes the root node
    if (subtree == null) {
      root = newNode;
      subtree.setData(newNode.getData());
    }
    // Case regarding when the value of the data in the node parameter is less than the value of the
    // subtree data
    if (newNode.getData().compareTo(subtree.getData()) < 0) {
      //If the subtree has a null value for its left child, it's replaced with the newNode
      if (subtree.getLeft() == null) {
        //The newNode is set as the left child and the subtree is set as the parent node
        subtree.setLeft(newNode);
        newNode.setUp(subtree);
      } else {
        // If there is a left child value, it recursively goes through the program to be placed
        // again on the left
        insertHelper(newNode, subtree.getLeft());
      }
      // Case for if the newNode and the subtree value are the same
    } else if (newNode.getData().compareTo(subtree.getData()) == 0) {
      //The node is placed to the left, if there is no child node to the left
      if (subtree.getLeft() == null) {
        //The newNode is set as the left child and the subtree is set as the parent node
        subtree.setLeft(newNode);
        newNode.setUp(subtree);
      } else {
        // Otherwise, the value recursively goes through the left side to be placed again
        insertHelper(newNode, subtree.getLeft());
      }
    } else {
      // Case for if the newNode is greater than the subtree value
      if (subtree.getRight() == null) {
        //The newNode is set as the right child and the subtree is set as the parent node
        subtree.setRight(newNode);
        newNode.setUp(subtree);
      } else {
        // Otherwise, the value recursively goes through the right side to be placed again
        insertHelper(newNode, subtree.getRight());
      }
    }
  }

  /**
   * Inserts a new data value into the sorted collection.
   *
   * @param data the new value being inserted
   * @throws NullPointerException if data argument is null, we do not allow null values to be stored
   *                              within a SortedCollection
   */
  @Override
  public void insert(T data) throws NullPointerException {
    // If there is no data, then a NullPointerException is thrown
    if (data == null) {
      throw new NullPointerException("Bad data");
    }
    // Create a new Node given the data in the parameter
    BSTNode<T> newNode = new BSTNode<>(data);
    // If the root is null, then set the newNode to be the root value of the BST
    if (root == null) {
      root = newNode;
    }
      // Else, invoke the helper method to place the newNode properly into the BST
      insertHelper(newNode, root);
  }

  /**
   * Check whether data is stored in the tree.
   *
   * @param data the value to check for in the collection
   * @return true if the collection contains data one or more times, and false otherwise
   */
  @Override
  public boolean contains(Comparable<T> data) {
    // Sets the value to the node to root
    BSTNode<T> finder = root;
    // Invokes the helper method given the subtree node and the data to be compared to
    return containsHelper(finder, data);
  }

  /**
   * Helper method for finding if the BST contains the data of a particular node
   *
   * @param subtree - Subtree that is being checked for the given data
   * @param data    - The data that we want to find within the BST
   * @return True if the data matches a node in the BST, false if not
   */
  private boolean containsHelper(BSTNode<T> subtree, Comparable<T> data) {
    // If the subtree is null and has no values there can be no match
    if (subtree == null) {
      return false;
    }
    //If there is a direct match to the subtree, then it returns true
    if ((data.compareTo(subtree.getData())) == 0) {
      return true;
      //If the data value is less than the subtree value, then it recursively checks the left side
      // of the BST
    } else if ((data.compareTo(subtree.getData())) < 0) {
      return containsHelper(subtree.getLeft(), data);
    } else {
      //Otherwise, it checks the right side of the BST if the data value is greater than the subtree
      // value
      return containsHelper(subtree.getRight(), data);
    }
  }

  /**
   * Counts the number of values in the collection, with each duplicate value being counted
   * separately within the value returned.
   *
   * @return the number of values in the collection, including duplicates
   */
  @Override
  public int size() {
    //Invokes the findSize helper method
    return findSize(root);
  }

  /**
   * Helper method that recursively goes through the BST in order to find the total number of nodes
   *
   * @param subtree - The node that is the parent node that contains child nodes to be counted
   * @return The size of the entire BSt
   */
  private int findSize(BSTNode<T> subtree) {
    // The variables below check for the size of the left side nodes, right side nodes, and the
    // parent node value
    int size = 0;
    int leftSide = 0;
    int rightSide = 0;
    //If there are no nodes in the subtree, then it returns 0 for no nodes
    if (subtree == null) {
      return size;
    }
    //This increments for the value of the root node
    size++;
    // The rightSide variable gets incremented by the values of the nodes on the right side of the
    //BST
    rightSide = findSize(subtree.getRight());
    // The leftSide variable gets incremented by the values of the nodes on the left side of the
    //BST
    leftSide = findSize(subtree.getLeft());
    //Adding the root node, left side nodes and right side nodes gives the side of the total tree
    return size + leftSide + rightSide;
  }

  /**
   * Checks if the collection is empty.
   *
   * @return true if the collection contains 0 values, false otherwise
   */
  @Override
  public boolean isEmpty() {
    //If the size of the tree is 0, found by the size() method, then the tree is empty
    if (size() == 0) {
      return true;
    }
    return false;
  }

  /**
   * Removes all values and duplicates from the collection.
   */
  @Override
  public void clear() {
    // Uses helper method to clear the entire BST
    clearingHelp(root);
  }

  /**
   * Helper method to clear out the BST via recursion
   *
   * @param subtree - the node taken in as a parameter to be the subtree that becomes cleared out
   */
  private void clearingHelp(BSTNode<T> subtree) {
    // First the root is set to null to make the tree node clear
    root = null;
    // If the subtree is already null then the method ends
    if (subtree == null) {
      return;
    } else {
      // This recursively works to set each node to null by going throughout the tree to correct it
      clearingHelp(subtree.getLeft());
      clearingHelp(subtree.getRight());
      subtree = null;
    }
  }

  /**
   * This first test checks the BST insertion process to ensure that the nodes are placed in order
   * and in level order as well as ensuring the insertion method works correctly.
   *
   * @return True if the test cases pass, false if not
   */
  public boolean test1() {
    // Create a new BST with Integer nodes
    BinarySearchTree<Integer> testTree = new BinarySearchTree<>();
    // Data insertion
    testTree.insert(21);
    testTree.insert(14);
    testTree.insert(28);
    testTree.insert(18);
    testTree.insert(11);
    testTree.insert(32);
    testTree.insert(25);

    //The two strings contain the expected number representations of the BST in both the order of
    // BST from least to greatest as well as the levels of the BST from left to right
    String expectedInOrder = "[ 11, 14, 18, 21, 25, 28, 32 ]";
    String expectedLevelOrder = "[ 21, 14, 28, 11, 18, 25, 32 ]";

    // These are the actual answers given by the methods
    String actualInOrder = testTree.root.toInOrderString();
    String actualLevelOrder = testTree.root.toLevelOrderString();

    // The boolean values check if the actual Strings are equal to the expected Strings
    boolean inOrderCheck = actualInOrder.equals(expectedInOrder);
    boolean levelOrderCheck = actualLevelOrder.equals(expectedLevelOrder);

    return inOrderCheck && levelOrderCheck;
  }

  /**
   * Uses a String BST to check to see the contains method, clear method, and size method to ensure
   * both are working correctly.
   *
   * @return True if the test cases pass, false if not
   */
  public boolean test2() {
    // Create a new BST with String nodes
    BinarySearchTree<String> testTree = new BinarySearchTree<>();
    //Data insertion
    testTree.insert("A Thousand Splendid Suns");
    testTree.insert("Babel");
    testTree.insert("No Country for Old Men");
    testTree.insert("The Picture of Dorian Gray");
    testTree.insert("American Psycho");
    testTree.insert("Where the Wild Things Are");
    testTree.insert("Pride and Prejudice");


    //Creates new boolean variables to see if the tree contains the book titles listed above
    boolean checkContain = testTree.contains("A Thousand Splendid Suns") && testTree.contains(
        "Babel") && testTree.contains("No Country for Old Men") && testTree.contains(
        "Pride and Prejudice") && testTree.contains("The Picture of Dorian Gray");
    //Checks to see if the size of the tree is correct with five nodes in the BST
    boolean checkSize1 = (testTree.size() == 7);
    //Expected order of the book list
    String expectedLevelOrder =
        "[ A Thousand Splendid Suns, Babel, American Psycho, No Country for Old Men, The Picture of"
            + " Dorian Gray, Pride and Prejudice, Where the Wild Things Are ]";
    String expectedInOrder =
        "[ A Thousand Splendid Suns, American Psycho, Babel, No Country for Old Men, Pride and " +
            "Prejudice, The Picture of Dorian Gray, Where the Wild Things Are ]";
    // These are the actual book orders given by the methods
    String actualInOrder = testTree.root.toInOrderString();
    String actualLevelOrder = testTree.root.toLevelOrderString();

    // The boolean values check if the actual Strings are equal to the expected Strings
    boolean inOrderCheck = actualInOrder.equals(expectedInOrder);
    boolean levelOrderCheck = actualLevelOrder.equals(expectedLevelOrder);
    //Clears tree
    testTree.clear();
    //Checks to see if the tree size is back to 0
    boolean checkSize2 = (testTree.size() == 0);

    return checkSize1 && checkContain && checkSize2 && inOrderCheck && levelOrderCheck;
  }

  /**
   * Creates an Integer BST to check to see if the clear method and the size method work
   *
   * @return True if the test cases pass, false if not
   */
  public boolean test3() {
    // Creates a new BST with Integer nodes
    BinarySearchTree<Integer> testTree = new BinarySearchTree<>();
    // Data insertion
    testTree.insert(6);
    testTree.insert(10);
    testTree.insert(49);
    testTree.insert(16);
    testTree.insert(18);
    //Checks to see if the size of the BST is 5 after inserting 5 nodes
    boolean testSize1 = (testTree.size() == 5);
    //Uses the clear method
    testTree.clear();
    //Checks to see if the clear method left the BST empty
    boolean testEmpty = testTree.isEmpty();
    //Checks to see if the size is now 0
    boolean testSize2 = (testTree.size() == 0);

    return testEmpty && testSize1 && testSize2;
  }

  /**
   * Main method checks the test cases and prints the results
   *
   * @param args unused
   */
  public static void main(String[] args) {
    BinarySearchTree<Integer> mainBST = new BinarySearchTree<>();
    System.out.println("First Test: " + mainBST.test1());
    System.out.println("Second Test: " + mainBST.test2());
    System.out.println("Third Test: " + mainBST.test3());
  }

}
