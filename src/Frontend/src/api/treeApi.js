export async function insertNode(value) {
    await fetch("http://localhost:8080/api/insert", {
      method: "POST",
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify({value})
    });
  }
  
  export async function getTree() {
    const res = await fetch("http://localhost:8080/api/tree");
    return res.json();
  }
  