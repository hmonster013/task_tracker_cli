package org.de013.tasktrackercli.cli.command;

import org.de013.tasktrackercli.service.TaskService;

public class DeleteCommand implements Command {
    private int id;

    public DeleteCommand() {};

    public DeleteCommand(int id) {
        this.id = id;
    }

    @Override
    public void excute(TaskService taskService) {
        taskService.delete(id);
    }
}
