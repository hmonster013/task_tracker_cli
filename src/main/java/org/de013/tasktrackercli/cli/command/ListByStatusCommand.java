package org.de013.tasktrackercli.cli.command;

import org.de013.tasktrackercli.model.Task;
import org.de013.tasktrackercli.service.TaskService;
import org.de013.tasktrackercli.util.Messages;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListByStatusCommand implements Command {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private String status;

    public ListByStatusCommand() {}

    public ListByStatusCommand(String status) {
        this.status = status;
    }

    @Override
    public void excute(TaskService taskService) {
        List<Task> tasks = taskService.getTasksByStatus(status);
        
        if (tasks.isEmpty()) {
            System.out.println(Messages.get("list.empty_status", getStatusText(status)));
            return;
        }
        
        System.out.println("\n" + Messages.get("list.header_status", getStatusText(status), tasks.size()));
        System.out.println("═══════════════════════════════════════════════════════════════════════");
        
        for (Task task : tasks) {
            printTask(task);
        }
        
        System.out.println("═══════════════════════════════════════════════════════════════════════");
        System.out.println(Messages.get("list.total", tasks.size()));
    }
    
    private void printTask(Task task) {
        String statusIcon = getStatusIcon(task.getStatus());
        String statusColor = getStatusText(task.getStatus());
        
        System.out.println();
        System.out.printf("  %s [ID: %d] %s%n", statusIcon, task.getId(), statusColor);
        System.out.printf("     Description: %s%n", task.getDescription());
        System.out.printf("     Created:     %s%n", task.getCreatedAt().format(FORMATTER));
        System.out.printf("     Updated:     %s%n", task.getUpdatedAt().format(FORMATTER));
        System.out.println("  ─────────────────────────────────────────────────────────────────────");
    }
    
    private String getStatusIcon(String status) {
        return switch (status) {
            case "todo" -> "[ ]";
            case "in-progress" -> "[▶]";
            case "done" -> "[✓]";
            default -> "[?]";
        };
    }
    
    private String getStatusText(String status) {
        return switch (status) {
            case "todo" -> "TODO";
            case "in-progress" -> "IN PROGRESS";
            case "done" -> "DONE";
            default -> status.toUpperCase();
        };
    }
}
