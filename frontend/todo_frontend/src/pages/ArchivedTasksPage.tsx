import { useEffect, useState } from 'react';
import { getArchivedTasks, updateTask, type Task } from '../services/tasks';
import TaskList from '../components/TaskList/TaskList';

const ArchivedTasksPage = () => {
  const [tasks, setTasks] = useState<Task[]>([]);

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
      setTasks((prev) => prev.filter((task) => task.id !== id));
    } catch (error) {
      console.error("Error toggling archive:", error);
    }
  };

  return (
    <div>
      <TaskList tasks={tasks} onToggleArchive={handleToggleArchive} />
    </div>
  );
};

export default ArchivedTasksPage;
