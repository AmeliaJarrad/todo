import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getTaskById, updateTask, type Task, type NewTaskForm } from "../services/tasks";
import TaskForm from '../components/TaskForm/TaskForm'
import Modal from '../components/Modal/Modal';

const EditTaskForm = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [task, setTask] = useState<Task | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [showModal, setShowModal] = useState(false);
  const [modalMessage, setModalMessage] = useState('');
  const [modalTitle, setModalTitle] = useState("Notice");


  useEffect(() => {
    if (!id) return;

    getTaskById(id)
      .then(task => {
        console.log("Fetched task:", task);
        setTask(task);
      })
      .catch(() => setError("Failed to load task"))
      .finally(() => setLoading(false));
  }, [id]);

  const handleSubmit = async (formValues: NewTaskForm) => {
    if (!task) return;

    try {
      await updateTask(task.id, formValues);
      setModalMessage('Task updated successfully!');
      setShowModal(true);

      setTimeout(() => {
        setShowModal(false);
        navigate('/');
      }, 2000);
    } catch (err) {
      console.error("Failed to update task", err);
      showModalMessage("Failed to update task", "Error");
    }
  };

  const showModalMessage = (message: string, title = "Notice") => {
    setModalMessage(message);
    setShowModal(true);
    setModalTitle(title);
  };

  if (loading) return <p>Loading task data...</p>;
  if (error) return <p>{error}</p>;
  if (!task) return <p>Task not found</p>;

  const initialValues: NewTaskForm = {
    taskname: task.taskname,
    dueDate: task.dueDate || undefined,
    isCompleted: task.isCompleted,
    isArchived: false,
    categoryIds: task.categories.map(c => c.id),
    newCategoryNames: [],
  };

  return (
    <>
      {showModal && (
        <Modal show={showModal} onClose={() => setShowModal(false)} title={modalTitle}>
          <p>{modalMessage}</p>
        </Modal>
      )}

      <TaskForm
        initialValues={initialValues}
        onSubmit={handleSubmit}
        onCancel={() => navigate(`/`)}
        submitButtonLabel="Save Changes"
        showModalMessage={showModalMessage}
      />
    </>
  );
};

export default EditTaskForm;
