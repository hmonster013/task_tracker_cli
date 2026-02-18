package org.de013.tasktrackercli;

import org.de013.tasktrackercli.cli.CommandParser;
import org.de013.tasktrackercli.cli.command.Command;
import org.de013.tasktrackercli.service.TaskService;
import org.de013.tasktrackercli.service.TaskServiceImpl;
import org.de013.tasktrackercli.util.Messages;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println(Messages.get("error.no_command"));
            System.err.println(Messages.get("error.available_commands"));
            return;
        }

        CommandParser commandParser = new CommandParser(args);
        TaskService taskService = new TaskServiceImpl();

        Command command = commandParser.parse();
        
        if (command == null) {
            System.err.println(Messages.get("error.parsing_failed"));
            return;
        }

        command.excute(taskService);
    }
}

