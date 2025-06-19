-- create tables

CREATE TABLE IF NOT EXISTS category (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    catname VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE  IF NOT EXISTS  tasks (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    taskname VARCHAR(255) NOT NULL,
    due_date DATE,
    is_completed boolean NOT NULL,
    is_archived boolean NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE  IF NOT EXISTS  tasks_categories(
    tasks_id INT,
    category_id INT,
    PRIMARY KEY (tasks_id, category_id),
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (tasks_id) REFERENCES tasks(id)
);