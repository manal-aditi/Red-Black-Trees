package Backend.src.main.controller;

import Backend.src.main.service.TreeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // allow React frontend
public class RbtController {

  private final TreeService treeService;

  public RbtController(TreeService treeService) {
    this.treeService = treeService;
  }

  /**
   * Returns the entire red-black tree as JSON.
   */
  @GetMapping("/tree")
  public Map<String, Object> getTree() {
    return treeService.getTree();
  }

  /**
   * Inserts a new value into the red-black tree.
   */
  @PostMapping("/insert")
  public ResponseEntity<?> insert(@RequestBody Map<String, String> body) {
    String value = body.get("value");
    treeService.insert(value);
    return ResponseEntity.ok(Map.of("ok", true));
  }
}
