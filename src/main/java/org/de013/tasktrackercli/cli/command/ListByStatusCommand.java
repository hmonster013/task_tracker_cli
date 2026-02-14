package org.de013.tasktrackercli.cli.command;

import org.de013.tasktrackercli.model.Task;
import org.de013.tasktrackercli.service.TaskService;

import java.util.List;

public class ListByStatusCommand implements Command {
    private String status;

    public ListByStatusCommand() {};

    public ListByStatusCommand(String status) {
        this.status = status;
    }

    @Override
    public void excute(TaskService taskService) {
        List<Task> tasks = taskService.getTasksByStatus(status);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < tasks.size(); i++) {
            sb.append("Task " + i + ": " + "\n");
            sb.append("=============================================\n");
            sb.append(tasks.get(i).toString() + "\n");
            sb.append("=============================================\n");
        }

        System.out.println(sb.toString());
    }
}
