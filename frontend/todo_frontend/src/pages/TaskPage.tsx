import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router"
import { duplicateTask, getTaskById, updateTask, type Task } from "../services/tasks";


const TaskPage = () => {
    const { id } = useParams();
    const navigate = useNavigate();

    const [task, setTask] = useState<Task | null>(null);

    useEffect(() => {
        if (id) getTaskById(id).then(setTask).catch(console.log);
    }, [id]);

    console.log(task);

    if(!task) {
        return <p>Loading task...</p>;
    }

    const handleDelete = async () => {
    try {
      // Archive the task (soft delete)
      await updateTask(task.id, { isArchived: true });
      alert("Task archived");
      navigate("/"); // Redirect to task list after archive
    } catch (err) {
      console.error("Failed to archive task", err);
    }
  };

  const handleDuplicate = async () => {
    try {
      await duplicateTask(task);
      alert("Task duplicated");
      navigate("/"); // Redirect to task list or refresh
    } catch (err) {
      console.error("Failed to duplicate task", err);
    }
  };

  const handleEdit = () => {
    navigate(`/tasks/${task.id}/edit`); // Assuming you have an edit page route
  };

  return (
    <div>
        <h2>{task.taskname}</h2>
        <p>{task.createdAt}</p>
        <p>{task.dueDate}</p>
        <p>Completed: {task.isCompleted ? "Yes" : "No"}</p>
        <p>Archived: {task.isArchived ? "Yes" : "No"}</p>
        <p>Last Updated: {task.updatedAt ? task.updatedAt : "N/A"}</p>
        <p>Categories: {task.categories.length > 0 ? task.categories.join(", ") : "None"}</p>

        <button onClick={handleEdit}>Edit</button>
        <button onClick={handleDelete} style={{ marginLeft: "10px" }}>
            Delete (Archive)
        </button>
        <button onClick={handleDuplicate} style={{ marginLeft: "10px" }}>
            Duplicate
        </button>
    </div>
  )
}

export default TaskPage;