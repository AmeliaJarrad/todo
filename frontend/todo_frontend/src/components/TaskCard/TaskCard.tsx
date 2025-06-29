import { Link } from "react-router-dom";
import type { Task } from '../../services/tasks';
import styles from "./TaskCard.module.scss";

interface TaskCardProps {
  task: Task;
  onToggleArchive: (id: number, isArchived: boolean) => void;
}

const TaskCard = ({ task, onToggleArchive }: TaskCardProps) => {
  const handleArchiveClick = () => {
    onToggleArchive(task.id, !task.isArchived);
  };
console.log("rendering Taskcard", task)
  return (
    <div className={styles.card}>
      <Link to={`/tasks/${task.id}`}>
        <h2>{task.taskname}</h2>
      </Link>
      <p>Created: {task.createdAt}</p>
      <p>Due: {task.dueDate}</p>
     
      <p>Categories: {task.categories.length > 0 ? task.categories.join(", ") : "None"}</p>

      <div className={styles.buttonRow}>
        {!task.isArchived && (
          <button onClick={handleArchiveClick} className={styles.archiveBtn}>Archive</button>
        )}
        {task.isArchived && (
          <button onClick={handleArchiveClick} className={styles.unarchiveBtn}>Unarchive</button>
        )}
        <Link to={`/tasks/${task.id}/edit`} className={styles.editBtn}>Edit</Link>
      </div>
    </div>
  );
};

export default TaskCard;
