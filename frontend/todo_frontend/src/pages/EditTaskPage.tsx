import { useEffect, useState, useRef } from "react";
import { useParams, useNavigate } from "react-router";
import { getTaskById, updateTask, type Task } from "../services/tasks";

const EditTaskForm = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const formRef = useRef<HTMLFormElement>(null);

  const [task, setTask] = useState<Task | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!id) return;
    getTaskById(id)
      .then((data) => {
        setTask(data);
        setLoading(false);
      })
      .catch(() => {
        setError("Failed to load task");
        setLoading(false);
      });
  }, [id]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!formRef.current || !task) return;

    const formData = new FormData(formRef.current);
    const entries = Object.fromEntries(formData.entries());

    // checkbox special handling
    const isCompleted = formRef.current.querySelector<HTMLInputElement>('input[name="isCompleted"]')?.checked ?? false;

    // categories from comma separated string to array
    const categoryNamesInput = entries.categoryNames as string | undefined;
    const categoryNames = categoryNamesInput
      ? categoryNamesInput.split(",").map((s) => s.trim()).filter(Boolean)
      : [];

    try {
      await updateTask(task.id, {
        taskname: entries.taskname as string,
        dueDate: (entries.dueDate as string) || undefined,
        isCompleted,
        categoryNames,
      });

      alert("Task updated successfully!");
      navigate(`/tasks/${task.id}`); // back to task detail page
    } catch (err) {
      console.error("Failed to update task", err);
      setError("Failed to update task");
    }
  };

  if (loading) return <p>Loading task data...</p>;
  if (error) return <p>{error}</p>;
  if (!task) return <p>Task not found</p>;

  return (
    <form ref={formRef} onSubmit={handleSubmit}>
      <h2>Edit Task</h2>
      <label>
        Task Name:
        <input name="taskname" type="text" defaultValue={task.taskname} required />
      </label>
      <br />
      <label>
        Due Date:
        <input name="dueDate" type="date" defaultValue={task.dueDate || ""} />
      </label>
      <br />
      <label>
        Completed:
        <input name="isCompleted" type="checkbox" defaultChecked={task.isCompleted} />
      </label>
      <br />
      <label>
        Categories (comma separated):
        <input name="categoryNames" type="text" defaultValue={task.categories.join(", ")} />
      </label>
      <br />
      <button type="submit">Save</button>
      <button type="button" onClick={() => navigate(`/tasks/${task.id}`)}>Cancel</button>
    </form>
  );
};

export default EditTaskForm;
