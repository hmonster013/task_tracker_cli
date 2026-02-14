package org.de013.tasktrackercli.repository;

import org.de013.tasktrackercli.model.Task;

import java.util.List;

public interface TaskRepository {
    boolean saveData(List<Task> tasks, boolean increaseId);
    boolean saveData(List<Task> tasks);
    int getNextId();
    List<Task> getTasks();
}
