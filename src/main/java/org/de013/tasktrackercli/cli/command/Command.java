package org.de013.tasktrackercli.cli.command;

import org.de013.tasktrackercli.service.TaskService;

public interface Command {
    void execute(TaskService taskService);
}
