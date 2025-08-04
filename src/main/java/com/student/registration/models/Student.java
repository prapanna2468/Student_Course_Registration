package com.student.registration.models;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String username;
    private String password;
    private String name;
    private String email;
    private String program;
    private String semester;
    private String studentId;
    private String phone;
    private String address;
    private String bio;
    private List<String> registeredCourseCodes;

    public Student(String username, String password, String name, String email, String program, String semester, String studentId) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.program = program;
        this.semester = semester;
        this.studentId = studentId;
        this.registeredCourseCodes = new ArrayList<>();
        this.phone = "";
        this.address = "";
        this.bio = "";
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getProgram() { return program; }
    public String getSemester() { return semester; }
    public String getStudentId() { return studentId; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getBio() { return bio; }
    public List<String> getRegisteredCourseCodes() { return registeredCourseCodes; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setProgram(String program) { this.program = program; }
    public void setSemester(String semester) { this.semester = semester; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setBio(String bio) { this.bio = bio; }
    
    public void addRegisteredCourse(String courseCode) {
        if (!registeredCourseCodes.contains(courseCode)) {
            registeredCourseCodes.add(courseCode);
        }
    }
    
    public void removeRegisteredCourse(String courseCode) {
        registeredCourseCodes.remove(courseCode);
    }
}
