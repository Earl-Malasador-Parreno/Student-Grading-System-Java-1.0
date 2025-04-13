package finals;

import java.util.ArrayList;
import java.util.List;

public class SubjectList {
    private static List<String> subjects = new ArrayList<>();

    static {
        // Initialize with default subjects
        subjects.add("HUM 101");
        subjects.add("HIST 101");
        subjects.add("NSTP 102");
        subjects.add("CC-DISCRETE 12");
        subjects.add("CC-COMPROG 12");
        subjects.add("ENG 101");
        subjects.add("PSYCH 101");
        subjects.add("PE 102");
    }

    // Returns a copy of subjects
    public static List<String> getSubjects() {
        return new ArrayList<>(subjects);
    }

    // Adds a new subject to the list
    public static void addSubject(String subject) {
        subjects.add(subject);
    }

    // Removes a subject from the list
    public static void removeSubject(String subject) {
        subjects.remove(subject);
    }

    // Renames an existing subject
    public static void renameSubject(String oldName, String newName) {
        int index = subjects.indexOf(oldName);
        if (index != -1) {
            subjects.set(index, newName);
        }
    }

    // Check if a subject already exists
    public static boolean containsSubject(String subject) {
        return subjects.contains(subject);
    }
}
