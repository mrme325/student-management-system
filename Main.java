import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentManager manager = new StudentManager();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readIntInput("Enter your choice (1-7): ");

            switch (choice) {
                case 1:
                    handleAddStudent();
                    break;
                case 2:
                    handleDisplayAll();
                    break;
                case 3:
                    handleSearchById();
                    break;
                case 4:
                    handleSearchByName();
                    break;
                case 5:
                    handleUpdateStudent();
                    break;
                case 6:
                    handleDeleteStudent();
                    break;
                case 7:
                    System.out.println("\nExiting Student Management System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid selection. Please choose an option between 1 and 7.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n==========================================");
        System.out.println("        STUDENT MANAGEMENT SYSTEM         ");
        System.out.println("==========================================");
        System.out.println("1. Add New Student");
        System.out.println("2. Display All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Search Student by Name");
        System.out.println("5. Update Student Record");
        System.out.println("6. Delete Student Record");
        System.out.println("7. Exit");
        System.out.println("==========================================");
    }

    private static void handleAddStudent() {
        System.out.println("\n--- Add New Student ---");
        int id = readIntInput("Enter Student ID: ");

        if (manager.findStudentById(id) != null) {
            System.out.println("Error: A student with ID " + id + " already exists.");
            return;
        }

        String name = readNonEmptyString("Enter Full Name: ");
        int age = readIntInputWithRange("Enter Age (16-100): ", 16, 100);
        String course = readNonEmptyString("Enter Course/Major: ");
        double grade = readDoubleInputWithRange("Enter Grade/Marks (0.0 - 100.0): ", 0.0, 100.0);

        Student student = new Student(id, name, age, course, grade);
        if (manager.addStudent(student)) {
            System.out.println("Student record added successfully.");
        } else {
            System.out.println("Failed to add student record.");
        }
    }

    private static void handleDisplayAll() {
        System.out.println("\n--- All Student Records ---");
        List<Student> students = manager.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        for (Student s : students) {
            System.out.println(s);
        }
    }

    private static void handleSearchById() {
        System.out.println("\n--- Search Student by ID ---");
        int id = readIntInput("Enter Student ID: ");
        Student student = manager.findStudentById(id);

        if (student != null) {
            System.out.println("Record found:");
            System.out.println(student);
        } else {
            System.out.println("No student found with ID: " + id);
        }
    }

    private static void handleSearchByName() {
        System.out.println("\n--- Search Student by Name ---");
        String name = readNonEmptyString("Enter search keyword: ");
        List<Student> results = manager.searchByName(name);

        if (results.isEmpty()) {
            System.out.println("No records found matching '" + name + "'.");
        } else {
            System.out.println("Matching records (" + results.size() + "):");
            for (Student s : results) {
                System.out.println(s);
            }
        }
    }

    private static void handleUpdateStudent() {
        System.out.println("\n--- Update Student Record ---");
        int id = readIntInput("Enter Student ID to update: ");
        Student student = manager.findStudentById(id);

        if (student == null) {
            System.out.println("No student found with ID: " + id);
            return;
        }

        System.out.println("Current details: " + student);
        String name = readNonEmptyString("Enter New Name: ");
        int age = readIntInputWithRange("Enter New Age (16-100): ", 16, 100);
        String course = readNonEmptyString("Enter New Course: ");
        double grade = readDoubleInputWithRange("Enter New Grade (0.0 - 100.0): ", 0.0, 100.0);

        if (manager.updateStudent(id, name, age, course, grade)) {
            System.out.println("Student record updated successfully.");
        } else {
            System.out.println("Update failed.");
        }
    }

    private static void handleDeleteStudent() {
        System.out.println("\n--- Delete Student Record ---");
        int id = readIntInput("Enter Student ID to delete: ");

        if (manager.deleteStudent(id)) {
            System.out.println("Student record deleted successfully.");
        } else {
            System.out.println("No student found with ID: " + id);
        }
    }

    private static int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private static int readIntInputWithRange(String prompt, int min, int max) {
        while (true) {
            int value = readIntInput(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("Value must be between " + min + " and " + max + ".");
        }
    }

    private static double readDoubleInputWithRange(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                double value = Double.parseDouble(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Value must be between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid numeric value.");
            }
        }
    }

    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Field cannot be empty.");
        }
    }
}
