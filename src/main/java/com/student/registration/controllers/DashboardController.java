package com.student.registration.controllers;

import com.student.registration.models.DataManager;
import com.student.registration.models.Student;
import com.student.registration.utils.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DashboardController {

    @FXML private Text welcomeLabel;
    @FXML private Text dateLabel;
    @FXML private Label totalCoursesLabel;
    @FXML private Label availableCoursesLabel;
    @FXML private PieChart courseChart;

    @FXML
    public void initialize() {
        Student currentStudent = DataManager.getInstance().getCurrentStudent();
        if (currentStudent == null) {
            SceneManager.getInstance().switchScene("login.fxml");
            return;
        }

        // Set welcome message
        String displayName = getDisplayName(currentStudent.getUsername());
        welcomeLabel.setText("Welcome back, " + displayName + "! 👋");

        // Set current date
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
        dateLabel.setText("Spring 2024 - " + today.format(formatter));

        updateDashboardStats();
    }

    private String getDisplayName(String username) {
        switch (username) {
            case "john.doe": return "John Doe";
            case "jane.smith": return "Jane Smith";
            case "admin": return "Administrator";
            default: return username;
        }
    }

    private void updateDashboardStats() {
        Student currentStudent = DataManager.getInstance().getCurrentStudent();
        List<String> registeredCourseCodes = currentStudent.getRegisteredCourseCodes();
        
        int registeredCount = registeredCourseCodes.size();
        int totalAvailable = DataManager.getInstance().getAllAvailableCourses().size();
        int availableCount = totalAvailable - registeredCount;

        totalCoursesLabel.setText(String.valueOf(registeredCount));
        availableCoursesLabel.setText(String.valueOf(availableCount));

        updateCourseChart(registeredCount, availableCount);
    }

    private void updateCourseChart(int registeredCount, int availableCount) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        
        if (registeredCount > 0) {
            pieChartData.add(new PieChart.Data("Registered (" + registeredCount + ")", registeredCount));
        }
        if (availableCount > 0) {
            pieChartData.add(new PieChart.Data("Available (" + availableCount + ")", availableCount));
        }
        if (registeredCount == 0 && availableCount == 0) {
            pieChartData.add(new PieChart.Data("No Data", 1));
        }

        courseChart.setData(pieChartData);
        courseChart.setTitle("Course Registration Overview");
        courseChart.setLegendVisible(true);

        // Apply custom colors
        courseChart.getData().forEach(data -> {
            if (data.getName().startsWith("Registered")) {
                data.getNode().setStyle("-fx-pie-color: #3498db;");
            } else if (data.getName().startsWith("Available")) {
                data.getNode().setStyle("-fx-pie-color: #2ecc71;");
            } else {
                data.getNode().setStyle("-fx-pie-color: #95a5a6;");
            }
        });
    }

    @FXML
    private void handleLogout() {
        DataManager.getInstance().setCurrentStudent(null);
        SceneManager.getInstance().switchScene("login.fxml");
    }

    @FXML
    private void goToProfile() {
        SceneManager.getInstance().switchScene("student-profile.fxml");
    }

    @FXML
    private void goToRegisterCourse() {
        SceneManager.getInstance().switchScene("register-course.fxml");
    }

    @FXML
    private void goToViewCourses() {
        SceneManager.getInstance().switchScene("view-courses.fxml");
    }
}
