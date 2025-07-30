const BASE_URL = import.meta.env.VITE_API_BASE_URL;

export interface Task {
  id: number;
  createdAt: string;
  updatedAt: string;
  taskname: string;
  dueDate: string;
  isCompleted: boolean;
  archived: boolean;
  categories: {
    id: number;
    catname: string;
  }[];
}

export interface Category {
  id: number;
  createdAt: string;
  updatedAt: string;
  catname: string;
}

export interface NewTaskForm {
  taskname: string;
  dueDate?: string;
  isCompleted: boolean;
  isArchived: boolean;
  categoryIds?: number[];
  newCategoryNames?: string[];
}

export type UpdateTaskForm = Partial<NewTaskForm>;

export interface CreateTaskDTO {
  taskname: string;
  dueDate?: string;
  isCompleted?: boolean;
  categoryNames?: string[];
}

// Fetch all tasks
export const getAllTasks = async (): Promise<Task[]> => {
  const response = await fetch(`${BASE_URL}/tasks`);
  if (!response.ok) {
    const errorText = await response.text();
    console.error("Failed to fetch tasks:", errorText);
    throw new Error(`HTTP ${response.status} - ${errorText}`);
  }
  return await response.json();
};

export const getTaskById = async (id: string): Promise<Task> => {
  const response = await fetch(`${BASE_URL}/tasks/${id}`);
  if (!response.ok) {
    throw new Error("Could not fetch data");
  }
  return await response.json();
};

export const getArchivedTasks = async (): Promise<Task[]> => {
  const response = await fetch(`${BASE_URL}/tasks/archived`);
  if (!response.ok) {
    throw new Error("Could not fetch archived tasks");
  }
  return await response.json();
};

export const createCategory = async (categoryName: string) => {
  await fetch(`${BASE_URL}/categories`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ catname: categoryName }),
  });
};

export const createTask = async (task: NewTaskForm) => {
  console.log("sending task to backend", task);
  const response = await fetch(`${BASE_URL}/tasks`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(task),
  });

  if (!response.ok) {
    const errorText = await response.text();
    console.log("Backend error response", errorText);
    throw new Error(`Failed to create task: ${response.status} ${response.statusText}`);
  }
};

export const updateTask = async (id: number, updates: UpdateTaskForm) => {
  console.log("Sending update to backend for task ID:", id);
  console.log("Update payload:", JSON.stringify(updates, null, 2));
  const response = await fetch(`${BASE_URL}/tasks/${id}`, {
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
  // Re-fetch full task with categories
  const fullTask = await getTaskById(originalTask.id.toString());

  const copy = {
    taskname: fullTask.taskname + " (copy)",
    dueDate: fullTask.dueDate,
    isCompleted: false,
    isArchived: false,
    categoryNames: fullTask.categories.map(cat => cat.catname),
  };

  console.log("Sending duplicate task payload:", copy);

  const response = await fetch(`${BASE_URL}/tasks`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(copy),
  });

  if (!response.ok) {
    const errorText = await response.text();
    console.error("Failed to duplicate task:", errorText);
    throw new Error(`Failed to duplicate task: ${response.status} ${response.statusText}`);
  }
};

export const archiveTask = async (id: number): Promise<void> => {
  const response = await fetch(`${BASE_URL}/tasks/${id}`, {
    method: "DELETE",
  });

  if (!response.ok) {
    const error = await response.text();
    throw new Error(`Failed to archive task: ${error}`);
  }
};
