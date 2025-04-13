package finals;

import java.util.ArrayList;

public class StudentGrades {
    private int studentNumber;
    private String studentName;
    private String course;
    private int yearLevel;
    private ArrayList<SubjectGrades> subjectGrades = new ArrayList<>();

    // Constructor
    public StudentGrades(int studentNumber, String studentName, String course, int yearLevel) {
        this.studentNumber = studentNumber;
        this.studentName = studentName;
        this.course = course;
        this.yearLevel = yearLevel;
    }

    // Getters and setters
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(int yearLevel) {
        this.yearLevel = yearLevel;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public ArrayList<SubjectGrades> getSubjectGrades() {
        return subjectGrades;
    }

    // Add a subject
    public void addSubjectGrade(SubjectGrades subjectGrade) {
        subjectGrades.add(subjectGrade);
    }

    // Delete a subject by name
    public boolean deleteSubjectByName(String subjectName) {
        return subjectGrades.removeIf(subject -> subject.getSubjectName().equalsIgnoreCase(subjectName));
    }

    // Rename a subject
    public boolean renameSubject(String oldName, String newName) {
        for (SubjectGrades subject : subjectGrades) {
            if (subject.getSubjectName().equalsIgnoreCase(oldName)) {
                subject.setSubjectName(newName);
                return true;
            }
        }
        return false;
    }

    // Update subject grade by name
    public boolean updateSubjectGrade(String subjectName, SubjectGrades updatedGrade) {
        for (int i = 0; i < subjectGrades.size(); i++) {
            if (subjectGrades.get(i).getSubjectName().equalsIgnoreCase(subjectName)) {
                subjectGrades.set(i, updatedGrade);
                return true;
            }
        }
        return false;
    }

    // Calculate average of all subjects
    public double calculateAverage() {
        if (subjectGrades.isEmpty()) return 0;
        double total = 0;
        for (SubjectGrades subject : subjectGrades) {
            total += subject.getFinalGrade();
        }
        return total / subjectGrades.size();
    }

    // Display the student's detailed grades
    public void displayStudentGrades() {
        System.out.println("\n============== GRADE DETAILS ==============");
        System.out.printf("Student Name: %s\nCourse: %s\nYear Level: %d\n\n", studentName, course, yearLevel);

        for (SubjectGrades subject : subjectGrades) {
            subject.displaySubjectGrades();
        }

        System.out.printf("\nOverall Average: %.2f\n", calculateAverage());
        System.out.println("==========================================");
    }
}
