package org.de013.tasktrackercli.repository;

import org.de013.tasktrackercli.model.Task;
import org.de013.tasktrackercli.util.JsonUtil;
import org.de013.tasktrackercli.util.Messages;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonTaskRepository implements TaskRepository {
    private static final Path DEFAULT_FILE_PATH = Paths.get("data.json");
    private static final Pattern NEXTID_PATTERN = Pattern.compile("\\s*\"nextId\"\\s*:\\s*(\\d+)\\s*", Pattern.DOTALL);

    private final Path filePath;
    private int nextId = 0;
    private final List<Task> tasks;
    
    public JsonTaskRepository() {
        this(DEFAULT_FILE_PATH);
    }
    
    public JsonTaskRepository(Path filePath) {
        this.filePath = filePath;
        this.tasks = loadFile();
    }

    private List<Task> loadFile() {
        File file = new File(filePath.toString());

        // Kiểm tra file có tồn tại hay không
        if (!file.exists()) {
            System.out.println(Messages.get("error.file.not_exist"));
            return new ArrayList<>();
        }

        try {
            // Kiểm tra dung lượng file (nếu file 0 byte thì Jackson sẽ báo lỗi)
            if (Files.size(file.toPath()) == 0) {
                return new ArrayList<>();
            }

            return readData(file);
        } catch (Exception e) {
            // Xử lý trường hợp corrupt (Dữ liệu sai định dạng)
            System.out.println(Messages.get("error.file.corrupt"));

            // Tùy chọn: Backup file bị hỏng để người dùng tự sửa và tạo file mới sạch sẽ
            handleCorruptFile(file);

            return new ArrayList<>();
        }
    }

    private void handleCorruptFile(File file) {
        try {
            File backup = new File(filePath.toString() + ".bak");
            Files.move(file.toPath(), backup.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            System.out.println(Messages.get("error.file.backup", backup.getName()));
        } catch (IOException e) {
            System.err.println(Messages.get("error.file.backup_failed", e.getMessage()));
        }
    }

    private List<Task> readData(File file) throws IOException{
        String data = Files.readString(file.toPath());

        // Read nextId
        Matcher nextIdMatcher = NEXTID_PATTERN.matcher(data);
        if (nextIdMatcher.find()) {
            this.nextId = Integer.parseInt(nextIdMatcher.group(1));
        }
        // Read tasks
        List<Task> taskData = new ArrayList<>();
        List<String> rawTask = splitObjects(data);
        for (String raw : rawTask) {
            taskData.add(JsonUtil.convertJsontoTask(raw));
        }

        return taskData;
    }

    private static List<String> splitObjects(String json) {
        List<String> objects = new ArrayList<>();
        json = json.trim();
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);

        int depth = 0;
        int start = -1;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') {
                if (depth == 0)  start = i;
                depth++;
            }
            if (c == '}') {
                depth--;
                if (depth == 0 && start != -1) {
                    objects.add(json.substring(start, i + 1));
                    start = -1;
                }
            }
        }

        return objects;
    }

    public boolean saveData(List<Task> tasks, boolean increaseId) {
        int potentialNextId = increaseId ? this.nextId + 1 : this.nextId;

        try {
            String json = JsonUtil.convertListTaskToJson(tasks, potentialNextId);

            Files.writeString(filePath, json);

            if (increaseId) {
                this.nextId = potentialNextId;
            }

            return true;
        } catch (IOException e) {
            System.out.println(Messages.get("error.file.write", e.getMessage()));
            return false;
        }
    }

    public boolean saveData(List<Task> tasks) {
        try {
            String json = JsonUtil.convertListTaskToJson(tasks, this.nextId);

            Files.writeString(filePath, json);

            return true;
        } catch (IOException e) {
            System.out.println(Messages.get("error.file.write", e.getMessage()));
            return false;
        }
    }

    public int getNextId() {
        return this.nextId;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }
}
