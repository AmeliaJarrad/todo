# Todo App

![GitHub Workflow](https://img.shields.io/github/actions/workflow/status/yourusername/yourrepo/ci.yml?branch=main)

## Demo & Snippets

- **Live demo:** [Insert your deployed app URL here]  
- **Screenshots:**  
  ![Screenshot 1](./screenshots/incomplete_tasks.png)  
  ![Screenshot 2](./screenshots/completed_tasks_collapsed.png)  

## Requirements / Purpose

This project is a full-stack todo application designed to manage tasks and categories efficiently. The backend API supports CRUD operations with soft-deletion for tasks (archiving). The frontend provides an intuitive UI for managing, filtering, and organizing tasks.

### MVP Features

- Add, edit, and archive tasks  
- Categorize tasks with dynamic categories  
- Filter tasks by category  
- Duplicate tasks easily  
- Responsive UI with accessible form validation  

### Tech Stack

- **Frontend:** React + TypeScript, SCSS Modules, FontAwesome for icons  
- **Backend:** Node.js + Express (assumed), PostgreSQL (or another DB)  
- **Why:** TypeScript for type safety, React for UI flexibility, REST API for modularity  

## Build Steps

```bash
# Clone repo
git clone https://github.com/yourusername/yourrepo.git
cd yourrepo

# Install dependencies
npm install

# Run backend server
## In the main folder
mvn spring-boot:run

# Run frontend
## In subfolders: frontend / todo_frontend 
npm start
```

### Visit http://localhost:3000 in your browser.

## Design Goals / Approach
- Separation of Concerns: API handles data and logic, frontend manages UI
- Soft Delete: Archiving tasks instead of deleting them permanently
- User-Friendly UI: Completed tasks toggle for less visual clutter
- Extensible Structure: Categories stored separately to allow scalable filtering

## Features
✅ Add, edit, archive/unarchive, and duplicate tasks

✅ Add, update, delete categories

✅ Filter tasks by category

✅ Collapsible "Completed Tasks" section with animated chevron

✅ Responsive layout with SCSS Modules

✅ Basic client-side form validation


## Automated Tests
 - Unit Tests: Service layer tested with JUnit and Mockito
 - Integration Tests: End-to-end API tests with SpringBootTest and RestAssured

Test Coverage:
- Task creation, update, archive, and retrieval
- Validation error handling and 404 cases
- Filtering tasks by categories
- Handling invalid input and non-existent entities

## Known Issues

⚠ Deleting a category doesn’t warn about tasks linked to it

⚠ Some layout quirks on very small screens

## Future Goals
✅ Add more automated tests (Jest + React Testing Library)

✅ Improve mobile UI and responsiveness

✅ Add user accounts and per-user task management

✅ Sync tasks with external calendars or reminders

✅ Improve keyboard accessibility and ARIA labels

## Change Logs
2025-07-06 - Initial MVP Complete

Backend API built with soft delete functionality

React frontend implemented with form validation

Added category filtering and duplicate task support

Styled with SCSS Modules and FontAwesome icons

2025-07-14 - Added full end-to-end tests for Task API including update and archive
2025-07-14 - Improved error handling and validation feedback

## What did you struggle with?
Keeping local task state in sync with backend updates

Correctly archiving and filtering tasks while avoiding full-page reloads

Getting .gitignore and node_modules to behave with Git

Toggling visibility of completed tasks in a clean and intuitive way

## Licensing Details
This project is licensed under the MIT License. See the LICENSE file for more information.

## Further Details
This is a full-stack project built from scratch for learning and demonstration purposes.
Can be extended to integrate with calendar tools or used as a base for more complex task apps.
