package org.de013.tasktrackercli.cli.command;

import org.de013.tasktrackercli.model.Task;
import org.de013.tasktrackercli.service.TaskService;

import java.util.List;

public class ListCommand implements Command {
    public ListCommand() {};

    @Override
    public void excute(TaskService taskService) {
        List<Task> tasks = taskService.getTasks();
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
