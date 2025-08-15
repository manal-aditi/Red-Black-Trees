import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Class handles Red Black trees.
 *
 */
public class RedBlackTree<T extends Comparable<T>> extends BSTRotation<T> {
  @Override
  public void insert(T data) {
    if (data == null) {
      throw new IllegalArgumentException("Null values can't be inserted.");
    }

    RBTNode<T> newNode = new RBTNode<>(data);
    if (root == null) {
      root = newNode;
      if (newNode.isRed()) {
        newNode.flipColor();  // Root must be black
      }
      return;
    }

    // Insert the node using the standard BST logic
    super.insertHelper(newNode, root);

    // Check and restore Red-Black Tree properties
    ensureRedProperty(newNode);

    // Ensure root is always black
    if (((RBTNode<T>) root).isRed()) {
      ((RBTNode<T>) root).flipColor();
    }
  }

  /**
   * Checks if a new red node in the RedBlackTree causes a red property violation
   * by having a red parent. If this is not the case, the method terminates without
   * making any changes to the tree. If a red property violation is detected, then
   * the method repairs this violation and any additional red property violations
   * that are generated as a result of the applied repair operation.
   * @param newRedNode a newly inserted red node, or a node turned red by previous repair
   */
  protected void ensureRedProperty(RBTNode<T> newRedNode) {
    while (newRedNode != root && newRedNode.getUp().isRed()) {
      RBTNode<T> parent = newRedNode.getUp();
      RBTNode<T> grandparent = parent.getUp();
      RBTNode<T> uncle;

      if (parent == grandparent.getLeft()) {
        uncle = grandparent.getRight();

        // case 1: red uncle
        if (uncle != null && uncle.isRed()) {
          parent.flipColor();
          uncle.flipColor();
          grandparent.flipColor();
          newRedNode = grandparent;
        } else {
          // case 2: parent is left child and new node is right
          if (newRedNode == parent.getRight()) {
            rotateLeft(newRedNode, parent);
            newRedNode = parent;
            parent = newRedNode.getUp();
          }

          // case 3: parent is left child and newRedNode is left
          rotateRight(parent, grandparent);
          parent.flipColor();
          grandparent.flipColor();
        }
      } else {
        //uncle is left child of grandparent
        uncle = grandparent.getLeft();

        // case 1: red uncle
        if (uncle != null && uncle.isRed()) {
          parent.flipColor();
          uncle.flipColor();
          grandparent.flipColor();
          newRedNode = grandparent;
        } else {
          // case 2: right parent child and new child is left
          if (newRedNode == parent.getLeft()) {
            rotateRight(newRedNode, parent);
            newRedNode = parent;
            parent = newRedNode.getUp();
          }

          // case 3: right child parent and new child is right
          rotateLeft(parent, grandparent);
          parent.flipColor();
          grandparent.flipColor();
        }
      }
    }
  }

  /**
   * This method rotates the binary tree on the left
   *
   * @param child - This intakes the child node for the rotation
   * @param parent - This takes the parent node for the rotation
   */
  private void rotateLeft(BSTNode<T> child, BSTNode<T> parent) {
    //This takes in the grandparent value of the previous parent node
    BSTNode<T> grandparent = parent.getUp();
    // The parent and grandparent nodes are updated depending on the vacancies in the tree
    if (grandparent == null) {
      root = child;
    } else if (grandparent.getLeft() != parent) {
      grandparent.setRight(child);
    } else {
      grandparent.setLeft(child);
    }
    //This sets the grandparent value to be above the child
    child.setUp(grandparent);

    // If the child is a parent duplicate, then it needs to be placed to the left side
    /** if (child.getData().compareTo(parent.getData()) == 0) {
     parent.setRight(child.getLeft());
     if (child.getLeft() != null) {
     child.getLeft().setUp(parent);
     } */
    // If there's no duplicates, then the rest of the nodes rotate leftwards
    parent.setRight(child.getLeft());
    if (child.getLeft() != null) {
      child.getLeft().setUp(parent);
    }


    // The parent node needs to be placed below the child
    child.setLeft(parent);
    parent.setUp(child);
  }


