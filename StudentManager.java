import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StudentManager {
    private List<Student> students;
    private final String dataFilePath = "students.txt";

    public StudentManager() {
        students = new ArrayList<>();
        loadDataFromFile();
    }

    public boolean addStudent(Student student) {
        if (findStudentById(student.getId()) != null) {
            return false;
        }
        students.add(student);
        saveDataToFile();
        return true;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public Student findStudentById(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public List<Student> searchByName(String query) {
        List<Student> results = new ArrayList<>();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(query.toLowerCase())) {
                results.add(s);
            }
        }
        return results;
    }

    public boolean updateStudent(int id, String name, int age, String course, double grade) {
        Student student = findStudentById(id);
        if (student != null) {
            student.setName(name);
            student.setAge(age);
            student.setCourse(course);
            student.setGrade(grade);
            saveDataToFile();
            return true;
        }
        return false;
    }

    public boolean deleteStudent(int id) {
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getId() == id) {
                iterator.remove();
                saveDataToFile();
                return true;
            }
        }
        return false;
    }

    private void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFilePath))) {
            for (Student s : students) {
                writer.write(s.getId() + "," + s.getName() + "," + s.getAge() + "," + s.getCourse() + "," + s.getGrade());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Warning: Unable to save data to file: " + e.getMessage());
        }
    }

    private void loadDataFromFile() {
        File file = new File(dataFilePath);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    int age = Integer.parseInt(parts[2].trim());
                    String course = parts[3].trim();
                    double grade = Double.parseDouble(parts[4].trim());
                    students.add(new Student(id, name, age, course, grade));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Warning: Could not read existing data file: " + e.getMessage());
        }
    }
}