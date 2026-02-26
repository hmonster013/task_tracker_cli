package org.de013.tasktrackercli.cli.command;

import org.de013.tasktrackercli.service.TaskService;
import org.de013.tasktrackercli.util.Messages;

public class HelpCommand implements Command {

    @Override
    public void execute(TaskService taskService) {
        System.out.println(Messages.get("help.title"));
        System.out.println();
        System.out.println(Messages.get("help.usage"));
        System.out.println();
        System.out.println(Messages.get("help.commands"));
        System.out.println(Messages.get("help.add"));
        System.out.println(Messages.get("help.update"));
        System.out.println(Messages.get("help.delete"));
        System.out.println(Messages.get("help.mark_progress"));
        System.out.println(Messages.get("help.mark_done"));
        System.out.println(Messages.get("help.list"));
        System.out.println(Messages.get("help.list_status"));
        System.out.println(Messages.get("help.language"));
        System.out.println(Messages.get("help.help"));
        System.out.println();
        System.out.println(Messages.get("help.examples"));
        System.out.println(Messages.get("help.example1"));
        System.out.println(Messages.get("help.example2"));
        System.out.println(Messages.get("help.example3"));
    }
}
