-- Insert categories
INSERT INTO category (catname) VALUES 
('Work'),
('Personal'),
('Learning');

-- Insert tasks
INSERT INTO tasks (taskname, dueDate, createdDate, isCompleted, isArchived) VALUES 
('Finish project report', '2025-06-20', '2025-06-10', false, false),
('Buy groceries', '2025-06-13', '2025-06-11', false, false),
('Watch Spring Boot tutorial', '2025-06-15', '2025-06-12', false, false);

-- Link tasks to categories
INSERT INTO tasks_categories (tasks_id, category_id) VALUES 
(1, 1), -- Task 1 -> Work
(2, 2), -- Task 2 -> Personal
(3, 3); -- Task 3 -> Learning
