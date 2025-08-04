package com.student.registration;

import com.student.registration.utils.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize the SceneManager with the primary stage
        SceneManager.getInstance().setPrimaryStage(primaryStage);

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primaryStage.setTitle("Student Course Registration System");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true); // Start maximized instead of fullscreen for better UX
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
