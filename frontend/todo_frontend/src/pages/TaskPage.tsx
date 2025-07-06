import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router"
import { duplicateTask, getTaskById, updateTask, type Task } from "../services/tasks";
import TaskCard from "../components/TaskCard/TaskCard";


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

    //don't need this part now as I'm using the TaskCard component

  //   const handleDelete = async () => {
  //   try {
  //     // Archive the task (soft delete)
  //     await updateTask(task.id, { isArchived: true });
  //     alert("Task archived");
  //     navigate("/"); // Redirect to task list after archive
  //   } catch (err) {
  //     console.error("Failed to archive task", err);
  //   }
  // };

  // archive (soft-delete)
  const handleToggleArchive = async (id: number, isArchived: boolean) => {
    try {
      await updateTask(id, { isArchived });
      alert(isArchived ? "Task archived" : "Task unarchived");
      navigate("/");
    } catch (err) {
      console.error("Failed to archive task", err);
    }
  };

  // complete toggle
  const handleToggleComplete = async (id: number, isCompleted: boolean) => {
    try {
      await updateTask(id, { isCompleted });
      // Update task locally for instant UI feedback
      setTask(prev => prev ? { ...prev, isCompleted } : prev);
    } catch (err) {
      console.error("Failed to mark task complete", err);
    }
  };

//issues with task duplication addressed by fixing the date prompt, tasks with
//dates in past couldn't be duplicated 

  const handleDuplicate = async () => {
    const today = new Date().toISOString().split("T")[0];
    const newDueDate = window.prompt("Enter new due date (YYYY-MM-DD):", today);

      if (!newDueDate) return;

      if (new Date(newDueDate) < new Date(today)) {
      alert("Due date must be today or in the future.");
      return;
    }




    try {
      await duplicateTask({...task, dueDate: newDueDate});
      alert("Task duplicated");
      navigate("/"); // Redirect to task list or refresh
    } catch (err) {
      console.error("Failed to duplicate task", err);
    }
  };


  return (
  <div style={{ maxWidth: "600px", margin: "0 auto" }}>
      <TaskCard
        task={task}
        onToggleArchive={handleToggleArchive}
        onToggleComplete={handleToggleComplete}
        onDuplicate={handleDuplicate}
      />

    </div>
   
  )
}

export default TaskPage;