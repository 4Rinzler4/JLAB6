import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Student {
    private String lastName;
    private String firstName;
    private String studentId;
    private int[] grades;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int[] getGrades() {
        return grades;
    }

    public void setGrades(int[] grades) {
        this.grades = grades;
    }

    public double calculateAverageGrade() {
        return grades != null ? Arrays.stream(grades).average().orElse(0) : 0;
    }

    public boolean hasUnsatisfactoryGrades() {
        return grades != null && Arrays.stream(grades).anyMatch(grade -> grade < 60);
    }
}

public class lab6_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Student> students = new ArrayList<>();

        boolean continueExecution = true;
        while (continueExecution) {
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Введіть дані для студента " + (i + 1) + ":");
                    Student student = new Student();

                    boolean validInput = false;
                    while (!validInput) {
                        System.out.print("Прізвище (без пробілів і цифр): ");
                        String lastName = scanner.nextLine();
                        if (lastName == null || lastName.trim().isEmpty() || lastName.matches(".*\\d.*")) {
                            System.out.println("Прізвище повинно бути текстовим значенням і не містити пробілів чи цифр.");
                        } else {
                            student.setLastName(lastName);
                            validInput = true;
                        }
                    }

                    validInput = false;
                    while (!validInput) {
                        System.out.print("Ім'я (без пробілів і цифр): ");
                        String firstName = scanner.nextLine();
                        if (firstName == null || firstName.trim().isEmpty() || firstName.matches(".*\\d.*")) {
                            System.out.println("Ім'я повинно бути текстовим значенням і не містити пробілів чи цифр.");
                        } else {
                            student.setFirstName(firstName);
                            validInput = true;
                        }
                    }

                    validInput = false;
                    while (!validInput) {
                        System.out.print("Номер залікової книжки (тільки цифри): ");
                        String studentId = scanner.nextLine();
                        if (studentId == null || studentId.trim().isEmpty() || !studentId.matches("\\d+")) {
                            System.out.println("Номер залікової книжки повинен містити тільки цифри.");
                        } else {
                            student.setStudentId(studentId);
                            validInput = true;
                        }
                    }

                    validInput = false;
                    while (!validInput) {
                        System.out.println("Оцінки за 5 предметів (введіть через пробіл): ");
                        String[] gradesInput = scanner.nextLine().split(" ");
                        if (gradesInput.length != 5 || !Arrays.stream(gradesInput).allMatch(grade -> grade.matches("\\d+"))) {
                            System.out.println("Некоректний ввід оцінок. Повторіть спробу.");
                        } else {
                            int[] grades = Arrays.stream(gradesInput).mapToInt(Integer::parseInt).toArray();
                            student.setGrades(grades);

                            if (Arrays.stream(grades).anyMatch(grade -> grade < 0 || grade > 100)) {
                                System.out.println("Оцінки повинні бути в діапазоні від 0 до 100. Повторіть спробу.");
                            } else {
                                validInput = true;
                            }
                        }
                    }

                    students.add(student);
                }

                students.sort((s1, s2) -> Double.compare(s1.calculateAverageGrade(), s2.calculateAverageGrade()));

                System.out.println("Прізвище\t\tІм'я\tНомер залікової книжки\tСередній бал");
                for (Student student : students) {
                    System.out.printf("%-15s\t%-10s\t%-20s\t%.2f%n",
                            student.getLastName(), student.getFirstName(), student.getStudentId(), student.calculateAverageGrade());
                }

                long unsatisfactoryCount = students.stream().filter(Student::hasUnsatisfactoryGrades).count();
                double unsatisfactoryPercentage = (double) unsatisfactoryCount / students.size() * 100;
                System.out.printf("%nВідсоток студентів з незадовільними оцінками: %.2f%%%n", unsatisfactoryPercentage);

                System.out.print("Бажаєте продовжити роботу? (1 - так, 0 - ні): ");
                int continueChoice = scanner.nextInt();
                continueExecution = continueChoice == 1;
                scanner.nextLine();
            } catch (Exception ex) {
                System.out.println("Помилка: " + ex.getMessage());
                scanner.nextLine();
            }
        }

        scanner.close();
    }
}
