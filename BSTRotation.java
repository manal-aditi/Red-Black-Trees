public class BSTRotation<T extends Comparable<T>> extends BinarySearchTree<T> {


  /**
   * Performs the rotation operation on the provided nodes within this tree. When the provided child
   * is a left child of the provided parent, this method will perform a right rotation. When the
   * provided child is a right child of the provided parent, this method will perform a left
   * rotation. When the provided nodes are not related in one of these ways, this method will either
   * throw a NullPointerException: when either reference is null, or otherwise will throw an
   * IllegalArgumentException.
   *
   * @param child  is the node being rotated from child to parent position
   * @param parent is the node being rotated from parent to child position
   * @throws NullPointerException     when either passed argument is null
   * @throws IllegalArgumentException when the provided child and parent nodes are not initially
   *                                  (pre-rotation) related that way
   */
  protected void rotate(BSTNode<T> child, BSTNode<T> parent)
      throws NullPointerException, IllegalArgumentException {
    //If the parent node or the child node is null, then a NullPointerException is thrown
    if (parent == null || child == null) {
      throw new NullPointerException("The references are null.");
    }
    //If the parent node's left child is equivalent to the parameter child node, then the tree is
    //rotated to the right
    if (parent.getLeft() == child) {
      rotateRight(child, parent);
      //If the parent node's right child is equivalent to the parameter child node, then the tree is
      //rotated to the left
    } else if (parent.getRight() == child) {
      rotateLeft(child, parent);
    } else {
      //If the param nodes are unrelated as a parent child relationship then it triggers a
      //IllegalArgumentException
      throw new IllegalArgumentException("The nodes are unrelated.");
    }
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

  /** This test does both a right and left rotation on the root node
   *
   * @return True if the tests pass, false if not
   */
  /**
  public boolean test1() {
    BSTRotation<String> mainBST1 = new BSTRotation<>();

    BSTNode<String> node = new BSTNode<>("E");
    BSTNode<String> node1 = new BSTNode<>("B");
    BSTNode<String> node2 = new BSTNode<>("H");
    BSTNode<String> node3 = new BSTNode<>("A");
    BSTNode<String> node4 = new BSTNode<>("D");
    BSTNode<String> node5 = new BSTNode<>("F");
    BSTNode<String> node6 = new BSTNode<>("J");
    BSTNode<String> node7 = new BSTNode<>("C");
    BSTNode<String> node8 = new BSTNode<>("G");
    BSTNode<String> node9 = new BSTNode<>("I");
    BSTNode<String> node10 = new BSTNode<>("K");

    mainBST1.insertHelper(node1, node);
    mainBST1.insertHelper(node2, node);
    mainBST1.insertHelper(node3, node);
    mainBST1.insertHelper(node4, node);
    mainBST1.insertHelper(node5, node);
    mainBST1.insertHelper(node6, node);
    mainBST1.insertHelper(node7, node);
    mainBST1.insertHelper(node8, node);
    mainBST1.insertHelper(node9, node);
    mainBST1.insertHelper(node10, node);


    //Rotates the root node to the right
    rotate((BSTNode<T>) node1, (BSTNode<T>) node);
    String actualOrder1 = root.toLevelOrderString();

    if(!actualOrder1.equals("[ B, A, E, D, H, C, F, J, G, I, K ]")) {
      //Rotates and sets the binary tree back to order if the test fails
      rotate(root.getRight(), root);
      return false;
    } else {
      //Rotates binary tree back in order to perform a right rotation on the original tree
      rotate(root.getRight(), root);
    }
    //Rotates the tree to the left
    rotate(root.getRight(), root);
    if(!root.toLevelOrderString().equals("[ H, E, J, B, F, I, K, A, D, G, C ]")){
      //Rotates and sets the binary tree back to order if the test fails
      rotate(root.getLeft(), root);
      return false;
    }else{
      //Rotates tree back in order to perform the next tests
      rotate(root.getLeft(), root);
      return true;
    }
  }
 **/
  /** This method rotates the nodes that aren't exactly the root node in order to check different
   * parent-child pairs.
   *
   * @return True if the tests pass, false if not
   */
  /**
  public boolean test2() {
    BSTRotation<String> mainBST2 = new BSTRotation<>();

    BSTNode<String> node = new BSTNode<>("E");
    BSTNode<String> node1 = new BSTNode<>("B");
    BSTNode<String> node2 = new BSTNode<>("H");
    BSTNode<String> node3 = new BSTNode<>("A");
    BSTNode<String> node4 = new BSTNode<>("D");
    BSTNode<String> node5 = new BSTNode<>("F");
    BSTNode<String> node6 = new BSTNode<>("J");
    BSTNode<String> node7 = new BSTNode<>("C");
    BSTNode<String> node8 = new BSTNode<>("G");
    BSTNode<String> node9 = new BSTNode<>("I");
    BSTNode<String> node10 = new BSTNode<>("K");

    mainBST2.insertHelper(node1, node);
    mainBST2.insertHelper(node2, node);
    mainBST2.insertHelper(node3, node);
    mainBST2.insertHelper(node4, node);
    mainBST2.insertHelper(node5, node);
    mainBST2.insertHelper(node6, node);
    mainBST2.insertHelper(node7, node);
    mainBST2.insertHelper(node8, node);
    mainBST2.insertHelper(node9, node);
    mainBST2.insertHelper(node10, node);

    //Right rotation on root's right-right subtree
    rotate((BSTNode<T>) root.getRight().getRight(), (BSTNode<T>) root.getRight());
    String expectedResult = "[ E, B, J, A, D, H, K, C, F, I, G ]";
    if (!root.toLevelOrderString().equals(expectedResult)) {
      //Rotates and sets the binary tree back to order if the test fails
      rotate(root.getRight().getLeft(), root.getRight());
      return false;
    }
    rotate(root.getLeft(), root);
    expectedResult = "[ B, A, E, D, J, C, H, K, F, I, G ]";
    if (!root.toLevelOrderString().equals(expectedResult)) {
      //Rotates and sets the binary tree back to order if the test fails
      rotate(root.getRight(), root);
      rotate(root.getRight().getLeft(), root.getRight());
      return false;
    }
    //Rotates tree back in order to perform the next tests
    rotate(root.getRight(), root);
    rotate(root.getRight().getLeft(), root.getRight());
    return true;
  }
   */
  /** This test rotates different parent child pairs
   *
   * @return True if the tests pass, false if not
   */
  /**
   public boolean test3() {
    BSTRotation<String> mainBST2 = new BSTRotation<>();

    BSTNode<String> node = new BSTNode<>("E");
    BSTNode<String> node1 = new BSTNode<>("B");
    BSTNode<String> node2 = new BSTNode<>("H");
    BSTNode<String> node3 = new BSTNode<>("A");
    BSTNode<String> node4 = new BSTNode<>("D");
    BSTNode<String> node5 = new BSTNode<>("F");
    BSTNode<String> node6 = new BSTNode<>("J");
    BSTNode<String> node7 = new BSTNode<>("C");
    BSTNode<String> node8 = new BSTNode<>("G");
    BSTNode<String> node9 = new BSTNode<>("I");
    BSTNode<String> node10 = new BSTNode<>("K");

    mainBST2.insertHelper(node1, node);
    mainBST2.insertHelper(node2, node);
    mainBST2.insertHelper(node3, node);
    mainBST2.insertHelper(node4, node);
    mainBST2.insertHelper(node5, node);
    mainBST2.insertHelper(node6, node);
    mainBST2.insertHelper(node7, node);
    mainBST2.insertHelper(node8, node);
    mainBST2.insertHelper(node9, node);
    mainBST2.insertHelper(node10, node);

    //First the tree is rotated to the right preliminarily
    rotate((BSTNode<T>) root.getLeft(), (BSTNode<T>) root);
    String expectedString3 = "[ B, A, E, D, H, C, F, J, G, I, K ]";
    if (!root.toLevelOrderString().equals(expectedString3)) {
      //Rotates and sets the binary tree back to order if the test fails
      rotate(root.getRight(), root);
      return false;
    }
    //Left rotation on the child of the right-left root node
    rotate(root.getRight().getLeft(), root.getRight());
    String expectedString2 = "[ B, A, D, C, E, H, F, J, G, I, K ]";
    if (!root.toLevelOrderString().equals(expectedString2)) {
      //Returns false if it doesn't pass
      return false;
    }
    return true;
  }   */


  /**
   * Main method checks the test cases and prints the results
   *
   * @param args unused
   */
  public static void main (String[]args){
    BSTRotation<String> rotationTest = new BSTRotation<>();
    //System.out.println("Test 1: " + rotationTest.test1());
    //System.out.println("Test 2: " + rotationTest.test2());
    //System.out.println("Test 3: " + rotationTest.test3());

  }
}
