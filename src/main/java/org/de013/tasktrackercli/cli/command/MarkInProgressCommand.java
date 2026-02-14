package org.de013.tasktrackercli.cli.command;

import org.de013.tasktrackercli.service.TaskService;

public class MarkInProgressCommand implements Command {
    private int id;

    public MarkInProgressCommand() {};

    public MarkInProgressCommand(int id) {
        this.id = id;
    }

    @Override
    public void excute(TaskService taskService) {
        taskService.markInProgress(id);
    }
}
