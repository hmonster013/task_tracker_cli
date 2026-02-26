package org.de013.tasktrackercli.cli.command;

import org.de013.tasktrackercli.model.Task;
import org.de013.tasktrackercli.service.TaskService;
import org.de013.tasktrackercli.util.Messages;
import org.de013.tasktrackercli.util.TaskStatus;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListByStatusCommand implements Command {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private TaskStatus status;

    public ListByStatusCommand() {}

    public ListByStatusCommand(TaskStatus status) {
        this.status = status;
    }

    @Override
    public void execute(TaskService taskService) {
        List<Task> tasks = taskService.getTasksByStatus(status);
        
        if (tasks.isEmpty()) {
            System.out.println(Messages.get("list.empty_status", status.displayName));
            return;
        }
        
        System.out.println("\n" + Messages.get("list.header_status", status.displayName, tasks.size()));
        System.out.println("═══════════════════════════════════════════════════════════════════════");
        
        for (Task task : tasks) {
            printTask(task);
        }
        
        System.out.println("═══════════════════════════════════════════════════════════════════════");
        System.out.println(Messages.get("list.total", tasks.size()));
    }
    
    private void printTask(Task task) {
        System.out.println();
        System.out.printf("  %s [ID: %d] %s%n", task.getStatus().icon, task.getId(), task.getStatus().displayName);
        System.out.printf("     Description: %s%n", task.getDescription());
        System.out.printf("     Created:     %s%n", task.getCreatedAt().format(FORMATTER));
        System.out.printf("     Updated:     %s%n", task.getUpdatedAt().format(FORMATTER));
        System.out.println("  ─────────────────────────────────────────────────────────────────────");
    }
}
