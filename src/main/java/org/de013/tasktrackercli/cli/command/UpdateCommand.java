package org.de013.tasktrackercli.cli.command;

import org.de013.tasktrackercli.service.TaskService;

public class UpdateCommand implements Command {
    private int id;
    private String description;

    public UpdateCommand() {}

    public UpdateCommand(int id, String description){
        this.id = id;
        this.description = description;
    }

    @Override
    public void execute(TaskService taskService) {
        taskService.update(id, description);
    }
}
