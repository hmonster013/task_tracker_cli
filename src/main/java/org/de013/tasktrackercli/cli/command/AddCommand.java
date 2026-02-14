package org.de013.tasktrackercli.cli.command;

import org.de013.tasktrackercli.service.TaskService;

public class AddCommand implements Command {
    private String description;

    public AddCommand() {}

    public AddCommand(String description) {
        this.description = description;
    }

    @Override
    public void excute(TaskService taskService) {
        taskService.add(description);
    }
}
