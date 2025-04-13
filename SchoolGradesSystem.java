package finals;

import java.util.Scanner;

public class SchoolGradesSystem {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int numStudents = input.nextInt();
        input.nextLine(); // consume newline

        StudentGrades[] students = new StudentGrades[numStudents];

        // Input student data
        for (int i = 0; i < numStudents; i++) {
            System.out.print("\nEnter name for Student " + (i + 1) + ": ");
            String name = input.nextLine();
            
            System.out.print("Enter course: ");
            String course = input.nextLine();
            
            System.out.print("Enter year level: ");
            int yearLevel = input.nextInt();
            input.nextLine(); // consume newline
            
            students[i] = new StudentGrades(i + 1, name, course, yearLevel);
        }

        // Display summary
        GradeReport.displayGradeSummary(students);

        // Detailed view option
        System.out.print("\nEnter student number for details (0 to exit): ");
        int choice = input.nextInt();
        while (choice != 0) {
            if (choice > 0 && choice <= students.length) {
                GradeReport.displayDetailedReport(students[choice - 1]);
            } else {
                System.out.println("Invalid student number!");
            }
            System.out.print("\nEnter student number for details (0 to exit): ");
            choice = input.nextInt();
        }

        System.out.println("Program exited. Thank you!");
        input.close();
    }
}
