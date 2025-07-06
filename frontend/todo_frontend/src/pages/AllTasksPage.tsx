import { useEffect, useState } from 'react';
import { getAllTasks, updateTask, type Task } from '../services/tasks';
import TaskList from '../components/TaskList/TaskList';
import styles from '../components/TaskList/TaskList.module.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faChevronDown, faChevronRight } from '@fortawesome/free-solid-svg-icons';

const AllTasksPage = () => {
  const [tasks, setTasks] = useState<Task[]>([]);
   const [showCompleted, setShowCompleted] = useState(false); // state to toggle completed section

  useEffect(() => {
    getAllTasks()
      .then(setTasks)
      .catch(console.warn);
  }, []);

  const handleToggleArchive = async (id: number, isArchived: boolean) => {
    try {
      await updateTask(id, { isArchived });
      setTasks((prev) => prev.filter((task) => task.id !== id));
    } catch (error) {
      console.error("Error toggling archive:", error);
    }
  };

  const handleToggleComplete = async (id: number, isCompleted: boolean) => {
  try {
    await updateTask(id, { isCompleted });
    setTasks(prev =>
      prev.map(t => t.id === id ? { ...t, isCompleted } : t)
    );
  } catch (error) {
    console.error("Error updating completion status:", error);
  }
};

// Separate tasks
  const incompleteTasks = tasks.filter(task => !task.isCompleted);
  const completedTasks = tasks.filter(task => task.isCompleted);


  return (
    <div>
      <h2 className={styles.sectionHeader}>Todo Tasks</h2>
      {incompleteTasks.length > 0 ? (
        <TaskList
          tasks={incompleteTasks}
          onToggleArchive={handleToggleArchive}
          onToggleComplete={handleToggleComplete}
        />
      ) : (
        <p>No incomplete tasks</p>
      )}

      {completedTasks.length > 0 && (
        <div>
          <div
            className={styles.completedHeader}
            onClick={() => setShowCompleted(!showCompleted)}
          >
            <FontAwesomeIcon icon={showCompleted ? faChevronDown : faChevronRight} />
            Completed Tasks
          </div>

          {showCompleted && (
            <TaskList
              tasks={completedTasks}
              onToggleArchive={handleToggleArchive}
              onToggleComplete={handleToggleComplete}
            />
          )}
        </div>
      )}
    </div>
  );
};

export default AllTasksPage;