package com.student.registration.controllers;

import com.student.registration.models.Course;
import com.student.registration.models.DataManager;
import com.student.registration.utils.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewCoursesController implements Initializable {
    
    @FXML private TableView<Course> coursesTable;
    @FXML private TableColumn<Course, String> courseIdColumn;
    @FXML private TableColumn<Course, String> courseNameColumn;
    @FXML private TableColumn<Course, String> instructorColumn;
    @FXML private TableColumn<Course, Integer> creditsColumn;
    @FXML private TableColumn<Course, String> scheduleColumn;
    
    private ObservableList<Course> coursesList;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        loadRegisteredCourses();
    }
    
    private void setupTable() {
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        instructorColumn.setCellValueFactory(new PropertyValueFactory<>("instructor"));
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
        scheduleColumn.setCellValueFactory(new PropertyValueFactory<>("schedule"));
        
        coursesList = FXCollections.observableArrayList();
        coursesTable.setItems(coursesList);
    }
    
    private void loadRegisteredCourses() {
        coursesList.clear();
        coursesList.addAll(DataManager.getInstance().getCurrentStudent().getRegisteredCourses());
    }
    
    @FXML
    private void handleDeleteCourse() {
        Course selectedCourse = coursesTable.getSelectionModel().getSelectedItem();
        
        if (selectedCourse == null) {
            showAlert("No Selection", "Please select a course to delete.");
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Course");
        alert.setHeaderText("Are you sure you want to delete this course?");
        alert.setContentText("Course: " + selectedCourse.getCourseName());
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DataManager.getInstance().getCurrentStudent().removeCourse(selectedCourse);
            loadRegisteredCourses();
            showAlert("Success", "Course deleted successfully!");
        }
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
