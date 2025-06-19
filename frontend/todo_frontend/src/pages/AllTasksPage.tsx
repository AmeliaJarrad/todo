import  { useEffect, useState } from 'react'
import { getAllTasks, type Task } from '../services/tasks';
import TaskCard from '../components/TaskCard/TaskCard';

const AllTasksPage = () => {
    const [tasks, setTasks] = useState<Task[]>([]);

    useEffect(() => {
        getAllTasks()
        .then((res) => {
            console.log(res, "tasks from API");
            setTasks(res);
        })
        .catch(console.warn);
    }, []);
  return (
    <div>
        {tasks.map((task: Task) => {
            return <TaskCard task={task} key={task.id} />;
        })}
    </div>
  )
}

export default AllTasksPage