package org.de013.tasktrackercli.service;

import org.de013.tasktrackercli.model.Task;
import org.de013.tasktrackercli.repository.JsonTaskRepository;
import org.de013.tasktrackercli.repository.TaskRepository;
import org.de013.tasktrackercli.util.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {
    TaskRepository taskRepository;
    public TaskServiceImpl() {
        taskRepository = new JsonTaskRepository();
    }

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void add(String description) {
        Task newTask = new Task(taskRepository.getNextId(), description);
        List<Task> tasks = taskRepository.getTasks();
        tasks.add(newTask);

        taskRepository.saveData(tasks, true);
    }

    @Override
    public void update(int id, String description) {
        List<Task> tasks = taskRepository.getTasks();

        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setDescription(description);
                task.setUpdatedAt(LocalDateTime.now());

                break;
            }
        }

        taskRepository.saveData(tasks);
    }

    @Override
    public void delete(int id) {
        List<Task> tasks = taskRepository.getTasks();

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.remove(i);
                break;
            }
        }

        taskRepository.saveData(tasks);
    }

    @Override
    public void markInProgress(int id) {
        List<Task> tasks = taskRepository.getTasks();
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setStatus(TaskStatus.IN_PROGRESS);

                break;
            }
        }

        taskRepository.saveData(tasks);
    }

    @Override
    public void markDone(int id) {
        List<Task> tasks = taskRepository.getTasks();
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setStatus(TaskStatus.DONE);

                break;
            }
        }

        taskRepository.saveData(tasks);
    }

    @Override
    public List<Task> getTasks() {
        return taskRepository.getTasks();
    }

    @Override
    public List<Task> getTasksByStatus(String status) {
        return taskRepository.getTasks()
                .stream()
                .filter(task -> status.equals(task.getStatus()))
                .collect(Collectors.toList());
    }
}
