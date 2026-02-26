package org.de013.tasktrackercli.cli.command;

import org.de013.tasktrackercli.service.TaskService;

public class MarkDoneCommand implements Command {
    private int id;

    public MarkDoneCommand() {}

    public MarkDoneCommand(int id) {
        this.id = id;
    }

    @Override
    public void execute(TaskService taskService) {
        taskService.markDone(id);
    }
}
