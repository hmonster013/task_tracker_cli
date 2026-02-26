package org.de013.tasktrackercli.service;

import org.de013.tasktrackercli.model.Task;
import org.de013.tasktrackercli.util.TaskStatus;

import java.util.List;

public interface TaskService {
    void add(String description);
    void update(int id, String description);
    void delete(int id);
    void markInProgress(int id);
    void markDone(int id);
    List<Task> getTasks();
    List<Task> getTasksByStatus(TaskStatus status);
}
