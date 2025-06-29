import TaskCard from '../TaskCard/TaskCard';
import styles from './TaskList.module.scss';
import type { Task } from '../../services/tasks';

interface TaskListProps {
  tasks: Task[];
  onToggleArchive: (id: number, isArchived: boolean) => void;
}

const TaskList = ({ tasks, onToggleArchive }: TaskListProps) => {
  return (
    <div className={styles.tasksContainer}>
      {tasks.map((task) => (
        <TaskCard key={task.id} task={task} onToggleArchive={onToggleArchive} />
      ))}
    </div>
  );
};

export default TaskList;
