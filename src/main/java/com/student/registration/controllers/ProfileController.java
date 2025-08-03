package com.student.registration.controllers;

import com.student.registration.models.DataManager;
import com.student.registration.models.Student;
import com.student.registration.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField programField;
    @FXML private TextField semesterField;
    @FXML private TextField studentIdField;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadStudentData();
    }
    
    private void loadStudentData() {
        Student student = DataManager.getInstance().getCurrentStudent();
        nameField.setText(student.getName());
        emailField.setText(student.getEmail());
        programField.setText(student.getProgram());
        semesterField.setText(student.getSemester());
        studentIdField.setText(student.getStudentId());
        studentIdField.setEditable(false);
    }
    
    @FXML
    private void handleSave() {
        Student student = DataManager.getInstance().getCurrentStudent();
        student.setName(nameField.getText());
        student.setEmail(emailField.getText());
        student.setProgram(programField.getText());
        student.setSemester(semesterField.getText());
        
        showAlert("Success", "Profile updated successfully!");
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
