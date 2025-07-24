import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router"
import { duplicateTask, getTaskById, updateTask, type Task } from "../services/tasks";
import TaskCard from "../components/TaskCard/TaskCard";
import Modal from "../components/Modal/Modal";



const TaskPage = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    

    const [task, setTask] = useState<Task | null>(null);
    const [showModal, setShowModal] = useState(false);
    const [modalMessage, setModalMessage] = useState("");

    // New states for duplicate modal input
  const [showDuplicateModal, setShowDuplicateModal] = useState(false);
  const [newDueDate, setNewDueDate] = useState(() => {
    const today = new Date().toISOString().split("T")[0];
    return today;
  });
  const [duplicateError, setDuplicateError] = useState("");

    useEffect(() => {
        if (id) getTaskById(id).then(setTask).catch(console.log);
    }, [id]);

      const showModalMessage = (message: string) => {
        setModalMessage(message);
        setShowModal(true);
      };
    console.log(task);

    if(!task) {
        return <p>Loading task...</p>;
    }


  // archive (soft-delete)
  const handleToggleArchive = async (id: number, isArchived: boolean) => {
    try {
      await updateTask(id, { isArchived });
      showModalMessage(isArchived ? "Task archived" : "Task unarchived");
      navigate("/");
    } catch (err) {
      console.error("Failed to archive task", err);
      showModalMessage("Failed to archive task");
    }
  };

  // complete toggle
  const handleToggleComplete = async (id: number, isCompleted: boolean) => {
    try {
      await updateTask(id, { isCompleted });
      // Update task locally for instant UI feedback
      setTask(prev => prev ? { ...prev, isCompleted } : prev);
    } catch (err) {
      console.error("Failed to mark task complete", err);
      showModalMessage("Failed to mark task complete");
    }
  };

//issues with task duplication addressed by fixing the date prompt, tasks with
//dates in past couldn't be duplicated 

  const handleDuplicate = async () => {
    setDuplicateError("");
    setShowDuplicateModal(true);
    };


    const confirmDuplicate = async () => {
    const today = new Date().toISOString().split("T")[0];
    if (newDueDate < today) {
      setDuplicateError("Due date must be today or in the future.");
      return;
    }


    try {
      await duplicateTask({...task, dueDate: newDueDate});
      setShowDuplicateModal(false);
       showModalMessage("Task duplicated");
      navigate("/"); // Redirect to task list or refresh
    } catch (err) {
      console.error("Failed to duplicate task", err);
       setShowDuplicateModal(false);
      showModalMessage("Failed to duplicate task");
    }
  };


  return (
   <>
      {showModal && (
        <Modal show={showModal} onClose={() => setShowModal(false)} title="Notice">
          <p>{modalMessage}</p>
        </Modal>
      )}

        {/* Duplicate date modal */}
      {showDuplicateModal && (
          <Modal
        show={showDuplicateModal}
        onClose={() => setShowDuplicateModal(false)}
        title="Duplicate Task"
        onConfirm={confirmDuplicate}
        onCancel={() => setShowDuplicateModal(false)}
        confirmLabel="Confirm"
        cancelLabel="Cancel"
      >
        <label style={{ display: "block", marginBottom: "1rem" }}>
          New Due Date:
          <input
            type="date"
            value={newDueDate}
            min={new Date().toISOString().split("T")[0]}
            onChange={(e) => setNewDueDate(e.target.value)}
             style={{ marginLeft: "0.5rem" }}
          />
        </label>
        {duplicateError && <p style={{ color: "red", marginTop: "1rem" }}>{duplicateError}</p>}
      </Modal>

      )}

      <div style={{ maxWidth: "600px", margin: "0 auto" }}>
        <TaskCard
          task={task}
          onToggleArchive={handleToggleArchive}
          onToggleComplete={handleToggleComplete}
          onDuplicate={handleDuplicate}
        />
      </div>
    </>
   
  )
}

export default TaskPage;