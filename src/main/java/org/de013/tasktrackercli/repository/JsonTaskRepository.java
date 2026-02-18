package org.de013.tasktrackercli.repository;

import org.de013.tasktrackercli.model.Task;
import org.de013.tasktrackercli.util.JsonUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonTaskRepository implements TaskRepository {
    private static final Path FILE_PATH = Paths.get("data.json");
    private static final Pattern NEXTID_PATTERN = Pattern.compile("\\s*\"nextId\"\\s*:\\s*(\\d+)\\s*", Pattern.DOTALL);
    private static final Pattern RAW_TASKS_PATTERN = Pattern.compile("\\{\\s*\"id\"\\s*:\\s*\\d+[\\s\\S]*?}", Pattern.DOTALL);

    private int nextId = 0;
    private final List<Task> tasks;

    public JsonTaskRepository() {
        this.tasks = new ArrayList<>();
        this.readData();
    }

    private void readData() {
        try {
            String data = Files.readString(FILE_PATH);

            // Read nextId
            Matcher nextIdMatcher = NEXTID_PATTERN.matcher(data);
            if (nextIdMatcher.find()) {
                this.nextId = Integer.parseInt(nextIdMatcher.group(1));
            }
            // Read tasks
            Matcher rawTasksMatcher = RAW_TASKS_PATTERN.matcher(data);
            while (rawTasksMatcher.find()) {
                String task = rawTasksMatcher.group();
                this.tasks.add(JsonUtil.convertJsontoTask(task));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean saveData(List<Task> tasks, boolean increaseId) {
        if (increaseId) {
            this.nextId++;
        }
        String json = JsonUtil.convertListTaskToJson(tasks, this.nextId);

        try {
            Files.writeString(FILE_PATH, json);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean saveData(List<Task> tasks) {
        // TODO: Xử lý các trường hợp command
        String json = JsonUtil.convertListTaskToJson(tasks, this.nextId);

        try {
            Files.writeString(FILE_PATH, json);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
