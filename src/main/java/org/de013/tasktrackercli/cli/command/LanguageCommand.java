package org.de013.tasktrackercli.cli.command;

import org.de013.tasktrackercli.service.TaskService;
import org.de013.tasktrackercli.util.Messages;

public class LanguageCommand implements Command {
    private String language;

    public LanguageCommand() {}

    public LanguageCommand(String language) {
        this.language = language;
    }

    @Override
    public void execute(TaskService taskService) {
        if ("vi".equalsIgnoreCase(language)) {
            Messages.setLanguage(Messages.Language.VI);
            System.out.println(Messages.get("success.lang.changed", "Tiếng Việt"));
        } else if ("en".equalsIgnoreCase(language)) {
            Messages.setLanguage(Messages.Language.EN);
            System.out.println(Messages.get("success.lang.changed", "English"));
        }
    }
}
