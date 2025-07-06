// src/pages/EditTaskForm.tsx

import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getTaskById, updateTask, type Task, type NewTaskForm } from "../services/tasks";
import TaskForm from '../components/TaskForm/TaskForm'

const EditTaskForm = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [task, setTask] = useState<Task | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!id) return;

    getTaskById(id)
      .then(setTask)
      .catch(() => setError("Failed to load task"))
      .finally(() => setLoading(false));
  }, [id]);

  const handleSubmit = async (formValues: NewTaskForm) => {
    if (!task) return;

    try {
      await updateTask(task.id, formValues);
      alert("Task updated successfully!");
      navigate(`/tasks/${task.id}`);
    } catch (err) {
      console.error("Failed to update task", err);
      setError("Failed to update task");
    }
  };

  if (loading) return <p>Loading task data...</p>;
  if (error) return <p>{error}</p>;
  if (!task) return <p>Task not found</p>;

  // Convert existing task into initialValues shape
  const initialValues: NewTaskForm = {
    taskname: task.taskname,
    dueDate: task.dueDate || undefined,
    isCompleted: task.isCompleted,
    isArchived: false, // You can modify if needed
    categoryIds: task.categories.length > 0 ? [task.categories[0].id] : [],
    newCategoryNames: [], // This will always be empty on edit
  };

  return (
    <TaskForm
      initialValues={initialValues}
      onSubmit={handleSubmit}
      onCancel={() => navigate(`/tasks/${task.id}`)}
      submitButtonLabel="Save Changes"
    />
  );
};

export default EditTaskForm;
