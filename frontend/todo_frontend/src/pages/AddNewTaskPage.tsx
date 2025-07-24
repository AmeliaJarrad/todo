import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import TaskForm from '../components/TaskForm/TaskForm';
import { createTask, type NewTaskForm } from '../services/tasks';
import Modal from '../components/Modal/Modal';

const AddNewTaskPage = () => {
  const navigate = useNavigate();

  const [showModal, setShowModal] = useState(false);
  const [modalMessage, setModalMessage] = useState('');
  const [modalTitle, setModalTitle] = useState(''); 
  const [isSuccess, setIsSuccess] = useState(false); // track success vs error

  const handleSubmit = async (formValues: NewTaskForm) => {
    try {
      await createTask(formValues);
      setModalMessage('Task created successfully!');
      setModalTitle('Success');
      setIsSuccess(true);
      setShowModal(true);

      setTimeout(() => {
        setShowModal(false);
        navigate('/');
      }, 2000);
    } catch (error) {
      console.error('Failed to create task', error);
      setModalMessage('Failed to create task.');
      setModalTitle('Error');
      setIsSuccess(false);
      setShowModal(true);
    }
  };

  const showModalMessage = (message: string,  title = "Alert!", success = false) => {
    setModalMessage(message);
    setIsSuccess(success);
    setModalTitle(title);
    setShowModal(true);
  };

  return (
    <>
      {showModal && (
        <Modal show={showModal} onClose={() => setShowModal(false)} title={modalTitle}>
          <p>{modalMessage}</p>
        </Modal>
      )}

      <TaskForm
        onSubmit={handleSubmit}
        onCancel={() => navigate('/')}
        submitButtonLabel="Create Task"
        showModalMessage={showModalMessage}
      />
    </>
  );
};

export default AddNewTaskPage;
