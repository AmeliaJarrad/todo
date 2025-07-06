import { useEffect, useRef, useState } from 'react';
import styles from './TaskForm.module.scss';
import type { NewTaskForm } from '../../services/tasks';


type Category = {
  id: number;
  catname: string;
};

type TaskFormProps = {
  initialValues?: Partial<NewTaskForm>;
  onSubmit: (formValues: NewTaskForm) => Promise<void>;
  onCancel: () => void;
  submitButtonLabel?: string;
};

const TaskForm = ({ initialValues = {}, onSubmit, onCancel, submitButtonLabel = 'Submit' }: TaskFormProps) => {
  const formRef = useRef<HTMLFormElement>(null);
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

    const isCompleted = form.querySelector<HTMLInputElement>('input[name="isCompleted"]')?.checked ?? false;

    const selectedCategoryIdRaw = entries.categoryId as string | undefined;
    const categoryId = selectedCategoryIdRaw ? Number(selectedCategoryIdRaw) : undefined;

    const newCategoryNameInput = entries.newCategoryName as string | undefined;
    const newCategoryNames = newCategoryNameInput?.trim() ? [newCategoryNameInput.trim()] : [];

    const formValues: NewTaskForm = {
      taskname: entries.taskname as string,
      dueDate: (entries.dueDate as string) || undefined,
      isCompleted,
      isArchived: false,
      categoryIds: categoryId ? [categoryId] : [],
      newCategoryNames,
    };

    await onSubmit(formValues);
  };

  return (
    <div className={styles.card}>
      <form ref={formRef} onSubmit={handleSubmit}>
        <div className={styles.formGroup}>
          <label>
            Task Name:
            <input
              name="taskname"
              type="text"
              defaultValue={initialValues.taskname}
              placeholder="Task name"
              required
            />
          </label>
        </div>

        <div className={styles.formGroup}>
          <label>
            Due Date:
            <input
              name="dueDate"
              type="date"
              defaultValue={initialValues.dueDate}
              min={new Date().toISOString().split('T')[0]}
            />
          </label>
        </div>

        <div className={styles.formGroup}>
          <label>
            Select a Category:
            <select name="categoryId" defaultValue="">
              <option value="" disabled>
                -- Choose one --
              </option>
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
            <input
              name="newCategoryName"
              type="text"
              defaultValue={initialValues.newCategoryNames?.[0]}
              placeholder="New category name"
            />
          </label>
        </div>

        <div className={`${styles.formGroup} ${styles.checkbox}`}>
          <label>
            Completed:
            <input
              name="isCompleted"
              type="checkbox"
              defaultChecked={initialValues.isCompleted}
            />
          </label>
        </div>

        <div className={styles.buttonRow}>
          <button type="button" onClick={onCancel} className={styles.cancelBtn}>
            Cancel
          </button>
          <button type="submit" className={styles.submitBtn}>
            {submitButtonLabel}
          </button>
        </div>
      </form>
    </div>
  );
};

export default TaskForm;