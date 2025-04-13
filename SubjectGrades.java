package finals;

import java.util.ArrayList;

public class SubjectGrades extends Grades {
    private String subjectName;
    private String subjectCode;

    public SubjectGrades(String subjectName, String subjectCode, 
                         ArrayList<Double> quizzes, double prelim, 
                         double midterm, double semiFinal, double finalExam) {
        super(quizzes, prelim, midterm, semiFinal, finalExam);
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
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
    
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
