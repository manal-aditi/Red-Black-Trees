export default function NodeComponent({ data, color, left, right }) {
    return (
      <div className="flex flex-col items-center">
        <div
          className={`rounded-full p-3 text-white font-bold ${
            color === "RED" ? "bg-red-500" : "bg-black"
          }`}
        >
          {data}
        </div>
        <div className="flex space-x-4 mt-2">
          {left && <NodeComponent {...left} />}
          {right && <NodeComponent {...right} />}
        </div>
      </div>
    );
  }
  