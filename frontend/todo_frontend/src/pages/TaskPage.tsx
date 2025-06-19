import { useEffect, useState } from "react";
import { useParams } from "react-router"
import { getTaskById, type Task } from "../services/tasks";


const TaskPage = () => {
    const { id } = useParams();

    const [task, setTask] = useState<Task | null>(null);

    useEffect(() => {
        if (id) getTaskById(id).then(setTask).catch(console.log);
    }, [id]);

    console.log(task);

    if(!task) {
        return <p>Loading task...</p>;
    }

  return (
    <div>
        <h2>{task.taskname}</h2>
        <p>{task.createdAt}</p>
        <p>{task.dueDate}</p>
        <p>Completed: {task.isCompleted ? "Yes" : "No"}</p>
        <p>Archived: {task.isArchived ? "Yes" : "No"}</p>
        <p>Last Updated: {task.updatedAt ? task.updatedAt : "N/A"}</p>
    </div>
  )
}

export default TaskPage;