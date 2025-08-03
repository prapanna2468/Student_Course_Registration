package com.student.registration.controllers;

import com.student.registration.models.Course;
import com.student.registration.models.DataManager;
import com.student.registration.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;  // Changed from javafx.scene.control.VBox

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterCourseController implements Initializable {
    
    @FXML private VBox coursesContainer;
    private List<CheckBox> courseCheckBoxes;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseCheckBoxes = new ArrayList<>();
        loadAvailableCourses();
    }
    
    private void loadAvailableCourses() {
        List<Course> availableCourses = DataManager.getInstance().getAvailableCourses();
        List<Course> registeredCourses = DataManager.getInstance().getCurrentStudent().getRegisteredCourses();
        
        for (Course course : availableCourses) {
            CheckBox checkBox = new CheckBox(course.getCourseName() + " - " + course.getInstructor() + 
                " (" + course.getCredits() + " credits) - " + course.getSchedule());
            checkBox.setUserData(course);
            checkBox.setSelected(registeredCourses.contains(course));
            checkBox.getStyleClass().add("course-checkbox");
            courseCheckBoxes.add(checkBox);
            coursesContainer.getChildren().add(checkBox);
        }
    }
    
    @FXML
    private void handleRegister() {
        List<Course> selectedCourses = new ArrayList<>();
        
        for (CheckBox checkBox : courseCheckBoxes) {
            if (checkBox.isSelected()) {
                selectedCourses.add((Course) checkBox.getUserData());
            }
        }
        
        DataManager.getInstance().getCurrentStudent().setRegisteredCourses(selectedCourses);
        showAlert("Success", "Course registration updated successfully!");
    }
    
    @FXML
    private void goBack() {
        SceneManager.getInstance().switchScene("dashboard.fxml");
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
