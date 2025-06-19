import { useEffect, useState } from 'react';
import { getArchivedTasks, type Task } from '../services/tasks';
import TaskCard from '../components/TaskCard/TaskCard';

const ArchivedTasksPage = () => {
  const [tasks, setTasks] = useState<Task[]>([]);

  useEffect(() => {
    getArchivedTasks()
      .then(setTasks)
      .catch(console.warn);
  }, []);

  return (
    <div>
      <h1>Archived Tasks</h1>
      {tasks.length === 0 ? (
        <p>No archived tasks found.</p>
      ) : (
        tasks.map((task) => <TaskCard key={task.id} task={task} />)
      )}
    </div>
  );
};

export default ArchivedTasksPage;
