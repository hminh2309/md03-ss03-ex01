package com.example.ss02;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controllers {
}

// USER CONTROLLER
@RestController
@RequestMapping("/api/v1/users")
class UserController {

    public static List<User> listUser = new ArrayList<>();
    private static long currentUserId = 1;

    // Yêu cầu 1: Lấy toàn bộ danh sách người dùng
    @GetMapping
    public List<User> getAllUsers() {
        return listUser;
    }

    // Yêu cầu 2: Tạo mới người dùng
    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setId(currentUserId++);
        listUser.add(user);
        return user;
    }

    // Yêu cầu 3: Cập nhật vai trò của người dùng
    @PatchMapping("/{id}/role")
    public User updateRole(@PathVariable Long id, @RequestBody Map<String, String> body) {
        for (User u : listUser) {
            if (u.getId().equals(id)) {
                u.setRole(body.get("role"));
                return u;
            }
        }
        return null;
    }

    // Yêu cầu 4: Xóa một người dùng khỏi hệ thống
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        for (int i = 0; i < listUser.size(); i++) {
            if (listUser.get(i).getId().equals(id)) {
                listUser.remove(i);
                return "Xóa thành công";
            }
        }
        return "Không tìm thấy user";
    }

    // Yêu cầu 7: Liệt kê toàn bộ công việc của 1 người dùng
    @GetMapping("/{userId}/tasks")
    public List<Task> getTasksByUser(@PathVariable Long userId) {
        List<Task> result = new ArrayList<>();
        for (Task t : TaskController.listTask) {
            if (t.getUserId() != null && t.getUserId().equals(userId)) {
                result.add(t);
            }
        }
        return result;
    }
}
// TASK CONTROLLER
@RestController
@RequestMapping("/api/v1/tasks")
class TaskController {

    public static List<Task> listTask = new ArrayList<>();
    private static long currentTaskId = 1;

    // Yêu cầu 1: Lấy toàn bộ danh sách công việc
    // Yêu cầu 5: Tìm các công việc có mức độ ưu tiên là "high" (dùng param ?priority=high)
    // Yêu cầu 6: Tìm các công việc có độ ưu tiên là "high" và giao cho user id 1 (dùng param ?priority=high&userId=1)
    @GetMapping
    public List<Task> getTasks(@RequestParam(required = false) String priority,
                               @RequestParam(required = false) Long userId) {
        List<Task> result = new ArrayList<>();
        for (Task t : listTask) {
            boolean matchPriority = (priority == null || priority.equalsIgnoreCase(t.getPriority()));
            boolean matchUser = (userId == null || (t.getUserId() != null && t.getUserId().equals(userId)));

            if (matchPriority && matchUser) {
                result.add(t);
            }
        }
        return result;
    }

    // Yêu cầu 2: Tạo mới công việc
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        task.setId(currentTaskId++);
        listTask.add(task);
        return task;
    }

    // Yêu cầu 3: Cập nhật trạng thái một công việc
    @PatchMapping("/{id}/status")
    public Task updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        for (Task t : listTask) {
            if (t.getId().equals(id)) {
                t.setStatus(body.get("status"));
                return t;
            }
        }
        return null;
    }

    // Yêu cầu 4: Xóa một công việc
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {
        for (int i = 0; i < listTask.size(); i++) {
            if (listTask.get(i).getId().equals(id)) {
                listTask.remove(i);
                return "Xóa công việc thành công";
            }
        }
        return "Không tìm thấy task";
    }

    // Yêu cầu 8: Gắn công việc cho người dùng
    @PutMapping("/{taskId}/assignee")
    public Task assignTaskToUser(@PathVariable Long taskId, @RequestBody Map<String, Long> body) {
        for (Task t : listTask) {
            if (t.getId().equals(taskId)) {
                t.setUserId(body.get("userId"));
                return t;
            }
        }
        return null;
    }
}