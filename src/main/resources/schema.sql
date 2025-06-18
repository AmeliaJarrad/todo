-- create tables

CREATE TABLE IF NOT EXISTS category (
      categoryID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
      catname VARCHAR(50) NOT NULL UNIQUE
);


CREATE TABLE  IF NOT EXISTS  tasks (
      id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
      taskname VARCHAR(255) NOT NULL,
      dueDate DATE NOT NULL,
      createdDate DATE NOT NULL,
      isCompleted boolean NOT NULL,
      isArchived boolean NOT NULL
);

CREATE TABLE  IF NOT EXISTS  tasks_categories(
    tasks_id INT,
    category_id INT,
    PRIMARY KEY (tasks_id, category_id),
    FOREIGN KEY (category_id) REFERENCES category(categoryID),
    FOREIGN KEY (tasks_id) REFERENCES tasks(id)

);