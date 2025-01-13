# Task Manager Application 🚀

## Overview.. 🚀

The Task Manager Application is a feature-rich project designed to streamline task management for employees and administrators. Built with Spring Boot, this application allows seamless task assignment, status tracking, and communication between users via a nested comments system.

Purpose: Simplify task collaboration by providing a secure, efficient, and user-friendly backend.

---

## 🌟 Features

- **User Roles**: Supports both `Admin` and `Employee` roles.
- **Task Management**:
  - Add, update, delete, and search tasks.
  - Assign tasks to employees.
- **Nested Comments**: Admins and employees can comment and reply to task-specific discussions.
- **Role-Based Authentication**: Ensures secure access to endpoints based on user roles.
- **Current User Utility**: Simplifies retrieval of the logged-in user's information.

---

## 🛠️ Technologies Used

- **Backend**: Spring Boot 3
- **Database**: MySQL
- **Authentication**: Spring Security
- **Data Transfer**: REST APIs

---

## 🚀 API Endpoints

### 1. **Admin Endpoints** (`/admin`)

#### User Management
- **Add Admin**:
  - `POST /admin/addAdmin`
  - Request Body: `UserModel`

#### Task Management
- **Get All Tasks**:
  - `GET /admin/allTask`
- **Add Task**:
  - `POST /admin/addTask`
  - Request Body: `TaskDto`
- **Delete Task**:
  - `DELETE /admin/task/{id}`
  - Path Variable: `id` (Task ID)
- **Get Task by ID**:
  - `GET /admin/task/{id}`
  - Path Variable: `id` (Task ID)
- **Update Task**:
  - `PUT /admin/updateTask/{id}`
  - Request Body: `TaskDto`
  - Path Variable: `id` (Task ID)
- **Search Tasks**:
  - `GET /admin/searchTask?keywords={keywords}&id={id}`
  - Query Params: `keywords` (Optional), `id` (Optional)
- **Get Tasks by Employee**:
  - `GET /admin/task/employee/{employeeId}`
  - Path Variable: `employeeId` (Employee ID)
- **Get Admin’s Tasks**:
  - `GET /admin/adminTask`

---

### 2. **Employee Endpoints** (`/employee`)

#### Task Management
- **Get All Tasks Assigned to Employee**:
  - `GET /employee/tasks`
- **Update Task**:
  - `PUT /employee/updateTask/{id}`
  - Request Body: `TaskDto`
  - Path Variable: `id` (Task ID)

---

### 3. **Comment Endpoints** (`/comment`)

#### Task-Specific Comments
- **Add Comment**:
  - `POST /comment/task/{taskId}`
  - Request Body: `CommentDto`
  - Path Variable: `taskId` (Task ID)
- **Get Comments for Task**:
  - `GET /comment/task/{taskId}`
  - Path Variable: `taskId` (Task ID)

---

### 4. **User Endpoints**

#### Authentication & User Management
- **Add User**:
  - `POST /addUser`
  - Request Body: `UserModel`
- **Login**:
  - `POST /login`
  - Request Body: `UserModel`

---

## 🎯 Models Overview

### **Task Model**
- `id`: Unique task identifier.
- `title`: Title of the task.
- `description`: Details about the task.
- `dueDate`: Deadline for the task.
- `priority`: Task priority (`HIGH`, `MEDIUM`, `LOW`).
- `taskStatus`: Status of the task.
- `user`: Assigned user (Many-to-One relationship).

### **Comment Model**
- `id`: Unique comment identifier.
- `content`: The comment text.
- `user`: The user who made the comment.
- `task`: Associated task.
- `parentComment`: For nested replies (self-referencing Many-to-One relationship).

### **User Model**
- `id`: Unique user identifier.
- `email`: User’s email (used for login).
- `password`: Password (securely hashed).
- `userRole`: Role of the user (`ADMIN`, `EMPLOYEE`).

---

## 🏗️ Folder Structure

```plaintext
src/
├── main/
│   ├── java/
│   │   ├── com.example.TaskManager/
│   │   │   ├── Controller/  # REST API Controllers
│   │   │   ├── Enum/        # Enums (e.g., UserRole, TaskPriority)
│   │   │   ├── Models/      # Entity Classes (e.g., Task, Comment, UserModel)
│   │   │   ├── Repo/        # Repositories
│   │   │   ├── Services/    # Business Logic Services
│   │   │   ├── Utility/     # Helper Classes (e.g., CurrentUser)
├── resources/
│   ├── application.properties  # Configuration
└── ...
```

---

## 🚦 How to Run the Project

1. Clone the repository:
   ```bash
   git clone <your-repo-url>
   ```
2. Navigate to the project directory:
   ```bash
   cd TaskManager
   ```
3. Configure your database in `application.properties`.
4. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```

---

## 🌐 Future Enhancements 

✨ Add email notifications for task updates.

✨ Integrate file attachments with comments.

✨ Create a frontend using Angular or React.

---

## 📚 Documentation

- **Authentication**:
  - Uses Spring Security with role-based access.
  - Admins and Employees have distinct permissions.
- **Nested Comments**:
  - Supports replies to comments via `parentComment` in the `Comment` model.
- **Task Search**:
  - Supports search by keywords or task ID.

---

## 🤝 Contributing

Feel free to fork this project, create new features, or optimize existing code! Submit a pull request, and let’s collaborate! 💻

---

## 📜 License

This project is licensed under the MIT License.

---

## 💬 Feedback

Have questions or suggestions? Open an issue or contact me directly!

---

Thank you for checking out the **Task Manager Project**! 🌟

