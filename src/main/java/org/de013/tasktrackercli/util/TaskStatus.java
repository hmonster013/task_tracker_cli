package org.de013.tasktrackercli.util;

public enum TaskStatus {
    TODO("todo", "[ ]", "TODO"),
    IN_PROGRESS("in-progress", "[▶]", "IN PROGRESS"),
    DONE("done", "[✓]", "DONE");

    public final String value;
    public final String icon;
    public final String displayName;

    TaskStatus(String value, String icon, String displayName) {
        this.value = value;
        this.icon = icon;
        this.displayName = displayName;
    }

    public static TaskStatus fromString(String value) {
        for (TaskStatus s : values()) {
            if (s.value.equals(value)) return s;
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }
}
