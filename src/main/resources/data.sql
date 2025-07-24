-- Insert categories if they don't already exist
INSERT INTO category (catname)
SELECT 'Work' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM category WHERE catname = 'Work');

INSERT INTO category (catname)
SELECT 'Personal' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM category WHERE catname = 'Personal');

INSERT INTO category (catname)
SELECT 'Learning' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM category WHERE catname = 'Learning');


-- Insert tasks only if taskname doesn't already exist
INSERT INTO tasks (taskname, due_date, created_at, is_completed, is_archived)
SELECT 'Finish project report' AS taskname,
       '2025-06-20' AS due_date,
       '2025-06-10' AS created_at,
       false AS is_completed,
       false AS is_archived
FROM DUAL
WHERE NOT EXISTS (
  SELECT 1 FROM tasks WHERE taskname = 'Finish project report'
);

INSERT INTO tasks (taskname, due_date, created_at, is_completed, is_archived)
SELECT 'Buy groceries' AS taskname,
       '2025-06-13' AS due_date,
       '2025-06-11' AS created_at,
       false AS is_completed,
       false AS is_archived
FROM DUAL
WHERE NOT EXISTS (
  SELECT 1 FROM tasks WHERE taskname = 'Buy groceries'
);

INSERT INTO tasks (taskname, due_date, created_at, is_completed, is_archived)
SELECT 'Watch Spring Boot tutorial' AS taskname,
       '2025-06-15' AS due_date,
       '2025-06-12' AS created_at,
       false AS is_completed,
       false AS is_archived
FROM DUAL
WHERE NOT EXISTS (
  SELECT 1 FROM tasks WHERE taskname = 'Watch Spring Boot tutorial'
);


-- Insert task-category mappings if not already present
INSERT INTO tasks_categories (tasks_id, category_id)
SELECT t.id, c.id
FROM tasks t, category c
WHERE t.taskname = 'Finish project report' AND c.catname = 'Work'
  AND NOT EXISTS (
    SELECT 1 FROM tasks_categories WHERE tasks_id = t.id AND category_id = c.id
);

INSERT INTO tasks_categories (tasks_id, category_id)
SELECT t.id, c.id
FROM tasks t, category c
WHERE t.taskname = 'Buy groceries' AND c.catname = 'Personal'
  AND NOT EXISTS (
    SELECT 1 FROM tasks_categories WHERE tasks_id = t.id AND category_id = c.id
);

INSERT INTO tasks_categories (tasks_id, category_id)
SELECT t.id, c.id
FROM tasks t, category c
WHERE t.taskname = 'Watch Spring Boot tutorial' AND c.catname = 'Learning'
  AND NOT EXISTS (
    SELECT 1 FROM tasks_categories WHERE tasks_id = t.id AND category_id = c.id
);

-- End of data.sql


--these all needed to be modified for deploying safely for production and the remote ec2 sql database