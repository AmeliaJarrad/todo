import { useEffect, useState } from 'react';
import { getArchivedTasks, updateTask, type Task } from '../services/tasks';
import TaskList from '../components/TaskList/TaskList';
import { useNavigate } from 'react-router-dom';
import styles from '../components/TaskCard/TaskCard.module.scss';  // Import styles

const ArchivedTasksPage = () => {
  const [tasks, setTasks] = useState<Task[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    getArchivedTasks()
      .then(tasks => {
      console.log("Archived tasks fetched:", tasks);
      setTasks(tasks);
      })
      .catch(console.warn);
  }, []);

  const handleToggleArchive = async (id: number, isArchived: boolean) => {
    
   try {
    await updateTask(id, { isArchived });

    if (!isArchived) {
      // If unarchived, go back to AllTasks
      navigate('/');
    } else {
      // If re-archived (probably never triggered here), remove from local list
      setTasks((prev) => prev.filter((task) => task.id !== id));
    }
  } catch (error) {
    console.error("Error toggling archive:", error);
  }
};
  return (
    <div>
    {tasks.length === 0 ? (
      <div className={styles.card}>
        <h2>No archived tasks</h2>
        <p>You don't have any archived tasks yet.</p>
      </div>
    ) : (
      <TaskList tasks={tasks} onToggleArchive={handleToggleArchive} />
    )}
  </div>
  );
};

export default ArchivedTasksPage;
