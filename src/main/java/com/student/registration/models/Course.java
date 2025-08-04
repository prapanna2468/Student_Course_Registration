package com.student.registration.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Course {
    private final SimpleStringProperty code;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty credits;
    private final SimpleStringProperty instructor;
    private final SimpleStringProperty schedule;
    private final SimpleStringProperty difficulty;
    private final SimpleIntegerProperty capacity;
    private SimpleIntegerProperty enrolled;
    private final SimpleStringProperty status;

    public Course(String code, String name, int credits, String instructor, String schedule, String difficulty, int capacity, int enrolled) {
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
        this.credits = new SimpleIntegerProperty(credits);
        this.instructor = new SimpleStringProperty(instructor);
        this.schedule = new SimpleStringProperty(schedule);
        this.difficulty = new SimpleStringProperty(difficulty);
        this.capacity = new SimpleIntegerProperty(capacity);
        this.enrolled = new SimpleIntegerProperty(enrolled);
        this.status = new SimpleStringProperty("Active");
    }

    // Getters for properties (for TableView)
    public String getCode() { return code.get(); }
    public SimpleStringProperty codeProperty() { return code; }

    public String getName() { return name.get(); }
    public SimpleStringProperty nameProperty() { return name; }

    public int getCredits() { return credits.get(); }
    public SimpleIntegerProperty creditsProperty() { return credits; }

    public String getInstructor() { return instructor.get(); }
    public SimpleStringProperty instructorProperty() { return instructor; }

    public String getSchedule() { return schedule.get(); }
    public SimpleStringProperty scheduleProperty() { return schedule; }

    public String getDifficulty() { return difficulty.get(); }
    public SimpleStringProperty difficultyProperty() { return difficulty; }

    public int getCapacity() { return capacity.get(); }
    public SimpleIntegerProperty capacityProperty() { return capacity; }

    public int getEnrolled() { return enrolled.get(); }
    public SimpleIntegerProperty enrolledProperty() { return enrolled; }

    public String getStatus() { return status.get(); }
    public SimpleStringProperty statusProperty() { return status; }

    // Setters
    public void setEnrolled(int enrolled) { this.enrolled.set(enrolled); }
    public void setStatus(String status) { this.status.set(status); }
}
