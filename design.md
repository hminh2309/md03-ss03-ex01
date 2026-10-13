# Tài Liệu Thiết Kế RESTful API - Quản Lý Task & User

## Quy tắc thiết kế:
- Sử dụng danh từ số nhiều (`/users`, `/tasks`).
- Đúng phương thức HTTP (GET, POST, PUT, PATCH, DELETE).
- Lọc dữ liệu qua Query Parameter.
- Quan hệ phụ thuộc được biểu diễn qua Sub-resource.

---

### 1. Lấy toàn bộ danh sách công việc, người dùng
- **Lấy danh sách người dùng**:
    - `GET /api/v1/users`
    - Response: `200 OK` (Danh sách User)
- **Lấy danh sách công việc**:
    - `GET /api/v1/tasks`
    - Response: `200 OK` (Danh sách Task)

---

### 2. Tạo mới công việc, tạo mới người dùng
- **Tạo mới người dùng**:
    - `POST /api/v1/users`
    - Body:
      ```json
      {
        "name": "Nguyen Van A",
        "email": "a@example.com",
        "role": "USER"
      }
      ```
    - Response: `201 Created`
- **Tạo mới công việc**:
    - `POST /api/v1/tasks`
    - Body:
      ```json
      {
        "title": "Học Spring Boot",
        "description": "Thực hành thiết kế RESTful",
        "priority": "high",
        "status": "pending",
        "userId": 1
      }
      ```
    - Response: `201 Created`

---

### 3. Cập nhật trạng thái một công việc, cập nhật vai trò của người dùng
- **Cập nhật vai trò của người dùng**:
    - `PATCH /api/v1/users/{id}/role`
    - Body:
      ```json
      {
        "role": "ADMIN"
      }
      ```
    - Response: `200 OK`
- **Cập nhật trạng thái một công việc**:
    - `PATCH /api/v1/tasks/{id}/status`
    - Body:
      ```json
      {
        "status": "completed"
      }
      ```
    - Response: `200 OK`

---

### 4. Xóa một công việc, xóa một người dùng khỏi hệ thống
- **Xóa người dùng**:
    - `DELETE /api/v1/users/{id}`
    - Response: `204 No Content`
- **Xóa công việc**:
    - `DELETE /api/v1/tasks/{id}`
    - Response: `204 No Content`

---

### 5. Tìm các công việc có mức độ ưu tiên là "high"
- `GET /api/v1/tasks?priority=high`
- Response: `200 OK` (Danh sách các Task có priority = high)

---

### 6. Tìm các công việc có độ ưu tiên là "high" và được giao cho người dùng với id là 1
- `GET /api/v1/tasks?priority=high&userId=1`
- Response: `200 OK` (Danh sách các Task thỏa mãn cả 2 điều kiện)

---

### 7. Liệt kê toàn bộ công việc của 1 người dùng (Sub-resource)
- `GET /api/v1/users/{userId}/tasks`
- Response: `200 OK` (Danh sách công việc thuộc về user có id chỉ định)

---

### 8. Gắn công việc cho người dùng
- `PUT /api/v1/tasks/{taskId}/assignee`
- Body:
  ```json
  {
    "userId": 2
  }