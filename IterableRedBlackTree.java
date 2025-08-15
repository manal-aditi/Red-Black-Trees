import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class extends RedBlackTree into a tree that supports iterating over the values it
 * stores in sorted, ascending order.
 */
public class IterableRedBlackTree<T extends Comparable<T>> extends RedBlackTree<T> implements IterableSortedCollection<T> {
  //class variable that holds the min value, and null if there's no min value
  private T min = null;
  //class variable that holds the max value, and null if there's no max value
  private T max = null;

  /**
   * Allows setting the start (minimum) value of the iterator. When this method is called, every
   * iterator created after it will use the minimum set by this method until this method is called
   * again to set a new minimum value.
   *
   * @param min the minimum for iterators created for this tree, or null for no minimum
   */
  @Override
  public void setIteratorMin(Comparable min) {
    //Setter method for min value
    this.min = (T) min;
  }

  /**
   * Allows setting the stop (maximum) value of the iterator. When this method is called, every
   * iterator created after it will use the maximum set by this method until this method is called
   * again to set a new maximum value.
   *
   * @param max the maximum for iterators created for this tree, or null for no maximum
   */
  @Override
  public void setIteratorMax(Comparable max) {
    //Setter method for max value
    this.max = (T) max;
  }

  /**
   * Removes all values and duplicates from the collection.
   */
  @Override
  public void clear() {
    //clear the tree from the root
    root = null;
  }

  /**
   * Returns an iterator over the values stored in this tree. The iterator uses the start (minimum)
   * value set by a previous call to setIteratorMin, and the stop (maximum) value set by a previous
   * call to setIteratorMax. If setIteratorMin has not been called before, or if it was called with
   * a null argument, the iterator uses no minimum value and starts with the lowest value that
   * exists in the tree. If setIteratorMax has not been called before, or if it was called with a
   * null argument, the iterator uses no maximum value and finishes with the highest value that
   * exists in the tree.
   */
  @Override
  public Iterator iterator() {
    //Runs the private class iterator
    return new RBTIterator((RBTNode<T>) root, min, max);
  }

  /**
   * Nested class for Iterator objects created for this tree and returned by the iterator method.
   * This iterator follows an in-order traversal of the tree and returns the values in sorted,
   * ascending order.
   */
  protected static class RBTIterator<R> implements Iterator<R> {
    // stores the start point (minimum) for the iterator
    Comparable<R> min = null;
    // stores the stop point (maximum) for the iterator
    Comparable<R> max = null;
    // stores the stack that keeps track of the inorder traversal
    Stack<BSTNode<R>> stack = null;

    /**
     * Constructor for a new iterator if the tree with root as its root node, and min as the start
     * (minimum) value (or null if no start value) and max as the stop (maximum) value (or null if
     * no stop value) of the new iterator.
     *
     * @param root root node of the tree to traverse
     * @param min  the minimum value that the iterator will return
     * @param max  the maximum value that the iterator will return
     */
    public RBTIterator(BSTNode<R> root, Comparable<R> min, Comparable<R> max) {
      //set the min to the constructor min, set max to the constructor max, stack is set to constructor stack
      this.min = min;
      this.max = max;
      this.stack = new Stack<>();
      //initialize and update stack
      buildStackHelper(root);
    }

    /**
     * Helper method for initializing and updating the stack. This method both - finds the next data
     * value stored in the tree (or subtree) that is bigger than or equal to the specified start
     * point (maximum), and - builds up the stack of ancestor nodes that contain values larger than
     * or equal to the start point so that those nodes can be visited in the future.
     *
     * @param node the root node of the subtree to process
     */
    private void buildStackHelper(BSTNode<R> node) {
      //If the node is null, the method should return
      if (node == null) {
        return;
      }
      //The data in the node is cast and saved as a Comparable
      Comparable<R> nodeData = (Comparable<R>) node.getData();
      //as long as the min value is not null and the node data is greater than the min value
      if (min != null && nodeData.compareTo((R) min) < 0) {
        //Recursively call the method on the right node of the current node
        buildStackHelper(node.getRight());
      } else {
        //Push the node in the stack
        stack.push(node);
        //Recursively call the method on the left node of the current node
        buildStackHelper(node.getLeft());
      }
    }

    /**
     * Returns true if the iterator has another value to return, and false otherwise.
     */
    public boolean hasNext() {
      //as long as the stack isn't empty, check the iterator values
      while (!stack.isEmpty()) {
        //Make a new BST node that takes in the first val in the stack
        BSTNode<R> first = (RBTNode<R>) stack.peek();
        //Cast and save this node value as a Comparable val in order to compare the data
        Comparable<R> currentData = (Comparable<R>) first.getData();

        // Compare the current data with the max bound or if no bound, if it falls in line, return true
        if (max == null || currentData.compareTo((R) max) <= 0) {
          return true;
        } else {
          // If the data is greater than max, pop the node from the stack and continue
          stack.pop();
        }
      }
      return false;
    }

    /**
     * Returns the next value of the iterator.
     *
     * @throws NoSuchElementException if the iterator has no more values to return
     */
    public R next() {
      //Checks to see if there's other values next
      if (!hasNext()) {
        throw new NoSuchElementException("No more values to return.");
      }
      //Make new BST node that's assigned to the first element in stack
      BSTNode<R> nextNode = stack.pop();
      //Make a new R val that contains the data of the new BST Node
      R finalVal = nextNode.getData();
      //If the right val isn't null, build the stack using the right val
      if (nextNode.getRight() != null) {
        buildStackHelper(nextNode.getRight());
      }
      //Return the data of the next val
      return finalVal;
    }

  }

