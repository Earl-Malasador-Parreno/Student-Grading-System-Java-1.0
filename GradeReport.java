package finals;

import java.util.ArrayList;

public class GradeReport extends Grades {
    private String subjectName;
    private String subjectCode;
    private double maxScore; 

    public GradeReport(String subjectName, String subjectCode, 
                         ArrayList<Double> quizzes, double prelim, 
                         double midterm, double semiFinal, double finalExam,
                         double maxScore) { 
        super(quizzes, prelim, midterm, semiFinal, finalExam);
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.maxScore = maxScore;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    // Calculates the final grade using weights
    public double computeFinalGrade() {
        double quizzesAvg = calculateQuizAverage();
        return quizzesAvg * 0.40 + getPrelim() * 0.10 + getMidterm() * 0.10 +
               getSemiFinal() * 0.15 + getFinalExam() * 0.25;
    }

    // Description based on numeric grade
    public String getGradeDescription() {
        return GradingSystem.getGradeDescription(computeFinalGrade());
    }

    public void displaySubjectGrades() {
        System.out.println("\nSubject: " + subjectCode + " - " + subjectName);
        System.out.println("Quizzes (" + getQuizzes().size() + "): " + getQuizzes());
        System.out.printf("Exams: Prelim=%.2f | Midterm=%.2f | Semi-Final=%.2f | Final=%.2f\n",
                getPrelim(), getMidterm(), getSemiFinal(), getFinalExam());
        System.out.printf("Final Grade: %.2f\n", computeFinalGrade());
        System.out.println("Grade Description: " + getGradeDescription());
    }

    public static void displayDetailedReport(StudentGrades studentGrades) {
        System.out.println("\n===== Detailed Grade Report for " + studentGrades.getStudentName() + " =====");
        for (SubjectGrades report : studentGrades.getSubjectGrades()) {
            report.displaySubjectGrades();
        }
    }


    public static void displayGradeSummary(StudentGrades[] students) {
        System.out.println("\n===== Grade Summary =====");
        for (StudentGrades student : students) {
            double total = 0;
            int count = 0;
            for (SubjectGrades report : student.getSubjectGrades()) {
                total += report.computeFinalGrade();
                count++;
            }
            double avg = count > 0 ? total / count : 0;
            System.out.printf("Student: %s | Subjects: %d | Average Grade: %.2f\n",
                    student.getStudentName(), count, avg);
        }
    }

}
