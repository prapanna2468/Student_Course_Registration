package com.student.registration.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {
    private static DataManager instance;
    private Map<String, Student> students;
    private Map<String, Course> courses;
    private Student currentStudent;

    private DataManager() {
        students = new HashMap<>();
        courses = new HashMap<>();
        initializeData();
    }

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private void initializeData() {
        // Initialize Students
        Student john = new Student("john.doe", "password123", "John Doe", "john.doe@university.edu", 
                                 "Computer Science", "6th Semester", "CS2021001");
        john.setPhone("+1 (555) 123-4567");
        john.setAddress("123 University Ave, College Town, CT 12345");
        john.setBio("Passionate computer science student with interests in web development and artificial intelligence.");
        students.put(john.getUsername(), john);

        Student jane = new Student("jane.smith", "student456", "Jane Smith", "jane.smith@university.edu", 
                                 "Information Technology", "4th Semester", "IT2022015");
        jane.setPhone("+1 (555) 987-6543");
        jane.setAddress("456 Campus Drive, University City, UC 67890");
        jane.setBio("IT student focused on cybersecurity and database management systems.");
        students.put(jane.getUsername(), jane);

        Student admin = new Student("admin", "admin123", "Administrator", "admin@university.edu", 
                                  "Administration", "N/A", "ADMIN001");
        students.put(admin.getUsername(), admin);

        // Initialize Courses
        courses.put("CS301", new Course("CS301", "Advanced Java Programming", 4, "Dr. Sarah Johnson", 
                                      "Mon, Wed, Fri 10:00-11:00 AM", "Advanced", 30, 22));
        courses.put("CS302", new Course("CS302", "Database Management Systems", 3, "Prof. Michael Chen", 
                                      "Tue, Thu 2:00-3:30 PM", "Intermediate", 35, 28));
        courses.put("DS201", new Course("DS201", "Python for Data Science", 3, "Dr. Emily Rodriguez", 
                                      "Mon, Wed 1:00-2:30 PM", "Beginner", 25, 18));
        courses.put("CS201", new Course("CS201", "Web Development Fundamentals", 4, "Mr. David Kim", 
                                      "Tue, Thu 10:00-12:00 PM", "Beginner", 40, 35));
        courses.put("AI401", new Course("AI401", "Machine Learning", 4, "Dr. Lisa Wang", 
                                      "Mon, Wed, Fri 3:00-4:00 PM", "Advanced", 20, 15));
        courses.put("CS401", new Course("CS401", "Cybersecurity Fundamentals", 3, "Prof. Robert Taylor", 
                                      "Tue, Thu 4:00-5:30 PM", "Intermediate", 30, 20));
        courses.put("CS351", new Course("CS351", "Mobile App Development", 4, "Ms. Jennifer Lee", 
                                      "Mon, Wed 4:00-6:00 PM", "Intermediate", 25, 19));
        courses.put("CS451", new Course("CS451", "Cloud Computing", 3, "Dr. Mark Anderson", 
                                      "Fri 1:00-4:00 PM", "Advanced", 28, 16));

        // Assign initial courses
        john.addRegisteredCourse("CS301");
        john.addRegisteredCourse("DS201");
        john.addRegisteredCourse("CS302");

        jane.addRegisteredCourse("CS201");
        jane.addRegisteredCourse("CS351");
    }

    public boolean authenticateStudent(String username, String password) {
        Student student = students.get(username);
        if (student != null && student.getPassword().equals(password)) {
            currentStudent = student;
            return true;
        }
        return false;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student student) {
        this.currentStudent = student;
    }

    public List<Course> getAllAvailableCourses() {
        return new ArrayList<>(courses.values());
    }

    public void registerCourse(String username, String courseCode) {
        Student student = students.get(username);
        Course course = courses.get(courseCode);
        if (student != null && course != null && !student.getRegisteredCourseCodes().contains(courseCode)) {
            student.addRegisteredCourse(courseCode);
            course.setEnrolled(course.getEnrolled() + 1);
        }
    }

    public void dropCourse(String username, String courseCode) {
        Student student = students.get(username);
        Course course = courses.get(courseCode);
        if (student != null && course != null && student.getRegisteredCourseCodes().contains(courseCode)) {
            student.removeRegisteredCourse(courseCode);
            course.setEnrolled(Math.max(0, course.getEnrolled() - 1));
        }
    }

    public void saveStudentProfile(Student updatedStudent) {
        if (students.containsKey(updatedStudent.getUsername())) {
            students.put(updatedStudent.getUsername(), updatedStudent);
            if (currentStudent != null && currentStudent.getUsername().equals(updatedStudent.getUsername())) {
                currentStudent = updatedStudent;
            }
        }
    }

    public Course getCourse(String courseCode) {
        return courses.get(courseCode);
    }
}
