import { useEffect, useState } from 'react';
import { getAllTasks, updateTask, type Task } from '../services/tasks';
import TaskList from '../components/TaskList/TaskList';

const AllTasksPage = () => {
  const [tasks, setTasks] = useState<Task[]>([]);

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

  return (
    <div>
      <TaskList tasks={tasks} onToggleArchive={handleToggleArchive} />
    </div>
  );
};

export default AllTasksPage;