  /**
   * This method rotates the binary tree on the right
   *
   * @param child - This intakes the child node for the rotation
   * @param parent - This takes the parent node for the rotation
   */
  private void rotateRight(BSTNode<T> child, BSTNode<T> parent) {
    //This takes in the grandparent value of the previous parent node
    BSTNode<T> newTree = parent.getUp();
    // The parent and grandparent nodes are updated depending on the vacancies in the tree
    if (newTree == null) {
      root = child; }
    else if (newTree.getLeft() != parent) {
      newTree.setRight(child);
    } else {
      newTree.setLeft(child);
    }
    //This sets the grandparent value to be above the child
    child.setUp(newTree);

    // If the child is a parent duplicate, then it needs to be placed to the left side
    /** if (child.getData().compareTo(parent.getData()) == 0) {
     parent.setLeft(child.getRight());
     if (child.getRight() != null) {
     child.getRight().setUp(parent);
     } **/
    // If there's no duplicates, then the rest of the nodes rotate rightwards
    parent.setLeft(child.getRight());
    if (child.getRight() != null) {
      child.getRight().setUp(parent);

    }
    // The parent node needs to be placed below the child
    child.setRight(parent);
    parent.setUp(child);
  }


  /**
   * This method was created to set the nodes to the correct colors for checking insertion
   *
   * @param data - data to check
   */
  private RBTNode<T> search(T data) {
    return searchHelper((RBTNode<T>) root, data);
  }

  /**
   * Helper method to the search method
   *
   * @param node - Find the given node
   * @param data - data to be found
   */
  private RBTNode<T> searchHelper(RBTNode<T> node, T data) {
    if (node == null || node.getData().equals(data)) {
      return node;
    }
    //Uses compareTo method to find the correct data recursively
    if (data.toString().compareTo(node.getData().toString()) < 0) {
      return searchHelper(node.getLeft(), data);
    } else {
      return searchHelper(node.getRight(), data);
    }
  }

  /**
   * /** Test method to check for strings. Losely based off question 3.
   */
  @Test
  public void testOne() {
    RedBlackTree<String> testTree = new RedBlackTree<>();
    //Insert nodes
    testTree.insert("J");
    testTree.insert("E");
    testTree.insert("S");
    testTree.insert("C");
    testTree.insert("H");
    testTree.insert("N");

    //Assigns certain nodes the colors that I want to test
    RBTNode<String> nodeE = testTree.search("E");
    if (nodeE != null && !(nodeE.isRed())) {
      nodeE.flipColor();
    }
    RBTNode<String> nodeC = testTree.search("C");
    if (nodeC != null && nodeC.isRed()) {
      nodeC.flipColor();
    }

    RBTNode<String> nodeH = testTree.search("H");
    if (nodeH != null && nodeH.isRed()) {
      nodeH.flipColor();
    }
    System.out.println(testTree.root.toLevelOrderString());
    //Insert a final node as a test
    testTree.insert("R");
    String expected = "[ J(b), E(r), R(b), C(b), H(b), N(r), S(r) ]";
    assertEquals(expected, testTree.root.toLevelOrderString());
  }

  @Test
  public void testTwo() {
    RedBlackTree<Integer> testTree = new RedBlackTree<>();
    //Insert nodes
    testTree.insert(20);
    testTree.insert(18);
    testTree.insert(5);
    testTree.insert(15);
    testTree.insert(17);
    //Comparison of the nodes in order and in color
    String expected = "[ 18(b), 15(b), 20(b), 5(r), 17(r) ]";
    assertEquals(expected, testTree.root.toLevelOrderString());
  }

  @Test
  public void testThree() {
    RedBlackTree<Integer> testTree = new RedBlackTree<>();
    //Insert nodes
    testTree.insert(10);
    testTree.insert(5);
    testTree.insert(30);
    testTree.insert(2);
    testTree.insert(9);
    testTree.insert(50);
    testTree.insert(45);

    //Comparison of the nodes in order and color
    String expected = "[ 10(b), 5(b), 45(b), 2(r), 9(r), 30(r), 50(r) ]";
    assertEquals(expected, testTree.root.toLevelOrderString());
  }
}
