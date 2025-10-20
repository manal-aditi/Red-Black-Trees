// P102/frontend/src/components/RedBlackVisualizer.jsx
import React, { useEffect, useState } from "react";
import { getTree, insertNode } from "../api/treeApi";
import NodeComponent from "./NodeComponent";

function computePositions(root) {
  if (!root) return [];
  const levels = [];
  const queue = [{ node: root, level: 0 }];
  while (queue.length) {
    const { node, level } = queue.shift();
    if (!levels[level]) levels[level] = [];
    levels[level].push(node);
    if (node.left) queue.push({ node: node.left, level: level + 1 });
    if (node.right) queue.push({ node: node.right, level: level + 1 });
  }
  const items = [];
  const width = 900;
  const spacingX = 100;
  const spacingY = 110;
  levels.forEach((nodes, L) => {
    const totalWidth = (nodes.length - 1) * spacingX;
    nodes.forEach((n, i) => {
      const x = 50 + i * spacingX + (width - totalWidth) / 2 - 20;
      const y = 40 + L * spacingY;
      items.push({ node: n, x, y });
    });
  });
  return items;
}

export default function RedBlackVisualizer() {
  const [tree, setTree] = useState(null);
  const [value, setValue] = useState("");

  async function refresh() {
    try {
      const json = await getTree();
      setTree(json.root || null);
    } catch (e) {
      console.error(e);
    }
  }

  useEffect(() => { refresh(); }, []);

  async function handleInsert(e) {
    e.preventDefault();
    if (!value) return;
    await insertNode(value);
    setValue("");
    await refresh();
  }

  const positions = computePositions(tree);
  const mapByData = new Map(positions.map(p => [p.node.data, p]));

  return (
    <div>
      <form onSubmit={handleInsert} style={{ display: "flex", gap: 8, marginBottom: 12 }}>
        <input value={value} onChange={(e) => setValue(e.target.value)} placeholder="Insert value (e.g., J, A, 5)" />
        <button type="submit">Insert</button>
        <button type="button" onClick={refresh}>Refresh</button>
      </form>

      <div style={{ overflow: "auto", border: "1px solid #e2e8f0", background: "#fff", height: 520 }}>
        <svg width={1000} height={520}>
          {positions.map(p => {
            const { node } = p;
            if (node.left) {
              const a = mapByData.get(node.data);
              const b = mapByData.get(node.left.data);
              if (a && b) {
                // line from parent bottom to child top
                const x1 = a.x;
                const y1 = a.y + 18;
                const x2 = b.x;
                const y2 = b.y - 18;
                return <line key={`${node.data}-${node.left.data}`} x1={x1} y1={y1} x2={x2} y2={y2} stroke="#94a3b8" strokeWidth="2" />;
              }
            }
            return null;
          })}

          {positions.map((p, i) => {
            const { node, x, y } = p;
            const color = node.color || "BLACK";
            return <NodeComponent key={`${node.data}-${i}`} x={x} y={y} text={node.data} color={color} />;
          })}
        </svg>
      </div>
    </div>
  );
}
