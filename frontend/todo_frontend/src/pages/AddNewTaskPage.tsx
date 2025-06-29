import { useEffect, useRef, useState } from 'react';
import { createTask, type NewTaskForm } from '../services/tasks'; 
import { useNavigate } from 'react-router-dom';
import styles from './AddNewTaskPage.module.scss';

type Category = {
  id: number;
  catname: string;
};

const AddNewTask = () => {
  const formRef = useRef<HTMLFormElement>(null);
  const navigate = useNavigate();
  const [categories, setCategories] = useState<Category[]>([]);


  useEffect(() => {
    fetch('http://localhost:8080/categories')
      .then(res => res.json())
      .then(data => setCategories(data))
      .catch(err => console.error('Failed to load categories:', err));
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const form = formRef.current;
    if (!form) return;

    const formData = new FormData(form);
    const entries = Object.fromEntries(formData.entries());

    

// Because form inputs always come as strings (or empty strings if unchecked), 
  // we must parse checkbox value and convert accordingly

  const isCompleted = form.querySelector<HTMLInputElement>('input[name="isCompleted"]')?.checked ?? false;

   const selectedCategoryIdRaw = entries.categoryId as string | undefined;
   //this is reading the selected category from the <select> dropdown as string or undefined (if none selected)
    const categoryId = selectedCategoryIdRaw ? Number(selectedCategoryIdRaw) : undefined;
    //converts that string into its ID number, or undefined if not selected


    
     const newCategoryNameInput = entries.newCategoryName as string | undefined;
    const newCategoryNames = newCategoryNameInput?.trim()
      ? [newCategoryNameInput.trim()]
      : [];


    const formValues: NewTaskForm = {
      taskname: entries.taskname as string,
      dueDate: (entries.dueDate as string) || undefined,
      isCompleted,
      isArchived: false,
      categoryIds: categoryId ? [categoryId] : [],
      newCategoryNames,
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

  const handleCancel = () => {
    navigate('/');
  };


   return (
    <div className={styles.card}>
      <form ref={formRef} onSubmit={handleSubmit}>
        <div className={styles.formGroup}>
          <label>
            Task Name:
            <input name="taskname" type="text" placeholder="Task name" required />
          </label>
        </div>

        <div className={styles.formGroup}>
          <label>
            Due Date:
            <input name="dueDate" type="date"  min={new Date().toISOString().split("T")[0]}/>
          </label>
        </div>

        <div className={styles.formGroup}>
          <label>
            Select a Category:
            <select name="categoryId" defaultValue="">
              <option value="" disabled>-- Choose one --</option>
              {categories.map(cat => (
                <option key={cat.id} value={cat.id}>
                  {cat.catname}
                </option>
              ))}
            </select>
          </label>
        </div>

        <div className={styles.formGroup}>
          <label>
            Or add new category:
            <input name="newCategoryName" type="text" placeholder="New category name" />
          </label>
        </div>

        <div className={`${styles.formGroup} ${styles.checkbox}`}>
          
        </div>

        <div className={styles.buttonRow}>
          
          <button type="button" onClick={handleCancel} className={styles.cancelBtn}>Cancel</button>
        
          <button type="submit" className={styles.submitBtn}>Create Task</button>
        
        </div>
      </form>
    </div>
  );
};

export default AddNewTask;