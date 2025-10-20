package Backend.src.main.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.Test;



public class Tests {
    
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
 

  @Test
  public void testOne() {
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
  public void testTwo() {
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
