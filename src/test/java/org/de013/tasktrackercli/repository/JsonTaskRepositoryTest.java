package org.de013.tasktrackercli.repository;

import org.de013.tasktrackercli.model.Task;
import org.de013.tasktrackercli.util.TaskStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonTaskRepositoryTest {
    
    private static final Path TEST_FILE = Paths.get("test_data.json");
    private static final Path BACKUP_FILE = Paths.get("test_data.json.bak");
    private JsonTaskRepository repository;
    
    @BeforeEach
    void setUp() {
        // Clean up test files before each test
        cleanupTestFiles();
    }
    
    @AfterEach
    void tearDown() {
        // Clean up test files after each test
        cleanupTestFiles();
    }
    
    private void cleanupTestFiles() {
        try {
            Files.deleteIfExists(TEST_FILE);
            Files.deleteIfExists(BACKUP_FILE);
        } catch (IOException e) {
            // Ignore cleanup errors
        }
    }
    
    // ===== TEST FILE KHÔNG TỒN TẠI =====
    
    @Test
    void testLoadFile_WhenFileNotExist_ShouldReturnEmptyList() {
        // ARRANGE: File không tồn tại
        assertFalse(Files.exists(TEST_FILE));
        
        // ACT: Tạo repository với test file
        repository = new JsonTaskRepository(TEST_FILE);
        
        // ASSERT: getTasks() trả về empty list
        List<Task> tasks = repository.getTasks();
        assertNotNull(tasks);
        assertEquals(0, tasks.size());
    }
    
    // ===== TEST SAVE VÀ LOAD FILE =====
    
    @Test
    void testSaveAndLoad_ShouldPersistTasksToFile() throws IOException {
        // ARRANGE: Tạo repository và add tasks
        repository = new JsonTaskRepository(TEST_FILE);
        List<Task> tasks = repository.getTasks();
        
        Task task1 = new Task(0, "Task 1");
        Task task2 = new Task(1, "Task 2");
        tasks.add(task1);
        tasks.add(task2);
        
        // ACT: Save data
        boolean saveResult = repository.saveData(tasks, true);
        
        // ASSERT: File được tạo và có data
        assertTrue(saveResult);
        assertTrue(Files.exists(TEST_FILE));
        
        // Đọc lại file và verify
        String content = Files.readString(TEST_FILE);
        assertTrue(content.contains("Task 1"));
        assertTrue(content.contains("Task 2"));
        assertTrue(content.contains("nextId"));
    }
    
    @Test
    void testSaveData_WithIncreaseId_ShouldIncrementNextId() {
        // ARRANGE
        repository = new JsonTaskRepository(TEST_FILE);
        List<Task> tasks = repository.getTasks();
        int initialNextId = repository.getNextId();
        
        // ACT: Save với increaseId = true
        repository.saveData(tasks, true);
        
        // ASSERT: nextId tăng lên
        assertEquals(initialNextId + 1, repository.getNextId());
    }
    
    @Test
    void testSaveData_WithoutIncreaseId_ShouldKeepNextId() {
        // ARRANGE
        repository = new JsonTaskRepository(TEST_FILE);
        List<Task> tasks = repository.getTasks();
        tasks.add(new Task(0, "Task"));
        int initialNextId = repository.getNextId();
        
        // ACT: Save với increaseId = false (method overload)
        repository.saveData(tasks);
        
        // ASSERT: nextId không đổi
        assertEquals(initialNextId, repository.getNextId());
    }
    
    // ===== TEST HANDLE CORRUPT JSON =====
    
    @Test
    void testLoadFile_WhenJsonCorrupt_ShouldReturnEmptyList() throws IOException {
        // ARRANGE: Tạo file JSON bị corrupt
        Files.writeString(TEST_FILE, "{invalid json content!!!}");
        assertTrue(Files.exists(TEST_FILE));
        
        // ACT: Load repository với corrupt file
        repository = new JsonTaskRepository(TEST_FILE);
        
        // ASSERT: getTasks() trả về empty list (không crash)
        List<Task> tasks = repository.getTasks();
        assertNotNull(tasks);
        assertEquals(0, tasks.size());
        
        // Note: Backup file behavior được test manually vì Files.move có thể
        // bị ảnh hưởng bởi file system timing issues trong automated tests
    }
    
    @Test
    void testLoadFile_WhenFileEmpty_ShouldReturnEmptyList() throws IOException {
        // ARRANGE: Tạo file rỗng (0 bytes)
        Files.writeString(TEST_FILE, "");
        assertTrue(Files.exists(TEST_FILE));
        
        // ACT
        repository = new JsonTaskRepository(TEST_FILE);
        
        // ASSERT: Không crash, trả về empty list
        List<Task> tasks = repository.getTasks();
        assertNotNull(tasks);
        assertEquals(0, tasks.size());
    }
    
    // ===== TEST LOAD VÀ PARSE JSON STRUCTURE =====
    
    @Test
    void testLoadFile_ShouldParseNextIdCorrectly() throws IOException {
        // ARRANGE: Tạo file JSON với nextId = 5
        String jsonContent = "{\"nextId\":5,\"tasks\":[]}";
        Files.writeString(TEST_FILE, jsonContent);
        
        // ACT
        repository = new JsonTaskRepository(TEST_FILE);
        
        // ASSERT: nextId được parse đúng
        assertEquals(5, repository.getNextId());
    }
    
    @Test
    void testLoadFile_ShouldParseTasksCorrectly() throws IOException {
        // ARRANGE: Tạo file JSON với tasks
        String jsonContent = """
        {
            "nextId": 2,
            "tasks": [
                {
                    "id": 0,
                    "description": "Test task 1",
                    "status": "todo",
                    "createdAt": "2026-02-18T10:00:00",
                    "updatedAt": "2026-02-18T10:00:00"
                },
                {
                    "id": 1,
                    "description": "Test task 2",
                    "status": "in-progress",
                    "createdAt": "2026-02-18T11:00:00",
                    "updatedAt": "2026-02-18T11:00:00"
                }
            ]
        }
        """;
        Files.writeString(TEST_FILE, jsonContent);
        
        // ACT
        repository = new JsonTaskRepository(TEST_FILE);
        
        // ASSERT: Tasks được parse đúng
        List<Task> tasks = repository.getTasks();
        assertEquals(2, tasks.size());
        
        assertEquals(0, tasks.get(0).getId());
        assertEquals("Test task 1", tasks.get(0).getDescription());
        assertEquals(TaskStatus.TODO, tasks.get(0).getStatus());
        
        assertEquals(1, tasks.get(1).getId());
        assertEquals("Test task 2", tasks.get(1).getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, tasks.get(1).getStatus());
    }
    
    // ===== TEST SAVE ERROR HANDLING =====
    
    @Test
    void testSaveData_ShouldReturnTrueOnSuccess() {
        // ARRANGE
        repository = new JsonTaskRepository(TEST_FILE);
        List<Task> tasks = repository.getTasks();
        tasks.add(new Task(0, "Test"));
        
        // ACT
        boolean result = repository.saveData(tasks);
        
        // ASSERT
        assertTrue(result);
    }
}
