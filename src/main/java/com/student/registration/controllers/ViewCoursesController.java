package com.student.registration.controllers;

import com.student.registration.models.Course;
import com.student.registration.models.DataManager;
import com.student.registration.utils.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ViewCoursesController {

    @FXML private TableView<Course> coursesTable;
    @FXML private TableColumn<Course, String> actionsColumn;
    @FXML private Label courseCountLabel;
    @FXML private Label messageLabel;
    @FXML private VBox emptyStateContainer;
    @FXML private VBox tableContainer;

    private ObservableList<Course> registeredCoursesData;

    @FXML
    public void initialize() {
        loadRegisteredCourses();
        setupActionsColumn();
        updateView();
    }

    private void loadRegisteredCourses() {
        List<String> registeredCourseCodes = DataManager.getInstance().getCurrentStudent().getRegisteredCourseCodes();
        List<Course> registeredCourses = DataManager.getInstance().getAllAvailableCourses().stream()
                .filter(course -> registeredCourseCodes.contains(course.getCode()))
                .collect(Collectors.toList());

        registeredCoursesData = FXCollections.observableArrayList(registeredCourses);
        coursesTable.setItems(registeredCoursesData);
    }

    private void setupActionsColumn() {
        actionsColumn.setCellFactory(param -> new TableCell<Course, String>() {
            final Button dropButton = new Button("Drop Course");

            {
                dropButton.getStyleClass().setAll("button-danger");
                dropButton.setOnAction(event -> {
                    Course course = getTableView().getItems().get(getIndex());
                    confirmDropCourse(course);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(dropButton);
                }
            }
        });
    }

    private void updateView() {
        int courseCount = registeredCoursesData.size();
        courseCountLabel.setText(courseCount + " Course" + (courseCount != 1 ? "s" : ""));

        if (courseCount == 0) {
            emptyStateContainer.setVisible(true);
            emptyStateContainer.setManaged(true);
            tableContainer.setVisible(false);
            tableContainer.setManaged(false);
        } else {
            emptyStateContainer.setVisible(false);
            emptyStateContainer.setManaged(false);
            tableContainer.setVisible(true);
            tableContainer.setManaged(true);
        }
    }

    private void confirmDropCourse(Course course) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Drop Course");
        alert.setHeaderText("Are you sure you want to drop " + course.getName() + "?");
        alert.setContentText("This action cannot be undone and may affect your academic progress.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DataManager.getInstance().dropCourse(
                DataManager.getInstance().getCurrentStudent().getUsername(), 
                course.getCode()
            );
            registeredCoursesData.remove(course);
            updateView();
            showMessage("✅ " + course.getName() + " has been successfully dropped.", "alert-success");
        }
    }

    private void showMessage(String message, String styleClass) {
        messageLabel.setText(message);
        messageLabel.getStyleClass().setAll(styleClass);
        messageLabel.setVisible(true);
        messageLabel.setManaged(true);

        // Hide message after 5 seconds
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                javafx.application.Platform.runLater(() -> {
                    messageLabel.setVisible(false);
                    messageLabel.setManaged(false);
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    @FXML
    private void goToDashboard() {
        SceneManager.getInstance().switchScene("dashboard.fxml");
    }

    @FXML
    private void goToRegisterCourse() {
        SceneManager.getInstance().switchScene("register-course.fxml");
    }
}
