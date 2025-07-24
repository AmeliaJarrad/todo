-- Insert categories if not already present
INSERT INTO category (catname)
SELECT * FROM (SELECT 'Work') AS tmp
WHERE NOT EXISTS (
    SELECT 1 FROM category WHERE catname = 'Work'
);

INSERT INTO category (catname)
SELECT * FROM (SELECT 'Personal') AS tmp
WHERE NOT EXISTS (
    SELECT 1 FROM category WHERE catname = 'Personal'
);

INSERT INTO category (catname)
SELECT * FROM (SELECT 'Learning') AS tmp
WHERE NOT EXISTS (
    SELECT 1 FROM category WHERE catname = 'Learning'
);

-- Insert tasks if not already present
INSERT INTO tasks (id, taskname, due_date, created_at, is_completed, is_archived)
SELECT * FROM (
    SELECT 1, 'Finish project report', '2025-06-20', '2025-06-10', false, false
) AS tmp
WHERE NOT EXISTS (
    SELECT 1 FROM tasks WHERE id = 1
);

INSERT INTO tasks (id, taskname, due_date, created_at, is_completed, is_archived)
SELECT * FROM (
    SELECT 2, 'Buy groceries', '2025-06-13', '2025-06-11', false, false
) AS tmp
WHERE NOT EXISTS (
    SELECT 1 FROM tasks WHERE id = 2
);

INSERT INTO tasks (id, taskname, due_date, created_at, is_completed, is_archived)
SELECT * FROM (
    SELECT 3, 'Watch Spring Boot tutorial', '2025-06-15', '2025-06-12', false, false
) AS tmp
WHERE NOT EXISTS (
    SELECT 1 FROM tasks WHERE id = 3
);

-- Insert task-category mappings if not already present
INSERT INTO tasks_categories (tasks_id, category_id)
SELECT * FROM (SELECT 1, 1) AS tmp
WHERE NOT EXISTS (
    SELECT 1 FROM tasks_categories WHERE tasks_id = 1 AND category_id = 1
);

INSERT INTO tasks_categories (tasks_id, category_id)
SELECT * FROM (SELECT 2, 2) AS tmp
WHERE NOT EXISTS (
    SELECT 1 FROM tasks_categories WHERE tasks_id = 2 AND category_id = 2
);

INSERT INTO tasks_categories (tasks_id, category_id)
SELECT * FROM (SELECT 3, 3) AS tmp
WHERE NOT EXISTS (
    SELECT 1 FROM tasks_categories WHERE tasks_id = 3 AND category_id = 3
);

--these all needed to be modified for deploying safely for production and the remote ec2 sql database