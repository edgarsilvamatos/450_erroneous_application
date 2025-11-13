import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TodoApp {

    private static final class Task {
        private final int id;
        private final String description;
        private boolean completed;

        Task(int id, String description) {
            this.id = id;
            this.description = description;
            this.completed = false;
        }

        void toggleCompletion() {
            completed = !completed;
        }
    }

    private static final List<Task> TASKS = new ArrayList<>();
    private static int nextTaskId = 1;
    private static final Scanner INPUT = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = INPUT.nextLine().trim();
            switch (choice) {
                case "1":
                    listTasks();
                    break;
                case "2":
                    addTask();
                    break;
                case "3":
                    toggleTaskCompletion();
                    break;
                case "4":
                    deleteTask();
                    break;
                case "0":
                    System.out.println("Bye!");
                    running = false;
                    break;
                default:
                    System.out.println("Unknown option.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== TODO LIST ===");
        System.out.println("1) List tasks");
        System.out.println("2) Add task");
        System.out.println("3) Toggle task completion");
        System.out.println("4) Delete task");
        System.out.println("0) Exit");
        System.out.print("Choose: ");
    }

    private static void listTasks() {
        if (TASKS.isEmpty()) {
            System.out.println("No tasks yet.");
            return;
        }

        System.out.println("\nCurrent tasks:");
        for (Task task : TASKS) {
            String status = task.completed ? "[x]" : "[ ]";
            System.out.println(task.id + " " + status + " " + task.description);
        }
    }

    private static void addTask() {
        System.out.print("Task description: ");
        String description = INPUT.nextLine().trim();
        if (description.isEmpty()) {
            System.out.println("Cannot add empty task.");
            return;
        }

        Task task = new Task(nextTaskId++, description);
        TASKS.add(task);
        System.out.println("Added task #" + task.id);
    }

    private static void toggleTaskCompletion() {
        Task task = promptForTaskById("Task id to toggle: ");
        if (task == null) {
            return;
        }

        task.toggleCompletion();
        System.out.println("Task #" + task.id + " marked as " + (task.completed ? "done" : "not done"));
    }

    private static void deleteTask() {
        Task task = promptForTaskById("Task id to delete: ");
        if (task == null) {
            return;
        }

        TASKS.remove(task);
        System.out.println("Deleted task #" + task.id);
    }

    private static Task promptForTaskById(String prompt) {
        System.out.print(prompt);
        String rawInput = INPUT.nextLine().trim();
        if (rawInput.isEmpty()) {
            System.out.println("Id cannot be empty.");
            return null;
        }

        try {
            int taskId = Integer.parseInt(rawInput);
            Task task = findTaskById(taskId);
            if (task == null) {
                System.out.println("Task not found.");
            }
            return task;
        } catch (NumberFormatException e) {
            System.out.println("Invalid id.");
            return null;
        }
    }

    private static Task findTaskById(int taskId) {
        for (Task task : TASKS) {
            if (task.id == taskId) {
                return task;
            }
        }
        return null;
    }
}
