package org.de013.tasktrackercli.cli;

import org.de013.tasktrackercli.cli.command.*;
import org.de013.tasktrackercli.util.Messages;
import org.de013.tasktrackercli.util.TaskCommand;
import org.de013.tasktrackercli.util.TaskStatus;

public class CommandParser {
    private String[] args;

    public CommandParser() {}

    public CommandParser(String[] args) {
        this.args = args;
    }

    public Command parse() {
        switch (args[0]) {
            case TaskCommand.ADD:
                if (args.length < 2) {
                    System.err.println(Messages.get("error.add.missing_desc"));
                    return null;
                }
                if (args[1] == null || args[1].trim().isEmpty()) {
                    System.err.println(Messages.get("error.add.empty_desc"));
                    return null;
                }
                return new AddCommand(args[1]);

            case TaskCommand.UPDATE:
                if (args.length < 3) {
                    System.err.println(Messages.get("error.update.missing_args"));
                    return null;
                }
                try {
                    int updateId = Integer.parseInt(args[1]);
                    if (updateId < 0) {
                        System.err.println(Messages.get("error.negative_id"));
                        return null;
                    }
                    if (args[2] == null || args[2].trim().isEmpty()) {
                        System.err.println(Messages.get("error.update.empty_desc"));
                        return null;
                    }
                    return new UpdateCommand(updateId, args[2]);
                } catch (NumberFormatException e) {
                    System.err.println(Messages.get("error.invalid_id_format"));
                    return null;
                }

            case TaskCommand.DELETE:
                if (args.length < 2) {
                    System.err.println(Messages.get("error.delete.missing_id"));
                    return null;
                }
                try {
                    int deleteId = Integer.parseInt(args[1]);
                    if (deleteId < 0) {
                        System.err.println(Messages.get("error.negative_id"));
                        return null;
                    }
                    return new DeleteCommand(deleteId);
                } catch (NumberFormatException e) {
                    System.err.println(Messages.get("error.invalid_id_format"));
                    return null;
                }

            case TaskCommand.MARK_IN_PROGRESS:
                if (args.length < 2) {
                    System.err.println(Messages.get("error.mark.missing_id", "mark-in-progress"));
                    return null;
                }
                try {
                    int progressId = Integer.parseInt(args[1]);
                    if (progressId < 0) {
                        System.err.println(Messages.get("error.negative_id"));
                        return null;
                    }
                    return new MarkInProgressCommand(progressId);
                } catch (NumberFormatException e) {
                    System.err.println(Messages.get("error.invalid_id_format"));
                    return null;
                }

            case TaskCommand.MARK_DONE:
                if (args.length < 2) {
                    System.err.println(Messages.get("error.mark.missing_id", "mark-done"));
                    return null;
                }
                try {
                    int doneId = Integer.parseInt(args[1]);
                    if (doneId < 0) {
                        System.err.println(Messages.get("error.negative_id"));
                        return null;
                    }
                    return new MarkDoneCommand(doneId);
                } catch (NumberFormatException e) {
                    System.err.println(Messages.get("error.invalid_id_format"));
                    return null;
                }

            case TaskCommand.LIST:
                if (args.length == 2) {
                    try {
                        TaskStatus taskStatus = TaskStatus.fromString(args[1]);
                        return new ListByStatusCommand(taskStatus);
                    } catch (IllegalArgumentException e) {
                        System.err.println(Messages.get("error.list.invalid_status"));
                        return null;
                    }
                } else {
                    return new ListCommand();
                }

            case TaskCommand.LANGUAGE:
                if (args.length < 2) {
                    System.err.println(Messages.get("error.lang.invalid"));
                    return null;
                }
                String lang = args[1].toLowerCase();
                if (!lang.equals("vi") && !lang.equals("en")) {
                    System.err.println(Messages.get("error.lang.invalid"));
                    return null;
                }
                return new LanguageCommand(lang);

            case TaskCommand.HELP:
                return new HelpCommand();

            default:
                System.err.println(Messages.get("error.unknown_command", args[0]));
                return null;
        }
    }
}
