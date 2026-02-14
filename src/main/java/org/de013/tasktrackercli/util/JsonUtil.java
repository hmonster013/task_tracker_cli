package org.de013.tasktrackercli.util;

import org.de013.tasktrackercli.model.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtil {
//    private static final Pattern TASK_PATTERN = Pattern.compile("\\{\\s*\"id\"\\s*:\\s*(\\d+)\\s*,\\s*\"description\"\\s*:\\s*\"(.*?)\"\\s*,\\s*\"status\"\\s*:\\s*\"(\\w+)\"\\s*,\\s*\"createdAt\"\\s*:\\s*\"(.*?)\"\\s*,\\s*\"updatedAt\"\\s*:\\s*\"(.*?)\"\\s*\\}", Pattern.DOTALL);
    private static final Pattern TASK_PATTERN = Pattern.compile("\\{\\s*\"id\"\\s*:\\s*(\\d+)\\s*,\\s*\"description\"\\s*:\\s*\"(.*?)\"\\s*,\\s*\"status\"\\s*:\\s*\"(.*?)\"\\s*,\\s*\"createdAt\"\\s*:\\s*\"(.*?)\"\\s*,\\s*\"updatedAt\"\\s*:\\s*\"(.*?)\"\\s*\\}", Pattern.DOTALL);
    public static Task convertJsontoTask(String data){
        Task task = null;

        Matcher m = TASK_PATTERN.matcher(data);
        if (m.find()) {
            int id = Integer.parseInt(m.group(1));
            String desc = m.group(2);
            String status = m.group(3);
            String created = m.group(4);
            String updated = m.group(5);

            task = new Task(id, desc, status, LocalDateTime.parse(created), LocalDateTime.parse(updated));
        }

        return task;
    }

    public static String convertListTaskToJson(List<Task> tasks, int nextId){
        // TODO: Xử lý các trường hợp command
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"nextId\":").append(nextId).append(",");
        sb.append("\"tasks\":");
        sb.append("[");

        for (int i = 0; i < tasks.size(); i++) {
            sb.append("{");
            sb.append("\"id\":").append(tasks.get(i).getId()).append(",");
            sb.append("\"description\":").append("\"").append(tasks.get(i).getDescription()).append("\",");
            sb.append("\"status\":").append("\"").append(tasks.get(i).getStatus()).append("\",");
            sb.append("\"createdAt\":").append("\"").append(tasks.get(i).getCreatedAt().toString()).append("\",");
            sb.append("\"updatedAt\":").append("\"").append(tasks.get(i).getCreatedAt().toString()).append("\"");
            sb.append("}");
            if (i < tasks.size() - 1) {
                sb.append(",");
            }
        }

        sb.append("]");
        sb.append("}");

        return sb.toString();
    }
}
