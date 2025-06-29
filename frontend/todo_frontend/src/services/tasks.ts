export interface Task{
    id: number;
    createdAt: string;
    updatedAt: string;
    taskname: string;
    dueDate: string;
    isCompleted: boolean;
    isArchived: boolean;
    categories: string [];

}

export interface Category{
    id: number;
    createdAt: string;
    updatedAt: string;
    catname: string;
}

//now will take new cats and existing cats 
export interface NewTaskForm {
    taskname: string;
    dueDate?: string;         
    isCompleted: boolean;    
    isArchived: boolean;     
    categoryIds?: number[];
    newCategoryNames?: string[];
}

//for updates
export type UpdateTaskForm = Partial<NewTaskForm>;

export interface CreateTaskDTO {
  taskname: string;
  dueDate?: string;
  isCompleted?: boolean;
  categoryNames?: string[];
}


export const getAllTasks = async (): Promise<Task[]> => {
    const response = await fetch("http://localhost:8080/tasks");
      if (!response.ok) {
    const errorText = await response.text();
    console.error("Failed to fetch tasks:", errorText);
    throw new Error(`HTTP ${response.status} - ${errorText}`);
  }
    const tasks = await response.json();
    return tasks;
}

export const getTaskById = async (id: string): Promise<Task> => {
    const response = await fetch("http://localhost:8080/tasks/" + id);
    if (!response.ok) {
        throw new Error("Could not fetch data");
    }
    return await response.json();
}   

export const getArchivedTasks = async (): Promise<Task[]> => {
  const response = await fetch("http://localhost:8080/tasks/archived");
  if (!response.ok) {
    throw new Error("Could not fetch archived tasks");
  }
  return await response.json();
}  

export const createCategory = async (categoryName: string) => {
  await fetch("http://localhost:8080/categories", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ catname: categoryName }),
  });
};

export const createTask = async (task: NewTaskForm)  => {
        console.log("sending task to backend", task)
    const response = await fetch("http://localhost:8080/tasks", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(task),
    });

    if(!response.ok) {
        const errorText = await response.text();
        console.log("Backend error response", errorText);
        throw new Error(`Failed to create task: ${response.status} ${response.statusText}`);
    }
};

export const updateTask = async (id: number, updates: UpdateTaskForm) => {
     console.log("Sending update to backend for task ID:", id);
    console.log("Update payload:", JSON.stringify(updates, null, 2));
  const response = await fetch(`http://localhost:8080/tasks/${id}`, {
    method: "PATCH",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(updates),
  });

  if (!response.ok) {
    const error = await response.text();
    console.error("Backend update error:", error);
    throw new Error(`Failed to update task: ${response.status}`);
  }
};

export const duplicateTask = async (originalTask: Task) => {
  const copy = {
    taskname: originalTask.taskname + " (copy)",
    dueDate: originalTask.dueDate,
    isCompleted: false,
    isArchived: false,
    categoryIds: [],
    newCategoryNames: [],         
     
  };

  await fetch("http://localhost:8080/tasks", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(copy),
  });


};

export const archiveTask = async (id: number): Promise<void> => {
  const response = await fetch(`http://localhost:8080/tasks/${id}`, {
    method: "DELETE",
  });

  if (!response.ok) {
    const error = await response.text();
    throw new Error(`Failed to archive task: ${error}`);
  }
};


//https://stackoverflow.com/questions/47643692/how-to-make-http-post-request-from-front-end-to-spring