  /**
   * This test follows book titles using max and min values.
   */
    @Test
    public void testIteratorWithBookTitles() {
      //Make a new tree
      IterableRedBlackTree<String> testTree = new IterableRedBlackTree<>();

      // Insert book titles into the test
      testTree.insert("A Thousand Splendid Suns");
      testTree.insert("Babel");
      testTree.insert("No Country for Old Men");
      testTree.insert("The Picture of Dorian Gray");
      testTree.insert("American Psycho");
      testTree.insert("Where the Wild Things Are");
      testTree.insert("Pride and Prejudice");
      testTree.insert("The Alchemist");

      // Test 1: no min or max bounds
      Iterator<String> iterator = testTree.iterator();
      assertTrue(iterator.hasNext());
      assertEquals("A Thousand Splendid Suns", iterator.next());
      assertEquals("American Psycho", iterator.next());
      assertEquals("Babel", iterator.next());
      assertEquals("No Country for Old Men", iterator.next());
      assertEquals("Pride and Prejudice", iterator.next());
      assertEquals("The Alchemist", iterator.next());
      assertEquals("The Picture of Dorian Gray", iterator.next());
      assertEquals("Where the Wild Things Are", iterator.next());
      //All book titles should be printed
      assertFalse(iterator.hasNext(), "All titles should be printed");

      // Test 2: Test for a min value ("Babel")
      testTree.setIteratorMin("Babel");
      iterator = testTree.iterator();
      assertTrue(iterator.hasNext());
      //Should start printing books from Babel onwards
      assertEquals("Babel", iterator.next());
      assertEquals("No Country for Old Men", iterator.next());
      assertEquals("Pride and Prejudice", iterator.next());
      assertEquals("The Alchemist", iterator.next());
      assertEquals("The Picture of Dorian Gray", iterator.next());
      assertEquals("Where the Wild Things Are", iterator.next());
      assertFalse(iterator.hasNext(), "Stop after 'Where the Wild Things Are'.");

      // Test 3: Test with a max bound ("The Picture of Dorian Gray")
      testTree.setIteratorMin(null);
      testTree.setIteratorMax("The Picture of Dorian Gray");
      iterator = testTree.iterator();
      assertTrue(iterator.hasNext());
      assertEquals("A Thousand Splendid Suns", iterator.next());
      assertEquals("American Psycho", iterator.next());
      assertEquals("Babel", iterator.next());
      assertEquals("No Country for Old Men", iterator.next());
      assertEquals("Pride and Prejudice", iterator.next());
      assertEquals("The Alchemist", iterator.next());
      //Print until "Picture of Dorian Gray"
      assertEquals("The Picture of Dorian Gray", iterator.next());

      assertFalse(iterator.hasNext(), "Stop at 'The Picture of Dorian Gray'.");

      // Test 4: Test min ("Babel") and max ("Pride and Prejudice")
      testTree.setIteratorMin("Babel");
      testTree.setIteratorMax("Pride and Prejudice");
      iterator = testTree.iterator();
      assertTrue(iterator.hasNext());
      assertEquals("Babel", iterator.next());
      assertEquals("No Country for Old Men", iterator.next());
      assertEquals("Pride and Prejudice", iterator.next());
      //Nothing else should print
      assertFalse(iterator.hasNext(), "Stop after 'Pride and Prejudice'");
    }
  /**
   * Test the iterator with an integer tree containing a max with duplicate values.
   *
   */
  @Test
  public void testIteratorWithStopPointOnlyIntegersWithDuplicates() {
    // Create a tree
    IterableRedBlackTree<Integer> tree = new IterableRedBlackTree<>();

    // Insert values including duplicate
    tree.insert(21);
    tree.insert(14);
    tree.insert(28);
    tree.insert(18);
    tree.insert(11);
    tree.insert(32);
    tree.insert(25);
    tree.insert(21);

    // Set the max to 28
    tree.setIteratorMax(28);

    // Make iterator
    Iterator<Integer> iterator = tree.iterator();

    // Check the printed values
    assertTrue(iterator.hasNext());
    assertEquals(11, iterator.next());
    assertEquals(14, iterator.next());
    assertEquals(18, iterator.next());
    assertEquals(21, iterator.next());
    assertEquals(21, iterator.next());
    assertEquals(25, iterator.next());
    assertEquals(28, iterator.next());
    //No other values past 28 should print
    assertFalse(iterator.hasNext(), "Stop after 28");
  }

  /**
   * Test the iterator with min and max values that aren't feasible.
   */
  @Test
  public void testIteratorWithNonExistentBounds() {
    //Make a new tree
    IterableRedBlackTree<Integer> tree = new IterableRedBlackTree<>();
    tree.insert(10);
    tree.insert(21);
    tree.insert(35);
    tree.insert(42);
    tree.insert(59);

    // Set bounds that don't match the exact values in the tree
    tree.setIteratorMin(19);
    tree.setIteratorMax(43);

    Iterator<Integer> iterator = tree.iterator();

    // Only a few values such as 21, 35, 42 should be returned
    assertTrue(iterator.hasNext());
    assertEquals(21, iterator.next());
    assertEquals(35, iterator.next());
    assertEquals(42, iterator.next());
    //No more values should be returned
    assertFalse(iterator.hasNext(), "No more values should be returned.");

    //Reset the max to be lower than the min
    tree.setIteratorMin(35);
    tree.setIteratorMax(25);

    Iterator<Integer> emptyIterator = tree.iterator();
    //Nothing should be returned
    assertFalse(emptyIterator.hasNext(), "Nothing should be returned.");
  }

}



