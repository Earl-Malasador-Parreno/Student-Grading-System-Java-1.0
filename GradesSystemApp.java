package finals;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class GradesSystemApp {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    private ArrayList<StudentGrades> allStudents = new ArrayList<>();
    private DefaultListModel<String> studentListModel = new DefaultListModel<>();
    private Map<String, ArrayList<JTextField>> subjectFieldMap = new HashMap<>();

    private JList<String> studentList;
    
    private StudentGrades currentStudent;
    private JTextArea detailsArea = new JTextArea();

    public GradesSystemApp() {
        frame = new JFrame("School Grading System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize panels
        mainPanel.add(createHomePanel(), "Home");
        mainPanel.add(createNewStudentPanel(), "NewStudent");
        mainPanel.add(createViewStudentsPanel(), "ViewStudents");
        mainPanel.add(createSubjectEntryPanel(), "SubjectEntry");
        mainPanel.add(createStudentDetailsPanel(), "StudentDetails");

        frame.add(mainPanel);
        frame.setVisible(true);
    }
    
    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Title
        JLabel title = new JLabel("School Grading System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        // Buttons
        JButton newStudentBtn = new JButton("Add Student");
        JButton viewStudentsBtn = new JButton("View Students");

        newStudentBtn.setPreferredSize(new Dimension(250, 75));
        viewStudentsBtn.setPreferredSize(new Dimension(250, 75));

        newStudentBtn.addActionListener(e -> cardLayout.show(mainPanel, "NewStudent"));
        viewStudentsBtn.addActionListener(e -> {
            refreshStudentList();
            cardLayout.show(mainPanel, "ViewStudents");
        });

        // Panel to hold the buttons side by side
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.add(newStudentBtn);
        btnPanel.add(viewStudentsBtn);

        // Wrapper panel to center btnPanel vertically and horizontally
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.add(btnPanel); // This will center it in both axes

        panel.add(centerWrapper, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createNewStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        JTextField nameField = new JTextField();
        JTextField courseField = new JTextField();
        JTextField yearField = new JTextField();
        
        formPanel.add(new JLabel("Student Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Course:"));
        formPanel.add(courseField);
        formPanel.add(new JLabel("Year Level:"));
        formPanel.add(yearField);
        
        formPanel.setPreferredSize(new Dimension(500, 100));
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitBtn = new JButton("Submit");
        JButton cancelBtn = new JButton("Cancel");
        
        submitBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String course = courseField.getText();
                int year = Integer.parseInt(yearField.getText());
                
                if (name.isEmpty() || course.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields");
                    return;
                }
                
                currentStudent = new StudentGrades(allStudents.size() + 1, name, course, year);
                allStudents.add(currentStudent);
                
                // Reset fields
                nameField.setText("");
                courseField.setText("");
                yearField.setText("");
                
                // Move to subject entry
                cardLayout.show(mainPanel, "SubjectEntry");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid year level");
            }
        });
        
        cancelBtn.addActionListener(e -> {
            nameField.setText("");
            courseField.setText("");
            yearField.setText("");
            cardLayout.show(mainPanel, "Home");
        });
        
        buttonPanel.add(submitBtn);
        buttonPanel.add(cancelBtn);
        
        JPanel formWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        formWrapper.add(formPanel);
        panel.add(formWrapper, BorderLayout.CENTER);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createViewStudentsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Student list
        studentList = new JList<>(studentListModel);
        studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(studentList);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton viewDetailsBtn = new JButton("View Details");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton backBtn = new JButton("Back");
        
        // View Details Button
        viewDetailsBtn.addActionListener(e -> {
            int selectedIndex = studentList.getSelectedIndex();
            if (selectedIndex >= 0) {
                currentStudent = allStudents.get(selectedIndex);
                loadStudentDetails();  // Load details into the detailsArea
                cardLayout.show(mainPanel, "StudentDetails");
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a student");
            }
        });

        // Edit Button
        editBtn.addActionListener(e -> {
            int selectedIndex = studentList.getSelectedIndex();
            if (selectedIndex >= 0) {
                currentStudent = allStudents.get(selectedIndex);

                // Rebuild subject entry form with existing student grades pre-filled
                for (String subject : SubjectList.getSubjects()) {
                    ArrayList<JTextField> fields = subjectFieldMap.get(subject);
                    SubjectGrades sg = currentStudent.getSubjectGrades().stream()
                        .filter(s -> s.getSubjectName().equals(subject))
                        .findFirst()
                        .orElse(null);

                    if (sg != null) {
                        for (int i = 0; i < AssessmentWeights.MAX_QUIZZES; i++) {
                            fields.get(i).setText(String.valueOf(sg.getQuizzes().get(i)));
                        }
                        fields.get(5).setText(String.valueOf(sg.getPrelim()));
                        fields.get(6).setText(String.valueOf(sg.getMidterm()));
                        fields.get(7).setText(String.valueOf(sg.getSemiFinal()));
                        fields.get(8).setText(String.valueOf(sg.getFinalExam()));
                    }
                }

                cardLayout.show(mainPanel, "SubjectEntry");
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a student to edit");
            }
        });

        // Delete Button
        deleteBtn.addActionListener(e -> {
            int selectedIndex = studentList.getSelectedIndex();
            if (selectedIndex >= 0) {
                int confirm = JOptionPane.showConfirmDialog(frame, 
                    "Are you sure you want to delete this student?", 
                    "Confirm Delete", 
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    allStudents.remove(selectedIndex);
                    refreshStudentList();  // Update the student list
                    JOptionPane.showMessageDialog(frame, "Student deleted successfully.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a student to delete");
            }
        });

        // Back Button
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        
        buttonPanel.add(viewDetailsBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(backBtn);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createSubjectEntryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Create a tab for each subject from the dynamic SubjectList
        for (String subject : SubjectList.getSubjects()) {
            JPanel subjectPanel = createSubjectTab(subject);
            tabbedPane.addTab(subject, subjectPanel);
        }
        
        // Button panel using FlowLayout to display buttons side by side
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "NewStudent")); // or "Home" if preferred
        
        JButton finishBtn = new JButton("Finish");
        finishBtn.addActionListener(e -> {
            // Clear existing subject grades (important if editing)
            currentStudent.getSubjectGrades().clear();
            
            // Use dynamic subject list
            for (String subject : SubjectList.getSubjects()) {
                ArrayList<JTextField> fields = subjectFieldMap.get(subject);
                ArrayList<Double> quizzes = new ArrayList<>();
        
                try {
                    // Parse quiz fields (support "scored/total" format)
                    for (int i = 0; i < AssessmentWeights.MAX_QUIZZES; i++) {
                        String[] parts = fields.get(i).getText().split("/");
                        if (parts.length == 2) {
                            double scored = Double.parseDouble(parts[0]);
                            double total = Double.parseDouble(parts[1]);
                            quizzes.add((scored / total) * 100); // Convert to percentage
                        } else {
                            quizzes.add(Double.parseDouble(fields.get(i).getText())); // fallback if no "/"
                        }
                    }
        
                    // Parse exam fields using parseGrade helper
                    double prelim = parseGrade(fields.get(5).getText());
                    double midterm = parseGrade(fields.get(6).getText());
                    double semiFinal = parseGrade(fields.get(7).getText());
                    double finalExam = parseGrade(fields.get(8).getText());
        
                    // Create and add subject grades
                    SubjectGrades sg = new SubjectGrades(
                    	    subject, subject, quizzes, prelim, midterm, semiFinal, finalExam
                    	);

                    currentStudent.addSubjectGrade(sg);
        
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid grades for " + subject);
                    return;
                }
            }
        
            JOptionPane.showMessageDialog(frame, "Grades saved successfully!");
            cardLayout.show(mainPanel, "Home");
        });
        
        // Add both buttons to the FlowLayout panel
        buttonPanel.add(backBtn);
        buttonPanel.add(finishBtn);
        
        panel.add(tabbedPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private double parseGrade(String input) throws NumberFormatException {
        if (input.contains("/")) {
            String[] parts = input.split("/");
            if (parts.length == 2) {
                double scored = Double.parseDouble(parts[0]);
                double total = Double.parseDouble(parts[1]);
                double percentage = (scored / total) * 100;
                return percentage;  // Return the grade as percentage
            }
        }
        return Double.parseDouble(input);  // Return the raw value if no "/" present
    }

    private JPanel createSubjectTab(String subject) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        ArrayList<JTextField> fields = new ArrayList<>();
    
        // Add quiz fields
        for (int i = 1; i <= AssessmentWeights.MAX_QUIZZES; i++) {
            panel.add(new JLabel("Quiz " + i + ":"));
            JTextField quizField = new JTextField(5);
            quizField.setPreferredSize(new Dimension(100, 25));
            fields.add(quizField);
            panel.add(quizField);
        }
    
        // Add exam fields
        String[] exams = {"Prelim Exam", "Midterm Exam", "Semi-Final Exam", "Final Exam"};
        for (String exam : exams) {
            panel.add(new JLabel(exam + ":"));
            JTextField examField = new JTextField(5);
            examField.setPreferredSize(new Dimension(100, 25));
            fields.add(examField);
            panel.add(examField);
        }
    
        // Store all fields for this subject in the map
        subjectFieldMap.put(subject, fields);
    
        return panel;
    }

    private JPanel createStudentDetailsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        detailsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backBtn = new JButton("Back");
        JButton editInfoBtn = new JButton("Edit Student Info");

        // Edit Student Info Button
        editInfoBtn.addActionListener(e -> {
            if (currentStudent != null) {
                // Pre-fill current student's details into the form
                JTextField nameField = new JTextField(currentStudent.getStudentName());
                JTextField courseField = new JTextField(currentStudent.getCourse());
                JTextField yearField = new JTextField(String.valueOf(currentStudent.getYearLevel()));
                
                JPanel editPanel = new JPanel(new GridLayout(3, 2, 10, 10));
                editPanel.add(new JLabel("Student Name:"));
                editPanel.add(nameField);
                editPanel.add(new JLabel("Course:"));
                editPanel.add(courseField);
                editPanel.add(new JLabel("Year Level:"));
                editPanel.add(yearField);
                
                int option = JOptionPane.showConfirmDialog(frame, editPanel, "Edit Student Info", JOptionPane.OK_CANCEL_OPTION);
                
                if (option == JOptionPane.OK_OPTION) {
                    // Update student's information if confirmed
                    String newName = nameField.getText();
                    String newCourse = courseField.getText();
                    int newYearLevel = Integer.parseInt(yearField.getText());
                    
                    // Update the current student's details
                    currentStudent.setStudentName(newName);
                    currentStudent.setCourse(newCourse);
                    currentStudent.setYearLevel(newYearLevel);
                    
                    // Refresh the details display
                    loadStudentDetails();
                    
                    JOptionPane.showMessageDialog(frame, "Student information updated successfully!");
                }
            }
        });

        // Back Button
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "ViewStudents"));
        
        buttonPanel.add(editInfoBtn);
        buttonPanel.add(backBtn);
        
        panel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void loadStudentDetails() {
        if (currentStudent == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s: %s\n", "Student Name", currentStudent.getStudentName()));
        sb.append(String.format("%-20s: %s\n", "Course", currentStudent.getCourse()));
        sb.append(String.format("%-20s: %d\n\n", "Year Level", currentStudent.getYearLevel()));

        sb.append("SUBJECT GRADES:\n");
        sb.append("--------------------------------------------------\n");

        // Loop through each subject grade and display only the final grade and grade point
        for (SubjectGrades subjectGrade : currentStudent.getSubjectGrades()) {
            double finalGrade = subjectGrade.getFinalGrade();
            double gradePoint = GradingSystem.getGradePoint(finalGrade);  // Get grade point for final grade
            String status = GradingSystem.isPassing(finalGrade) ? "PASSED" : "FAILED";

            sb.append(String.format("Subject: %s\n", subjectGrade.getSubjectName()));
            sb.append(String.format("Final Grade: %.2f  \nGrade Point: %.2f - %s\n", finalGrade, gradePoint, status));
            sb.append("\n");
        }

        sb.append("--------------------------------------------------\n");
        sb.append("\nOverall Average: ").append(String.format("%.2f", currentStudent.calculateAverage()));
        detailsArea.setText(sb.toString());
    }

    private void refreshStudentList() {
        studentListModel.clear();
        for (StudentGrades student : allStudents) {
            studentListModel.addElement(String.format("%-4d %-20s %-15s %-4d %.2f",
                student.getStudentNumber(),
                student.getStudentName(),
                student.getCourse(),
                student.getYearLevel(),
                student.calculateAverage()));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GradesSystemApp());
    }
}