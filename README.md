# Task Tracker CLI

A simple command-line interface (CLI) application to track and manage your tasks. Built with Java as part of the [roadmap.sh Task Tracker project](https://roadmap.sh/projects/task-tracker).

## Features

- ✅ Add, update, and delete tasks
- 📝 Mark tasks as in-progress or done
- 📋 List all tasks or filter by status (todo, in-progress, done)
- 🌍 Multi-language support (English/Vietnamese)
- 💾 Data persistence with JSON file storage

## Requirements

- **Java**: JDK 11 or higher
- **Maven**: 3.6 or higher

## Installation & Setup

### 1. Clone the repository
```bash
git clone <repository-url>
cd task_tracker_cli
```

### 2. Build the project
```bash
mvn clean package
```

This will create an executable JAR file in the `target/` directory.

### 3. Run the application
```bash
java -jar target/task-tracker-cli-1.0-SNAPSHOT.jar <command> [arguments]
```

Or create an alias for convenience:
```bash
alias task-cli='java -jar /path/to/target/task-tracker-cli-1.0-SNAPSHOT.jar'
```

## Usage

### Commands

#### Add a new task
```bash
task-cli add "Buy groceries"
# Output: Task added successfully (ID: 1)
```

#### Update a task
```bash
task-cli update 1 "Buy groceries and cook dinner"
# Output: Task updated successfully (ID: 1)
```

#### Delete a task
```bash
task-cli delete 1
# Output: Task deleted successfully (ID: 1)
```

#### Mark task as in-progress
```bash
task-cli mark-in-progress 1
# Output: Task marked as in-progress (ID: 1)
```

#### Mark task as done
```bash
task-cli mark-done 1
# Output: Task marked as done (ID: 1)
```

#### List all tasks
```bash
task-cli list
```

#### List tasks by status
```bash
# List completed tasks
task-cli list done

# List pending tasks
task-cli list todo

# List tasks in progress
task-cli list in-progress
```

#### Change language
```bash
# Switch to Vietnamese
task-cli language vi

# Switch to English
task-cli language en
```

#### Show help
```bash
task-cli help
# Displays all available commands and usage examples
```

## Data Storage

Tasks are stored in a `data.json` file in the current directory. The file is automatically created on first use.

### JSON Structure
```json
{
  "nextId": 2,
  "tasks": [
    {
      "id": 0,
      "description": "Buy groceries",
      "status": "todo",
      "createdAt": "2026-02-14T10:46:39.400906100",
      "updatedAt": "2026-02-14T10:46:39.400906100"
    },
    {
      "id": 1,
      "description": "Complete project documentation",
      "status": "in-progress",
      "createdAt": "2026-02-14T10:46:44.043078900",
      "updatedAt": "2026-02-14T10:50:15.123456789"
    }
  ]
}
```

### Task Properties
- `id`: Unique identifier for the task
- `description`: Short description of the task
- `status`: Current status (`todo`, `in-progress`, `done`)
- `createdAt`: Timestamp when the task was created
- `updatedAt`: Timestamp when the task was last updated

## Troubleshooting

**Problem**: "Error: File JSON bị hỏng (corrupt)..."  
**Solution**: The app automatically creates a backup (`data.json.bak`). Delete `data.json` to start fresh or restore from backup.

**Problem**: "Error: Invalid ID format"  
**Solution**: Ensure you're providing a valid numeric ID (non-negative integer).

**Problem**: Language preference not persisting  
**Solution**: The language setting is saved in `language.config`. Ensure the file has write permissions.

## Project Structure
```
src/
├── main/java/org/de013/tasktrackercli/
│   ├── Main.java                    # Application entry point
│   ├── cli/
│   │   ├── CommandParser.java       # Parse and validate commands
│   │   └── command/                 # Command implementations
│   ├── model/
│   │   └── Task.java                # Task entity
│   ├── repository/
│   │   └── JsonTaskRepository.java  # Data persistence layer
│   ├── service/
│   │   └── TaskServiceImpl.java     # Business logic
│   └── util/
│       ├── Messages.java            # i18n support
│       ├── JsonUtil.java            # JSON parsing utilities
│       ├── TaskCommand.java         # Command constants
│       └── TaskStatus.java          # Status constants
```

## License

This project is part of the roadmap.sh backend projects curriculum.

---

**Project Link**: https://roadmap.sh/projects/task-tracker
