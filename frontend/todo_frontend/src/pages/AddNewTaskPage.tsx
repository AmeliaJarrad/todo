import { useRef } from 'react';
import { createTask, type NewTaskForm } from '../services/tasks'; // Assuming you have a createTask service function
import { useNavigate } from 'react-router-dom';

const AddNewTask = () => {
  const formRef = useRef<HTMLFormElement>(null);
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const form = formRef.current;
    if (!form) return;

    const formData = new FormData(form);
    const entries = Object.fromEntries(formData.entries());

// Because form inputs always come as strings (or empty strings if unchecked), 
  // we must parse checkbox value and convert accordingly

  const isCompleted = form.querySelector<HTMLInputElement>('input[name="completed"]')?.checked ?? false;

  // Split categories by comma and trim, or empty array if input empty
  const categoryNamesInput = entries.categoryNames as string | undefined;
  const categoryNames = categoryNamesInput
    ? categoryNamesInput.split(',').map((s) => s.trim()).filter(Boolean)
    : [];

    const formValues: NewTaskForm = {
    taskname: entries.taskname as string,
    dueDate: (entries.dueDate as string) || undefined,
    isCompleted: isCompleted ?? false,
    isArchived: false, // default new tasks not archived
    categoryNames: categoryNames,
  };

    try {
    console.log("Submitting to backend:", JSON.stringify(formValues, null, 2));
      await createTask(formValues); // Send data to backend
      form.reset(); // Reset form after successful submission
      navigate('/'); // Redirect to homepage or tasks list
    } catch (error) {
      console.error('Failed to create task:', error);
      // Show error to user if fails to create
    }
  };

  return (
  <form ref={formRef} onSubmit={handleSubmit}>
  <input name="taskname" type="text" placeholder="Task name" required />
  <input name="dueDate" type="date" />
  <input name="categoryNames" type="text" placeholder="Comma-separated categories" />
  <label>
    Completed:
    <input name="completed" type="checkbox" />
  </label>
  <button type="submit">Create Task</button>
</form>

  );
};

export default AddNewTask;
