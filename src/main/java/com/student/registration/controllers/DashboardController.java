package com.student.registration.controllers;

import com.student.registration.models.DataManager;
import com.student.registration.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.application.Platform;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    
    @FXML private Label welcomeLabel;
    @FXML private Label totalCoursesLabel;
    @FXML private PieChart courseChart;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String studentName = DataManager.getInstance().getCurrentStudent().getName();
        welcomeLabel.setText("Welcome, " + studentName + "!");
        
        updateDashboard();
    }
    
    private void updateDashboard() {
        int totalCourses = DataManager.getInstance().getCurrentStudent().getRegisteredCourses().size();
        totalCoursesLabel.setText("Total Registered Courses: " + totalCourses);
        
        // Update pie chart
        courseChart.getData().clear();
        courseChart.getData().add(new PieChart.Data("Registered", totalCourses));
        courseChart.getData().add(new PieChart.Data("Available", 
            DataManager.getInstance().getAvailableCourses().size() - totalCourses));
    }
    
    @FXML
    private void goToProfile() {
        SceneManager.getInstance().switchScene("profile.fxml");
    }
    
    @FXML
    private void goToRegisterCourse() {
        SceneManager.getInstance().switchScene("register-course.fxml");
    }
    
    @FXML
    private void goToViewCourses() {
        SceneManager.getInstance().switchScene("view-courses.fxml");
    }
    
    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("Any unsaved changes will be lost.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            SceneManager.getInstance().switchScene("login.fxml");
        }
    }
    
    @FXML
    private void handleExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Application");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("The application will be closed.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DataManager.getInstance().saveStudentData();
            Platform.exit();
        }
    }
}
