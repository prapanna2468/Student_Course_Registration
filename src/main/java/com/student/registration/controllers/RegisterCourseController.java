package com.student.registration.controllers;

import com.student.registration.models.Course;
import com.student.registration.models.DataManager;
import com.student.registration.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class RegisterCourseController {

    @FXML private GridPane courseGrid;
    @FXML private Label selectedCountLabel;
    @FXML private Label messageLabel;

    private List<Course> availableCourses;
    private List<Course> selectedCourses = new ArrayList<>();

    @FXML
    public void initialize() {
        availableCourses = DataManager.getInstance().getAllAvailableCourses();
        displayCourses();
        updateSelectedCount();
    }

    private void displayCourses() {
        courseGrid.getChildren().clear();
        int row = 0;
        int col = 0;

        for (Course course : availableCourses) {
            VBox courseCard = createCourseCard(course);
            courseGrid.add(courseCard, col, row);

            col++;
            if (col == 2) {
                col = 0;
                row++;
            }
        }
    }

    private VBox createCourseCard(Course course) {
        VBox card = new VBox(15);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(25));
        card.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(card, Priority.ALWAYS);
        HBox.setHgrow(card, Priority.ALWAYS);

        // Course title and difficulty
        HBox titleRow = new HBox(15);
        titleRow.setAlignment(Pos.CENTER_LEFT);
        
        Label titleLabel = new Label(course.getName());
        titleLabel.getStyleClass().add("card-header");
        HBox.setHgrow(titleLabel, Priority.ALWAYS);

        Label difficultyBadge = new Label(course.getDifficulty());
        difficultyBadge.getStyleClass().add("badge");
        switch (course.getDifficulty()) {
            case "Beginner":
                difficultyBadge.getStyleClass().add("badge-green");
                break;
            case "Intermediate":
                difficultyBadge.getStyleClass().add("badge-yellow");
                break;
            case "Advanced":
                difficultyBadge.getStyleClass().add("badge-red");
                break;
        }

        titleRow.getChildren().addAll(titleLabel, difficultyBadge);

        // Course details
        Label codeLabel = new Label(course.getCode() + " • " + course.getCredits() + " Credits");
        codeLabel.getStyleClass().add("card-description");

        Label instructorLabel = new Label("👨‍🏫 " + course.getInstructor());
        instructorLabel.getStyleClass().add("label");

        Label scheduleLabel = new Label("🕒 " + course.getSchedule());
        scheduleLabel.getStyleClass().add("label");

        // Availability
        int remainingSpots = course.getCapacity() - course.getEnrolled();
        Label availabilityLabel = new Label("👥 " + course.getEnrolled() + "/" + course.getCapacity() + " enrolled");
        availabilityLabel.getStyleClass().add("label");

        if (remainingSpots <= 5 && remainingSpots > 0) {
            availabilityLabel.setText(availabilityLabel.getText() + " (⚠️ Only " + remainingSpots + " spots left!)");
            availabilityLabel.getStyleClass().add("text-orange");
        } else if (remainingSpots == 0) {
            availabilityLabel.setText(availabilityLabel.getText() + " (🚫 Full)");
            availabilityLabel.getStyleClass().add("text-red");
        } else {
            availabilityLabel.getStyleClass().add("text-green");
        }

        // Registration status and checkbox
        boolean isRegistered = DataManager.getInstance().getCurrentStudent()
                .getRegisteredCourseCodes().contains(course.getCode());
        
        HBox actionRow = new HBox(10);
        actionRow.setAlignment(Pos.CENTER_LEFT);

        if (isRegistered) {
            Label registeredBadge = new Label("✅ Already Registered");
            registeredBadge.getStyleClass().addAll("badge", "badge-blue");
            actionRow.getChildren().add(registeredBadge);
        } else if (remainingSpots == 0) {
            Label fullBadge = new Label("🚫 Course Full");
            fullBadge.getStyleClass().addAll("badge", "badge-red");
            actionRow.getChildren().add(fullBadge);
        } else {
            CheckBox selectCheckbox = new CheckBox("Select for Registration");
            selectCheckbox.setSelected(selectedCourses.contains(course));
            selectCheckbox.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    if (!selectedCourses.contains(course)) {
                        selectedCourses.add(course);
                    }
                } else {
                    selectedCourses.remove(course);
                }
                updateSelectedCount();
            });
            actionRow.getChildren().add(selectCheckbox);
        }

        card.getChildren().addAll(titleRow, codeLabel, instructorLabel, scheduleLabel, availabilityLabel, actionRow);
        return card;
    }

    private void updateSelectedCount() {
        selectedCountLabel.setText(selectedCourses.size() + " Selected");
    }

    @FXML
    private void handleRegister() {
        if (selectedCourses.isEmpty()) {
            showMessage("Please select at least one course to register.", "alert-warning");
            return;
        }

        List<String> successfullyRegistered = new ArrayList<>();
        List<String> alreadyRegistered = new ArrayList<>();

        for (Course course : selectedCourses) {
            if (DataManager.getInstance().getCurrentStudent().getRegisteredCourseCodes().contains(course.getCode())) {
                alreadyRegistered.add(course.getName());
            } else {
                DataManager.getInstance().registerCourse(
                    DataManager.getInstance().getCurrentStudent().getUsername(), 
                    course.getCode()
                );
                successfullyRegistered.add(course.getName());
            }
        }

        selectedCourses.clear();
        displayCourses();
        updateSelectedCount();

        if (!successfullyRegistered.isEmpty()) {
            showMessage("✅ Successfully registered for: " + String.join(", ", successfullyRegistered), "alert-success");
        }
        if (!alreadyRegistered.isEmpty()) {
            showMessage("⚠️ Already registered for: " + String.join(", ", alreadyRegistered), "alert-warning");
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
}

