package com.company.fxml.Admin;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ClassesResultTable {
    private final SimpleIntegerProperty rID;
    private final SimpleStringProperty rLearnerName;
    private final SimpleStringProperty rTestName;
    private final SimpleIntegerProperty rResult;

    public ClassesResultTable(Integer rID, String rLearnerName, String rTestName, Integer rResult) {
        this.rID = new SimpleIntegerProperty(rID);
        this.rLearnerName = new SimpleStringProperty(rLearnerName);
        this.rTestName = new SimpleStringProperty(rTestName);
        this.rResult = new SimpleIntegerProperty(rResult);
    }

    public int getrID() {
        return rID.get();
    }

    public SimpleIntegerProperty rIDProperty() {
        return rID;
    }

    public void setrID(int rID) {
        this.rID.set(rID);
    }

    public String getrLearnerName() {
        return rLearnerName.get();
    }

    public SimpleStringProperty rLearnerNameProperty() {
        return rLearnerName;
    }

    public void setrLearnerName(String rStudentName) {
        this.rLearnerName.set(rStudentName);
    }

    public String getrTestName() {
        return rTestName.get();
    }

    public SimpleStringProperty rTestNameProperty() {
        return rTestName;
    }

    public void setrTestName(String rTestName) {
        this.rTestName.set(rTestName);
    }

    public int getrResult() {
        return rResult.get();
    }

    public SimpleIntegerProperty rResultProperty() {
        return rResult;
    }

    public void setrResult(int rResult) {
        this.rResult.set(rResult);
    }
}
