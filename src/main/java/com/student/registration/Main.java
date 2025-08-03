package com.student.registration;

import com.student.registration.utils.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        SceneManager.getInstance().setPrimaryStage(primaryStage);
        primaryStage.setTitle("Student Course Registration System");
        primaryStage.setResizable(false);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        
        // Load login scene
        SceneManager.getInstance().switchScene("login.fxml");
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
