package org.de013.tasktrackercli.service;

import org.de013.tasktrackercli.model.Task;
import org.de013.tasktrackercli.repository.TaskRepository;
import org.de013.tasktrackercli.util.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class TaskServiceImplTest {
    private TaskService taskService;
    private FakeTaskRepository fakeTaskRepository;

    @BeforeEach
    void setUp() {
        fakeTaskRepository = new FakeTaskRepository();
        taskService = new TaskServiceImpl(fakeTaskRepository);
    }

    // ===== TEST ADD TASK =====
    @Test
    void testAddTask_ShouldAddNewTaskToRepository() {
        // ARRANGE (Chuẩn bị)
        String desciption = "Buy groceries";
        // ACT (Thực hiện)
        taskService.add(desciption);
        // ASSERT (Kiểm tra kết quả)
        List<Task> tasks = taskService.getTasks();
        assertEquals(1, tasks.size());
        assertEquals(desciption, tasks.get(0).getDescription());
        assertEquals(TaskStatus.TODO, tasks.get(0).getStatus());
    }

    @Test
    void testAddTask_ShouldIncrementNextId() {
        // ARRANGE
        String desciption1 = "Buy groceries 1";
        String desciption2 = "Buy groceries 2";
        // ACT
        taskService.add(desciption1);
        taskService.add(desciption2);
        // ASSERT
        List<Task> tasks = taskService.getTasks();
        assertEquals(2, tasks.size());
        assertEquals(desciption1, tasks.get(0).getDescription());
        assertEquals(desciption2, tasks.get(1).getDescription());
        assertEquals(TaskStatus.TODO, tasks.get(0).getStatus());
    }

    // ===== TEST UPDATE TASK =====
    
    @Test
    void testUpdateTask_ShouldUpdateDescription() {
        // ARRANGE: Add task trước
        taskService.add("Old description");
        List<Task> tasks = taskService.getTasks();
        int taskId = tasks.get(0).getId();
        
        // ACT: Update task
        String newDescription = "New description";
        taskService.update(taskId, newDescription);
        
        // ASSERT: Check description đã thay đổi
        tasks = taskService.getTasks();
        assertEquals(1, tasks.size());
        assertEquals(newDescription, tasks.get(0).getDescription());
    }
    
    @Test
    void testUpdateTask_ShouldUpdateUpdatedAtTimestamp() {
        // ARRANGE
        taskService.add("Original task");
        List<Task> tasks = taskService.getTasks();
        int taskId = tasks.get(0).getId();
        
        // ACT
        taskService.update(taskId, "Updated task");
        
        // ASSERT: updatedAt should be set (not null)
        tasks = taskService.getTasks();
        assertNotNull(tasks.get(0).getUpdatedAt());
    }
    
    // ===== TEST DELETE TASK =====
    
    @Test
    void testDeleteTask_ShouldRemoveTaskFromList() {
        // ARRANGE: Add 3 tasks
        taskService.add("Task 1");
        taskService.add("Task 2");
        taskService.add("Task 3");
        
        List<Task> tasks = taskService.getTasks();
        int taskIdToDelete = tasks.get(1).getId(); // Delete task 2
        
        // ACT
        taskService.delete(taskIdToDelete);
        
        // ASSERT: Chỉ còn 2 tasks
        tasks = taskService.getTasks();
        assertEquals(2, tasks.size());
        assertEquals("Task 1", tasks.get(0).getDescription());
        assertEquals("Task 3", tasks.get(1).getDescription());
    }
    
    @Test
    void testDeleteTask_ShouldDoNothingIfIdNotFound() {
        // ARRANGE
        taskService.add("Task 1");
        
        // ACT: Delete với ID không tồn tại
        taskService.delete(999);
        
        // ASSERT: Task vẫn còn nguyên
        List<Task> tasks = taskService.getTasks();
        assertEquals(1, tasks.size());
    }
    
    // ===== TEST MARK IN-PROGRESS =====
    
    @Test
    void testMarkInProgress_ShouldChangeStatusToInProgress() {
        // ARRANGE
        taskService.add("Task to start");
        List<Task> tasks = taskService.getTasks();
        int taskId = tasks.get(0).getId();
        
        // ACT
        taskService.markInProgress(taskId);
        
        // ASSERT
        tasks = taskService.getTasks();
        assertEquals(TaskStatus.IN_PROGRESS, tasks.get(0).getStatus());
    }
    
    // ===== TEST MARK DONE =====
    
    @Test
    void testMarkDone_ShouldChangeStatusToDone() {
        // ARRANGE
        taskService.add("Task to complete");
        List<Task> tasks = taskService.getTasks();
        int taskId = tasks.get(0).getId();
        
        // ACT
        taskService.markDone(taskId);
        
        // ASSERT
        tasks = taskService.getTasks();
        assertEquals(TaskStatus.DONE, tasks.get(0).getStatus());
    }
    
    // ===== TEST LIST BY STATUS =====
    
    @Test
    void testGetTasksByStatus_ShouldReturnOnlyTodoTasks() {
        // ARRANGE: Add 3 tasks với status khác nhau
        taskService.add("Todo task");
        taskService.add("In progress task");
        taskService.add("Done task");
        
        List<Task> tasks = taskService.getTasks();
        taskService.markInProgress(tasks.get(1).getId());
        taskService.markDone(tasks.get(2).getId());
        
        // ACT
        List<Task> todoTasks = taskService.getTasksByStatus(TaskStatus.TODO);
        
        // ASSERT: Chỉ có 1 task todo
        assertEquals(1, todoTasks.size());
        assertEquals("Todo task", todoTasks.get(0).getDescription());
        assertEquals(TaskStatus.TODO, todoTasks.get(0).getStatus());
    }
    
    @Test
    void testGetTasksByStatus_ShouldReturnOnlyInProgressTasks() {
        // ARRANGE
        taskService.add("Task 1");
        taskService.add("Task 2");
        taskService.add("Task 3");
        
        List<Task> tasks = taskService.getTasks();
        taskService.markInProgress(tasks.get(0).getId());
        taskService.markInProgress(tasks.get(2).getId());
        
        // ACT
        List<Task> inProgressTasks = taskService.getTasksByStatus(TaskStatus.IN_PROGRESS);
        
        // ASSERT: Có 2 tasks in-progress
        assertEquals(2, inProgressTasks.size());
    }
    
    @Test
    void testGetTasksByStatus_ShouldReturnEmptyListWhenNoTasksMatch() {
        // ARRANGE: Add tasks nhưng không có task nào DONE
        taskService.add("Task 1");
        taskService.add("Task 2");
        
        // ACT
        List<Task> doneTasks = taskService.getTasksByStatus(TaskStatus.DONE);
        
        // ASSERT: Empty list
        assertEquals(0, doneTasks.size());
    }
    
    // ===== TEST GET ALL TASKS =====
    
    @Test
    void testGetTasks_ShouldReturnAllTasks() {
        // ARRANGE
        taskService.add("Task 1");
        taskService.add("Task 2");
        taskService.add("Task 3");
        
        // ACT
        List<Task> tasks = taskService.getTasks();
        
        // ASSERT
        assertEquals(3, tasks.size());
    }

    /*
    * Fake repository để test
    * */
    class FakeTaskRepository implements TaskRepository {
        private List<Task> tasks = new ArrayList<>();
        private int nextId = 0;

        @Override
        public boolean saveData(List<Task> tasks, boolean increaseId) {
            this.tasks = new ArrayList<>(tasks);
            if (increaseId) {
                nextId++;
            }

            return true;
        }

        @Override
        public boolean saveData(List<Task> tasks) {
            this.tasks = new ArrayList<>(tasks);

            return true;
        }

        @Override
        public int getNextId() {
            return nextId;
        }

        @Override
        public List<Task> getTasks() {
            return tasks;
        }
    }
}
