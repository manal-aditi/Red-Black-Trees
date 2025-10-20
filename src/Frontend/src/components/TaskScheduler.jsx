import React, { useState, useEffect } from "react";
import axios from "axios";

export default function TaskScheduler() {
  const [tasks, setTasks] = useState([]);
  const [form, setForm] = useState({
    name: "",
    dueDate: "",
    priority: "3",
    description: "",
  });

  // Fetch all tasks
  const fetchTasks = async () => {
    const res = await axios.get("http://localhost:8080/api/tasks/all");
    setTasks(res.data);
  };

  useEffect(() => {
    fetchTasks();
  }, []);

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    await axios.post("http://localhost:8080/api/tasks/add", form);
    setForm({ name: "", dueDate: "", priority: "3", description: "" });
    fetchTasks();
  };

  return (
    <div className="p-6 max-w-2xl mx-auto bg-white rounded-2xl shadow-lg">
      <h2 className="text-2xl font-bold mb-4 text-gray-800">
        🗓️ Task Scheduler
      </h2>

      <form
        onSubmit={handleSubmit}
        className="grid grid-cols-1 sm:grid-cols-2 gap-4 mb-6"
      >
        <input
          type="text"
          placeholder="Task name"
          value={form.name}
          onChange={(e) => setForm({ ...form, name: e.target.value })}
          className="border p-2 rounded"
          required
        />
        <input
          type="datetime-local"
          value={form.dueDate}
          onChange={(e) => setForm({ ...form, dueDate: e.target.value })}
          className="border p-2 rounded"
          required
        />
        <select
          value={form.priority}
          onChange={(e) => setForm({ ...form, priority: e.target.value })}
          className="border p-2 rounded"
        >
          <option value="1">High (1)</option>
          <option value="2">Medium-High (2)</option>
          <option value="3">Medium (3)</option>
          <option value="4">Low (4)</option>
          <option value="5">Very Low (5)</option>
        </select>
        <input
          type="text"
          placeholder="Description"
          value={form.description}
          onChange={(e) => setForm({ ...form, description: e.target.value })}
          className="border p-2 rounded col-span-1 sm:col-span-2"
        />
        <button
          type="submit"
          className="bg-blue-500 text-white py-2 px-4 rounded col-span-1 sm:col-span-2 hover:bg-blue-600"
        >
          Add Task
        </button>
      </form>

      <div>
        <h3 className="text-lg font-semibold mb-2 text-gray-700">
          Scheduled Tasks
        </h3>
        {tasks.length === 0 ? (
          <p className="text-gray-500">No tasks scheduled yet.</p>
        ) : (
          <ul className="space-y-2">
            {tasks.map((t, i) => (
              <li
                key={i}
                className="border rounded-lg p-3 flex flex-col sm:flex-row sm:justify-between"
              >
                <div>
                  <span className="font-medium">{t.name}</span> <br />
                  <span className="text-gray-500 text-sm">
                    Due: {t.dueDate.replace("T", " ")}
                  </span>
                  <p className="text-sm text-gray-600">{t.description}</p>
                </div>
                <span
                  className={`text-sm font-semibold mt-2 sm:mt-0 ${
                    t.priority === 1
                      ? "text-red-600"
                      : t.priority === 2
                      ? "text-orange-500"
                      : "text-green-600"
                  }`}
                >
                  Priority {t.priority}
                </span>
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
}
