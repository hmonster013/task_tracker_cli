package org.de013.tasktrackercli;

import org.de013.tasktrackercli.cli.CommandParser;
import org.de013.tasktrackercli.cli.command.Command;
import org.de013.tasktrackercli.service.TaskService;
import org.de013.tasktrackercli.service.TaskServiceImpl;

public class Main {
    public static void main(String[] args) {
        CommandParser commandParser = new CommandParser(args);
        TaskService taskService = new TaskServiceImpl();

        Command command = commandParser.parse();
        command.excute(taskService);
    }
}

