package finals;

public class GradingSystem {

    // Converts percentage to grade point (example: a linear scale from 100% to 75% maps to 1.00 to 3.00)
    public static double getGradePoint(double percentage) {
        if (percentage >= 100) return 1.00;
        else if (percentage >= 75) {
            return 1.00 + ((100 - percentage) * (2.0 / 25.0));
        } else {
            return 5.00; // Failure
        }
    }

    // Determines if a grade is passing (example: grade points 3.00 or lower pass)
    public static boolean isPassing(double percentage) {
        return getGradePoint(percentage) <= 3.00;
    }

    // Method to return a grade description based on computed final grade percentage
    public static String getGradeDescription(double finalGradePercentage) {
        double gradePoint = getGradePoint(finalGradePercentage);

        if (gradePoint < 1.5) {
            return "1.00 - Excellent";
        } else if (gradePoint < 2.0) {
            return "1.25 - Very Good";
        } else if (gradePoint < 2.5) {
            return "1.50 - Good";
        } else if (gradePoint < 3.0) {
            return "1.75 - Satisfactory";
        } else if (gradePoint <= 3.00) {
            return "2.00 - Passing";
        } else {
            return "5.00 - Failure";
        }
    }

}
