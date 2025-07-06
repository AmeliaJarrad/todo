import { useNavigate } from 'react-router-dom';
import TaskForm from '../components/TaskForm/TaskForm'
import { createTask, type NewTaskForm } from '../services/tasks';

const AddNewTaskPage = () => {
  const navigate = useNavigate();

  const handleSubmit = async (formValues: NewTaskForm) => {
    await createTask(formValues);
    navigate('/');
  };

  return (
    <TaskForm
      onSubmit={handleSubmit}
      onCancel={() => navigate('/')}
      submitButtonLabel="Create Task"
    />
  );
};

export default AddNewTaskPage;
