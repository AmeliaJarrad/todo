import { Link } from "react-router-dom";
import type { Task } from '../../services/tasks';
import styles from "./TaskCard.module.scss";

interface TaskCardProps {
  task: Task;
  onToggleArchive: (id: number, isArchived: boolean) => void;
  onToggleComplete?: (id: number, isCompleted: boolean) => void;
  onDuplicate?: (task: Task) => void;
}

const TaskCard = ({ task, onToggleArchive, onToggleComplete, onDuplicate }: TaskCardProps) => {
  const handleArchiveClick = () => {
    onToggleArchive(task.id, !task.archived);
  };
console.log("rendering Taskcard", task.id, "archived", task.archived);
  return (
    <div className={styles.card}>
      <Link to={`/tasks/${task.id}`}>
        <h2>{task.taskname}</h2>
      </Link>
      <p>Created: {task.createdAt}</p>
      <p>Due: {task.dueDate}</p>
     
      <p>
      Categories:{" "}
      {task.categories.length > 0
        ? task.categories.map(cat => typeof cat === "string" ? cat : cat.catname).join(", ")
        : "None"}
      </p>

    <div className={styles.checkboxRow}>
        <label className={styles.checkbox}>
          <input
            type="checkbox"
            checked={task.isCompleted}
            onChange={() => onToggleComplete?.(task.id, !task.isCompleted)}
          />
          Completed
        </label>
      </div>

  
      <div className={styles.buttonRow}>
        {!task.archived && (
          <button onClick={handleArchiveClick} className={styles.archiveBtn}>
            Archive
          </button>
        )}
        {task.archived && (
          <button onClick={handleArchiveClick} className={styles.unarchiveBtn}>
            Unarchive
          </button>
        )}
        <Link to={`/tasks/${task.id}/edit`} className={styles.editBtn}>
          Edit
        </Link>
        {onDuplicate && (
          <button onClick={() => onDuplicate(task)} className={styles.duplicateBtn}>
            Duplicate
          </button>
        )}


      </div>
  </div>
  );
};

export default TaskCard;
