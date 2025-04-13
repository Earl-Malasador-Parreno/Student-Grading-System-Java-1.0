package finals;

import java.util.ArrayList;

public class Grades {
    private ArrayList<Double> quizzes;
    private double prelim;
    private double midterm;
    private double semiFinal;
    private double finalExam;

    public Grades(ArrayList<Double> quizzes, double prelim, 
                  double midterm, double semiFinal, double finalExam) {
        this.quizzes = new ArrayList<>(quizzes);
        this.prelim = prelim;
        this.midterm = midterm;
        this.semiFinal = semiFinal;
        this.finalExam = finalExam;
    }

    public double calculateQuizAverage() {
        return quizzes.stream()
                      .mapToDouble(Double::doubleValue)
                      .average()
                      .orElse(0);
    }

    // calculates the final grade dynamically
    public double getFinalGrade() {
        double quizAverage = calculateQuizAverage();
        return (quizAverage * AssessmentWeights.QUIZ_WEIGHT) +
               (prelim * AssessmentWeights.PRELIM_WEIGHT) +
               (midterm * AssessmentWeights.MIDTERM_WEIGHT) +
               (semiFinal * AssessmentWeights.SEMI_FINAL_WEIGHT) +
               (finalExam * AssessmentWeights.FINAL_WEIGHT);
    }

    // Getters
    public ArrayList<Double> getQuizzes() { return new ArrayList<>(quizzes); }
    public double getPrelim() { return prelim; }
    public double getMidterm() { return midterm; }
    public double getSemiFinal() { return semiFinal; }
    public double getFinalExam() { return finalExam; }
}
