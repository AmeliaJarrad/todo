-- Insert categories if they don't already exist
INSERT INTO category (catname)
SELECT * FROM (SELECT 'Work') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM category WHERE catname = 'Work');

INSERT INTO category (catname)
SELECT * FROM (SELECT 'Personal') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM category WHERE catname = 'Personal');

INSERT INTO category (catname)
SELECT * FROM (SELECT 'Learning') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM category WHERE catname = 'Learning');


-- Insert tasks only if taskname doesn't already exist
INSERT INTO tasks (taskname, due_date, created_at, is_completed, is_archived)
SELECT * FROM (
  SELECT 'Finish project report', '2025-06-20', '2025-06-10', false, false
) AS tmp
WHERE NOT EXISTS (
  SELECT 1 FROM tasks WHERE taskname = 'Finish project report'
);

INSERT INTO tasks (taskname, due_date, created_at, is_completed, is_archived)
SELECT * FROM (
  SELECT 'Buy groceries', '2025-06-13', '2025-06-11', false, false
) AS tmp
WHERE NOT EXISTS (
  SELECT 1 FROM tasks WHERE taskname = 'Buy groceries'
);

INSERT INTO tasks (taskname, due_date, created_at, is_completed, is_archived)
SELECT * FROM (
  SELECT 'Watch Spring Boot tutorial', '2025-06-15', '2025-06-12', false, false
) AS tmp
WHERE NOT EXISTS (
  SELECT 1 FROM tasks WHERE taskname = 'Watch Spring Boot tutorial'
);


-- Insert task-category mappings if not already present

-- Link tasks to categories dynamically by name
INSERT INTO tasks_categories (tasks_id, category_id)
SELECT t.id, c.id FROM tasks t, category c
WHERE t.taskname = 'Finish project report' AND c.catname = 'Work'
  AND NOT EXISTS (
    SELECT 1 FROM tasks_categories WHERE tasks_id = t.id AND category_id = c.id
);

INSERT INTO tasks_categories (tasks_id, category_id)
SELECT t.id, c.id FROM tasks t, category c
WHERE t.taskname = 'Buy groceries' AND c.catname = 'Personal'
  AND NOT EXISTS (
    SELECT 1 FROM tasks_categories WHERE tasks_id = t.id AND category_id = c.id
);

INSERT INTO tasks_categories (tasks_id, category_id)
SELECT t.id, c.id FROM tasks t, category c
WHERE t.taskname = 'Watch Spring Boot tutorial' AND c.catname = 'Learning'
  AND NOT EXISTS (
    SELECT 1 FROM tasks_categories WHERE tasks_id = t.id AND category_id = c.id
);


--these all needed to be modified for deploying safely for production and the remote ec2 sql database