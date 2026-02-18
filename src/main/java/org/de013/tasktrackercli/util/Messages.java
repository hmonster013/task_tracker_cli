package org.de013.tasktrackercli.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Messages {
    private static final Path CONFIG_FILE = Paths.get("language.config");
    private static Language currentLanguage = Language.EN;

    public enum Language {
        VI, EN
    }

    private static final Map<String, Map<Language, String>> messages = new HashMap<>();

    static {
        loadLanguagePreference();
        initMessages();
    }

    private static void initMessages() {
        // Error messages
        addMessage("error.no_command", "Error: No command provided!", "Lỗi: Chưa nhập lệnh!");
        addMessage("error.available_commands", "Available commands: add, update, delete, mark-in-progress, mark-done, list, language, help", 
                   "Các lệnh khả dụng: add, update, delete, mark-in-progress, mark-done, list, language, help");
        addMessage("error.parsing_failed", "Command parsing failed. Please check your input.", "Phân tích lệnh thất bại. Vui lòng kiểm tra lại đầu vào.");
        addMessage("error.unknown_command", "Error: Unknown command '{0}'", "Lỗi: Lệnh không xác định '{0}'");
        
        // Add command
        addMessage("error.add.missing_desc", "Error: Missing description. Usage: add <description>", "Lỗi: Thiếu mô tả. Cách dùng: add <mô tả>");
        addMessage("error.add.empty_desc", "Error: Description cannot be empty!", "Lỗi: Mô tả không được để trống!");
        addMessage("success.add", "Task added successfully (ID: {0})", "Thêm task thành công (ID: {0})");
        
        // Update command
        addMessage("error.update.missing_args", "Error: Missing arguments. Usage: update <id> <description>", "Lỗi: Thiếu tham số. Cách dùng: update <id> <mô tả>");
        addMessage("error.update.empty_desc", "Error: Description cannot be empty!", "Lỗi: Mô tả không được để trống!");
        addMessage("success.update", "Task updated successfully (ID: {0})", "Cập nhật task thành công (ID: {0})");
        
        // Delete command
        addMessage("error.delete.missing_id", "Error: Missing ID. Usage: delete <id>", "Lỗi: Thiếu ID. Cách dùng: delete <id>");
        addMessage("success.delete", "Task deleted successfully (ID: {0})", "Xóa task thành công (ID: {0})");
        
        // Mark commands
        addMessage("error.mark.missing_id", "Error: Missing ID. Usage: {0} <id>", "Lỗi: Thiếu ID. Cách dùng: {0} <id>");
        addMessage("success.mark_progress", "Task marked as in-progress (ID: {0})", "Đánh dấu task đang thực hiện (ID: {0})");
        addMessage("success.mark_done", "Task marked as done (ID: {0})", "Đánh dấu task hoàn thành (ID: {0})");
        
        // List command
        addMessage("error.list.invalid_status", "Error: Invalid status! Must be one of: todo, in-progress, done", 
                   "Lỗi: Trạng thái không hợp lệ! Phải là: todo, in-progress, done");
        
        // Common errors
        addMessage("error.invalid_id_format", "Error: Invalid ID format. ID must be a number!", "Lỗi: Định dạng ID không hợp lệ. ID phải là số!");
        addMessage("error.negative_id", "Error: ID must be a non-negative number!", "Lỗi: ID phải là số không âm!");
        addMessage("error.task_not_found", "Error: Task with ID {0} not found!", "Lỗi: Không tìm thấy task có ID {0}!");
        
        // Language command
        addMessage("error.lang.invalid", "Error: Invalid language! Use: language vi or language en", "Lỗi: Ngôn ngữ không hợp lệ! Dùng: language vi hoặc language en");
        addMessage("success.lang.changed", "Language changed to {0}", "Đã đổi ngôn ngữ sang {0}");
        
        // Help command
        addMessage("help.title", "=== Task Tracker CLI - Help ===", "=== Task Tracker CLI - Trợ Giúp ===");
        addMessage("help.usage", "Usage: task-cli <command> [arguments]", "Cách dùng: task-cli <lệnh> [tham số]");
        addMessage("help.commands", "Available Commands:", "Các Lệnh Khả Dụng:");
        addMessage("help.add", "  add <description>              - Add a new task", "  add <mô tả>                    - Thêm task mới");
        addMessage("help.update", "  update <id> <description>      - Update a task", "  update <id> <mô tả>            - Cập nhật task");
        addMessage("help.delete", "  delete <id>                    - Delete a task", "  delete <id>                    - Xóa task");
        addMessage("help.mark_progress", "  mark-in-progress <id>          - Mark task as in-progress", "  mark-in-progress <id>          - Đánh dấu đang làm");
        addMessage("help.mark_done", "  mark-done <id>                 - Mark task as done", "  mark-done <id>                 - Đánh dấu hoàn thành");
        addMessage("help.list", "  list                           - List all tasks", "  list                           - Liệt kê tất cả");
        addMessage("help.list_status", "  list <status>                  - List tasks by status (todo/in-progress/done)", "  list <trạng thái>              - Liệt kê theo trạng thái (todo/in-progress/done)");
        addMessage("help.language", "  language <vi|en>               - Change language", "  language <vi|en>               - Đổi ngôn ngữ");
        addMessage("help.help", "  help                           - Show this help message", "  help                           - Hiển thị trợ giúp");
        addMessage("help.examples", "Examples:", "Ví Dụ:");
        addMessage("help.example1", "  task-cli add \"Buy groceries\"", "  task-cli add \"Mua đồ tạp hóa\"");
        addMessage("help.example2", "  task-cli mark-in-progress 1", "  task-cli mark-in-progress 1");
        addMessage("help.example3", "  task-cli list done", "  task-cli list done");
        
        // List command messages
        addMessage("list.empty", "No tasks found.", "Không có task nào.");
        addMessage("list.empty_status", "No {0} tasks found.", "Không có task {0} nào.");
        addMessage("list.header", "All Tasks ({0} total)", "Tất Cả Tasks ({0} tasks)");
        addMessage("list.header_status", "{0} Tasks ({1} total)", "Tasks {0} ({1} tasks)");
        addMessage("list.total", "Total: {0} task(s)", "Tổng: {0} task(s)");
        
        // File errors
        addMessage("error.file.not_exist", "File does not exist. Creating new file", "File không tồn tại. Đang khởi tạo file mới");
        addMessage("error.file.corrupt", "Error: JSON file is corrupted or cannot be read!", "Lỗi: File JSON bị hỏng (corrupt) hoặc không đọc được!");
        addMessage("error.file.backup", "Backed up corrupted file to: {0}", "Đã sao lưu file lỗi sang: {0}");
        addMessage("error.file.backup_failed", "Cannot backup corrupted file: {0}", "Không thể backup file lỗi: {0}");
        addMessage("error.file.write", "Error writing file: {0}", "Lỗi ghi file: {0}");
    }

    private static void addMessage(String key, String en, String vi) {
        Map<Language, String> translations = new HashMap<>();
        translations.put(Language.EN, en);
        translations.put(Language.VI, vi);
        messages.put(key, translations);
    }

    public static String get(String key, Object... params) {
        Map<Language, String> translations = messages.get(key);
        if (translations == null) {
            return key;
        }
        
        String message = translations.get(currentLanguage);
        if (message == null) {
            return key;
        }
        
        // Replace placeholders {0}, {1}, etc.
        for (int i = 0; i < params.length; i++) {
            message = message.replace("{" + i + "}", String.valueOf(params[i]));
        }
        
        return message;
    }

    public static void setLanguage(Language language) {
        currentLanguage = language;
        saveLanguagePreference();
    }

    public static Language getCurrentLanguage() {
        return currentLanguage;
    }

    private static void loadLanguagePreference() {
        File file = new File(CONFIG_FILE.toString());
        if (!file.exists()) {
            return;
        }
        
        try {
            String lang = Files.readString(file.toPath()).trim();
            if ("VI".equalsIgnoreCase(lang)) {
                currentLanguage = Language.VI;
            } else if ("EN".equalsIgnoreCase(lang)) {
                currentLanguage = Language.EN;
            }
        } catch (IOException e) {
            // Use default language
        }
    }

    private static void saveLanguagePreference() {
        try {
            Files.writeString(CONFIG_FILE, currentLanguage.name());
        } catch (IOException e) {
            System.err.println("Cannot save language preference: " + e.getMessage());
        }
    }
}
