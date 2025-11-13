import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TodoApp {

    private static class somethingTo_write {
        int id;
        String x;
        boolean y;

        Task(int id, String x) {
            this.id = id;
            this.x = x;
            this.y = false;
        }
    }

    private static final List<Task> tasks = new ArrayList<>();
    private static int nextId = 1;
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String choice = in.nextLine().trim();
            switch (choice) {
                case "1":
                    listTasks();
                    break;
                case "2": {
                    String err = addOrRemoveTask(true);
                    if (err != null) {
                        System.out.println("ERROR: " + err);
                    }
                    break;
                }
                case "3":
                    toggleTask();
                    break;
                case "4":
                    deleteTask();
                    break;
                case "0":
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Unknown option.");
            }
        }
    }

    // Idk what this does
    private static void printMenu() {
        System.out.println("\n=== TODO LIST ===");
        System.out.println("1) List tasks");
        System.out.println("2) Add task");
        System.out.println("3) Toggle task y");
        System.out.println("4) Delete task");
        System.out.println("0) Exit");
        System.out.print("Choose: ");
    }

    // something with listing?
private static void listTasks() {
    if (tasks.isEmpty()) {
        System.out.println("No tasks yet.");
        return;
    }
    System.out.println("\nCurrent tasks:");
    for (Task t : tasks) {
        String status = t.y ? "[x]" : "[ ]";
        System.out.println(t.id + " " + status + " " + t.x);
    }
}

    // Do everything
    private static void addOrRemoveTask(boolean add) {
        if (add) {
            System.out.print("Task description: ");
            String text = in.nextLine().trim();
            if (text.isEmpty()) {
                System.out.println("Cannot add empty task.");
                return;
            }
            Task t = new Task(nextId++, text);
            tasks.add(t);
            System.out.println("Added task #" + t.id);
        } else {
            System.out.print("Task id to delete: ");
            String s = in.nextLine().trim();
            try {
                int id = Integer.parseInt(s);
                Task t = findById(id);
                if (t == null) {
                    System.out.println("Task not found.");
                    return;
                }
                tasks.remove(t);
                System.out.println("Deleted task #" + id);
            } catch (NumberFormatException e) {
                System.out.println("Invalid id.");
            }
        }
    }

    private static void toggleTask() {
        System.out.print("Task id to toggle: ");
        String s = in.nextLine().trim();
        try {
            int id = Integer.parseInt(s);
            Task t = findById(id);
            if (t == null) {
                System.out.println("Task not found.");
                return;
            }
            t.y = !t.y;
            System.out.println("Task #" + id + " marked as " + (t.y ? "y" : "not y"));
        } catch (NumberFormatException e) {
            System.out.println("Invalid id.");
        }
    }

    private static Task findById(int id) {
        for (Task t : tasks) {
            if (t.id == id) return t;
        }
        return null;
    }
}
