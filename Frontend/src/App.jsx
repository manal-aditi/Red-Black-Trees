import React from "react";
import TaskScheduler from "./components/TaskScheduler";
import RedBlackVisualizer from "./components/RedBlackVisualizer";

export default function App() {
  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <h1 className="text-3xl font-bold mb-6 text-center">
        Red-Black Tree Visualizer & Task Scheduler
      </h1>

      <div className="grid md:grid-cols-2 gap-8">
        <RedBlackVisualizer />
        <TaskScheduler />
      </div>
    </div>
  );
}
