package com.student.registration.models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager instance;
    private Student currentStudent;
    private List<Course> availableCourses;
    
    private DataManager() {
        loadAvailableCourses();
        loadCurrentStudent();
    }
    
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
    
    public Student getCurrentStudent() { return currentStudent; }
    public void setCurrentStudent(Student student) { this.currentStudent = student; }
    
    public List<Course> getAvailableCourses() { return availableCourses; }
    
    private void loadAvailableCourses() {
        availableCourses = new ArrayList<>();
        availableCourses.add(new Course("CS101", "Introduction to Programming", "Dr. Smith", 3, "MWF 9:00-10:00"));
        availableCourses.add(new Course("CS201", "Data Structures", "Dr. Johnson", 4, "TTh 11:00-12:30"));
        availableCourses.add(new Course("CS301", "Database Management", "Dr. Brown", 3, "MWF 2:00-3:00"));
        availableCourses.add(new Course("CS401", "Software Engineering", "Dr. Davis", 4, "TTh 3:30-5:00"));
        availableCourses.add(new Course("MATH201", "Discrete Mathematics", "Dr. Wilson", 3, "MWF 10:00-11:00"));
        availableCourses.add(new Course("PHYS101", "Physics I", "Dr. Taylor", 4, "TTh 9:00-10:30"));
    }
    
    private void loadCurrentStudent() {
        // Default student for demo
        currentStudent = new Student("STU001", "John Doe", "john.doe@university.edu", "Computer Science", "Fall 2024");
    }
    
    public void saveStudentData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("student_data.txt"))) {
            writer.println(currentStudent.getStudentId());
            writer.println(currentStudent.getName());
            writer.println(currentStudent.getEmail());
            writer.println(currentStudent.getProgram());
            writer.println(currentStudent.getSemester());
            writer.println(currentStudent.getRegisteredCourses().size());
            for (Course course : currentStudent.getRegisteredCourses()) {
                writer.println(course.getCourseId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean validateLogin(String username, String password) {
        return "admin".equals(username) && "password".equals(password);
    }
}
