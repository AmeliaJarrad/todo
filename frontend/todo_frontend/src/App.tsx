

import { BrowserRouter, Route, Routes } from "react-router-dom"
import './App.css'
import AllTasksPage from './pages/AllTasksPage'
import TaskPage from './pages/TaskPage'

function App() {
 

  return (
   <BrowserRouter>
    <Routes>
      <Route path="/" element={<AllTasksPage />} />
       <Route path="/tasks/:id" element={<TaskPage />} />
    </Routes>
   
   </BrowserRouter>
  )
}

export default App
