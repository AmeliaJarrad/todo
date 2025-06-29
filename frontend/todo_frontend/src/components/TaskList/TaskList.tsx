import TaskCard from '../TaskCard/TaskCard';
import styles from './TaskList.module.scss';
import type { Task } from '../../services/tasks';

interface TaskListProps {
  tasks: Task[];
  onToggleArchive: (id: number, isArchived: boolean) => void;
  onToggleComplete?: (id: number, isCompleted: boolean) => void;
}

const TaskList = ({ tasks, onToggleArchive, onToggleComplete }: TaskListProps) => {
     if (!tasks || !Array.isArray(tasks)) {
    return <div>No tasks available</div>;
  }
  
  return (
    <div className={styles.tasksContainer}>
      {tasks.map((task) => (
        <TaskCard key={task.id} task={task} onToggleArchive={onToggleArchive} 
         onToggleComplete={onToggleComplete} />
      ))}
    </div>
  );
};

export default TaskList;
