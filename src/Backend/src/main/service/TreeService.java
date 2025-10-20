package Backend.src.main.service;

import Backend.src.main.model.RBTNode;
import Backend.src.main.model.RedBlackTree;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TreeService {

  private final RedBlackTree<String> tree = new RedBlackTree<>();

  /**
   * Insert a new value into the red-black tree.
   */
  public void insert(String value) {
    if (value == null || value.isEmpty()) {
      throw new IllegalArgumentException("Value cannot be null or empty");
    }
    tree.insert(value);
  }

  /**
   * Return the current tree structure as a nested JSON-compatible map.
   */
  public Map<String, Object> getTree() {
    return Map.of("root", nodeToMap((RBTNode<String>) tree.root));
  }

  /**
   * Recursively convert an RBTNode into a map for JSON serialization.
   */
  private Map<String, Object> nodeToMap(RBTNode<String> n) {
    if (n == null) return null;
    Map<String, Object> map = new HashMap<>();
    map.put("data", n.getData());
    map.put("color", n.isRed() ? "RED" : "BLACK");
    map.put("left", nodeToMap(n.getLeft()));
    map.put("right", nodeToMap(n.getRight()));
    return map;
  }
}
