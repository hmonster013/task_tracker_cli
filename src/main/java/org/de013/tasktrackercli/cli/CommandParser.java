package org.de013.tasktrackercli.cli;

import org.de013.tasktrackercli.cli.command.*;
import org.de013.tasktrackercli.util.TaskCommand;

public class CommandParser {
    private String[] args;

    public CommandParser() {}

    public CommandParser(String[] args) {
        this.args = args;
    }

    public Command parse() {
        switch (args[0]) {
            case TaskCommand.ADD:
                return new AddCommand(args[1]);
            case TaskCommand.UPDATE:
                return new UpdateCommand(Integer.parseInt(args[1]), args[2]);
            case TaskCommand.DELETE:
                return new DeleteCommand(Integer.parseInt(args[1]));
            case TaskCommand.MARK_IN_PROGRESS:
                return new MarkInProgressCommand(Integer.parseInt(args[1]));
            case TaskCommand.MARK_DONE:
                return new MarkDoneCommand(Integer.parseInt(args[1]));
            case TaskCommand.LIST:
                if (args.length == 2) {
                    return new ListByStatusCommand(args[1]);
                } else {
                    return new ListCommand();
                }
        }

        return null;
    }
}
