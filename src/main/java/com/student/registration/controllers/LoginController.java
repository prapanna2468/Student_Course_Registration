package com.student.registration.controllers;

import com.student.registration.models.DataManager;
import com.student.registration.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Pre-fill the credentials for demo purposes (optional)
        // usernameField.setText("prapanna");
        // passwordField.setText("123");
    }
    
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Failed", "Please enter both username and password.");
            return;
        }
        
        if (DataManager.getInstance().validateLogin(username, password)) {
            SceneManager.getInstance().switchScene("dashboard.fxml");
        } else {
            showAlert("Login Failed", "Invalid username or password.\n\nCorrect credentials:\nUsername: prapanna\nPassword: 123");
        }
    }
    
    @FXML
    private void handleKeyPressed(javafx.scene.input.KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            handleLogin();
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
