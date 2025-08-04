package com.student.registration.controllers;

import com.student.registration.models.Student;
import com.student.registration.models.DataManager;
import com.student.registration.utils.SceneManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class StudentProfileController {

    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label programBadge;
    @FXML private Label semesterBadge;
    @FXML private Label studentIdLabel;
    @FXML private Label phoneLabel;
    @FXML private Label addressLabel;
    @FXML private Label bioLabel;

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private ComboBox<String> programComboBox;
    @FXML private ComboBox<String> semesterComboBox;
    @FXML private TextField studentIdField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private TextArea bioArea;

    @FXML private Button editSaveButton;
    @FXML private Button cancelButton;
    @FXML private Label saveMessageLabel;

    private boolean isEditing = false;
    private Student currentStudent;
    private Student originalData;

    @FXML
    public void initialize() {
        currentStudent = DataManager.getInstance().getCurrentStudent();
        if (currentStudent == null) {
            SceneManager.getInstance().switchScene("login.fxml");
            return;
        }

        // Create a backup of original data
        originalData = new Student(
            currentStudent.getUsername(),
            currentStudent.getPassword(),
            currentStudent.getName(),
            currentStudent.getEmail(),
            currentStudent.getProgram(),
            currentStudent.getSemester(),
            currentStudent.getStudentId()
        );
        originalData.setPhone(currentStudent.getPhone());
        originalData.setAddress(currentStudent.getAddress());
        originalData.setBio(currentStudent.getBio());

        // Initialize combo boxes
        programComboBox.setItems(FXCollections.observableArrayList(
            "Computer Science", "Information Technology", "Computer Engineering", 
            "Data Science", "Cybersecurity", "Software Engineering"
        ));
        
        semesterComboBox.setItems(FXCollections.observableArrayList(
            "1st Semester", "2nd Semester", "3rd Semester", "4th Semester",
            "5th Semester", "6th Semester", "7th Semester", "8th Semester"
        ));

        updateView();
    }

    private void updateView() {
        if (isEditing) {
            showEditMode();
        } else {
            showViewMode();
        }
    }

    private void showViewMode() {
        // Show labels, hide fields
        nameLabel.setVisible(true);
        emailLabel.setVisible(true);
        programBadge.setVisible(true);
        semesterBadge.setVisible(true);
        studentIdLabel.setVisible(true);
        phoneLabel.setVisible(true);
        addressLabel.setVisible(true);
        bioLabel.setVisible(true);

        nameField.setVisible(false);
        emailField.setVisible(false);
        programComboBox.setVisible(false);
        semesterComboBox.setVisible(false);
        studentIdField.setVisible(false);
        phoneField.setVisible(false);
        addressField.setVisible(false);
        bioArea.setVisible(false);

        nameField.setManaged(false);
        emailField.setManaged(false);
        programComboBox.setManaged(false);
        semesterComboBox.setManaged(false);
        studentIdField.setManaged(false);
        phoneField.setManaged(false);
        addressField.setManaged(false);
        bioArea.setManaged(false);

        // Update labels with current data
        nameLabel.setText(currentStudent.getName());
        emailLabel.setText(currentStudent.getEmail());
        programBadge.setText(currentStudent.getProgram());
        semesterBadge.setText(currentStudent.getSemester());
        studentIdLabel.setText(currentStudent.getStudentId());
        phoneLabel.setText(currentStudent.getPhone().isEmpty() ? "Not provided" : currentStudent.getPhone());
        addressLabel.setText(currentStudent.getAddress().isEmpty() ? "Not provided" : currentStudent.getAddress());
        bioLabel.setText(currentStudent.getBio().isEmpty() ? "No bio provided" : currentStudent.getBio());

        editSaveButton.setText("Edit Profile");
        editSaveButton.getStyleClass().setAll("button");
        cancelButton.setVisible(false);
        cancelButton.setManaged(false);

        saveMessageLabel.setVisible(false);
        saveMessageLabel.setManaged(false);
    }

    private void showEditMode() {
        // Hide labels, show fields
        nameLabel.setVisible(false);
        emailLabel.setVisible(false);
        programBadge.setVisible(false);
        semesterBadge.setVisible(false);
        studentIdLabel.setVisible(false);
        phoneLabel.setVisible(false);
        addressLabel.setVisible(false);
        bioLabel.setVisible(false);

        nameField.setVisible(true);
        emailField.setVisible(true);
        programComboBox.setVisible(true);
        semesterComboBox.setVisible(true);
        studentIdField.setVisible(true);
        phoneField.setVisible(true);
        addressField.setVisible(true);
        bioArea.setVisible(true);

        nameField.setManaged(true);
        emailField.setManaged(true);
        programComboBox.setManaged(true);
        semesterComboBox.setManaged(true);
        studentIdField.setManaged(true);
        phoneField.setManaged(true);
        addressField.setManaged(true);
        bioArea.setManaged(true);

        // Populate fields with current data
        nameField.setText(currentStudent.getName());
        emailField.setText(currentStudent.getEmail());
        programComboBox.getSelectionModel().select(currentStudent.getProgram());
        semesterComboBox.getSelectionModel().select(currentStudent.getSemester());
        studentIdField.setText(currentStudent.getStudentId());
        phoneField.setText(currentStudent.getPhone());
        addressField.setText(currentStudent.getAddress());
        bioArea.setText(currentStudent.getBio());

        editSaveButton.setText("Save Changes");
        editSaveButton.getStyleClass().setAll("button", "button-success");
        cancelButton.setVisible(true);
        cancelButton.setManaged(true);
    }

    @FXML
    private void handleEditSave() {
        if (isEditing) {
            // Save changes
            currentStudent.setName(nameField.getText());
            currentStudent.setEmail(emailField.getText());
            currentStudent.setProgram(programComboBox.getSelectionModel().getSelectedItem());
            currentStudent.setSemester(semesterComboBox.getSelectionModel().getSelectedItem());
            currentStudent.setStudentId(studentIdField.getText());
            currentStudent.setPhone(phoneField.getText());
            currentStudent.setAddress(addressField.getText());
            currentStudent.setBio(bioArea.getText());

            DataManager.getInstance().saveStudentProfile(currentStudent);

            saveMessageLabel.setText("Profile updated successfully!");
            saveMessageLabel.getStyleClass().setAll("alert-success");
            saveMessageLabel.setVisible(true);
            saveMessageLabel.setManaged(true);

            isEditing = false;
        } else {
            isEditing = true;
        }
        updateView();
    }

    @FXML
    private void handleCancel() {
        // Restore original data
        currentStudent.setName(originalData.getName());
        currentStudent.setEmail(originalData.getEmail());
        currentStudent.setProgram(originalData.getProgram());
        currentStudent.setSemester(originalData.getSemester());
        currentStudent.setStudentId(originalData.getStudentId());
        currentStudent.setPhone(originalData.getPhone());
        currentStudent.setAddress(originalData.getAddress());
        currentStudent.setBio(originalData.getBio());

        isEditing = false;
        updateView();
    }

    @FXML
    private void goToDashboard() {
        SceneManager.getInstance().switchScene("dashboard.fxml");
    }
}
