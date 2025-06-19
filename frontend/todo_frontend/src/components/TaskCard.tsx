import { Link } from "react-router";
import type { Task } from '../services/tasks';
import styles from "./TaskCard.module.scss";

interface TaskCardProps {
    task: Task;
}


const TaskCard = ({ task }: TaskCardProps) => {
  return (
    <div className={styles.card}>
        <Link to={`/tasks/${task.id}`}>
            <h2>{task.taskname}</h2>
        </Link>
        <p>{task.createdAt}</p>
        <p>{task.dueDate}</p>
        <p>{task.isCompleted}</p>
       

    </div>
  )
}

export default TaskCard;