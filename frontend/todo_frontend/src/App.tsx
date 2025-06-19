

import { BrowserRouter, Route, Routes } from "react-router-dom"
import './App.css'
import AllTasksPage from './pages/AllTasksPage'
import TaskPage from './pages/TaskPage'
import NavBar from "./components/NavBar/NavBar"
import ArchivedTasksPage from "./pages/ArchivedTasksPage"
import AddNewTask from "./pages/AddNewTaskPage"
import EditTaskForm from "./pages/EditTaskPage"

function App() {
 

  return (
   <BrowserRouter>
   <NavBar />
    <Routes>
      <Route path="/" element={<AllTasksPage />} />
      <Route path="/tasks/:id" element={<TaskPage />} />
      <Route path="/tasks/new" element={<AddNewTask />} />
      <Route path="/tasks/:id/edit" element={<EditTaskForm />} />
      <Route path="/archived" element={<ArchivedTasksPage />} />
    </Routes>
   
   </BrowserRouter>
  )
}

export default App
