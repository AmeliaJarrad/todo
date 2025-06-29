import { useEffect, useState, useRef } from "react";
import { useParams, useNavigate } from "react-router";
import { getTaskById, updateTask, type Task } from "../services/tasks";


type Category = {
  id: number;
  catname: string;
};


const EditTaskForm = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const formRef = useRef<HTMLFormElement>(null);

  const [task, setTask] = useState<Task | null>(null);
  const [categories, setCategories] = useState<Category[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!id) return;

    getTaskById(id)
      .then(data => {
        setTask(data);
        return fetch("http://localhost:8080/categories");
      })
      .then(res => res.json())
      .then(data => {
        setCategories(data);
        setLoading(false);
      })
      .catch(() => {
        setError("Failed to load data");
        setLoading(false);
      });
  }, [id]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const form = formRef.current;
    if (!form || !task) return; // <- double safety check

    const formData = new FormData(form);
    const entries = Object.fromEntries(formData.entries());

    // checkbox special handling
    const isCompleted = form.querySelector<HTMLInputElement>('input[name="completed"]')?.checked ?? false;

    // refactoring to single category option
  const selectedCategoryIdRaw = entries.categoryId as string | undefined;
  const newCategoryNameInput = entries.newCategoryName as string | undefined;

  let categoryIds: number[] = [];
  let newCategoryNames: string[] = [];

  if (newCategoryNameInput?.trim()) {
    // Prioritize new category if provided
    newCategoryNames = [newCategoryNameInput.trim()];
  } else if (selectedCategoryIdRaw) {
    const categoryId = Number(selectedCategoryIdRaw);
    if (!isNaN(categoryId)) {
      categoryIds = [categoryId];
    }
  }


    try {
      await updateTask(task.id, {
      taskname: entries.taskname as string,
      dueDate: (entries.dueDate as string) || undefined,
      isCompleted,
      categoryIds,
      newCategoryNames,
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

  const selectedCategory = task.categories.length > 0 
    ? categories.find(cat => cat.catname === task.categories[0])
    : undefined;

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
        <input name="dueDate" type="date" min={new Date().toISOString().split("T")[0]} />
      </label>
      <br />

      <label>
        Completed:
        <input name="isCompleted" type="checkbox" defaultChecked={task.isCompleted} />
      </label>
      <br />

      <label>
        Category:
        <select name="categoryId" defaultValue={selectedCategory ? String(selectedCategory.id) : ""} required>
          <option value="" disabled>Select a category</option>
          {categories.map(cat => (
            <option key={cat.id} value={cat.id}>{cat.catname}</option>
          ))}
        </select>
      </label>
      <br />

      <button type="submit">Save</button>
      <button type="button" onClick={() => navigate(`/tasks/${task.id}`)}>Cancel</button>
    </form>
  );
};

export default EditTaskForm;
