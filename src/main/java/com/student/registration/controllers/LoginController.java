package com.student.registration.controllers;

import com.student.registration.models.DataManager;
import com.student.registration.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (DataManager.getInstance().validateLogin(username, password)) {
            SceneManager.getInstance().switchScene("dashboard.fxml");
        } else {
            showAlert("Login Failed", "Invalid username or password. Use 'admin' and 'password'.");
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
