package com.company.fxml.Learner;

import com.company.Database;
import com.company.TestElements.Test;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LearnerTable {
    private final SimpleIntegerProperty rID;
    private final SimpleStringProperty rName;
    private final SimpleStringProperty rSubject;
    private final SimpleIntegerProperty rScore;
    private final Integer testID;
    private final SimpleIntegerProperty rQuestions;
    private Button button;

    public LearnerTable(int i, Test test, int id) {
        this.rID = new SimpleIntegerProperty(i);
        this.rName = new SimpleStringProperty(test.getName());
        this.rSubject = new SimpleStringProperty(test.getSubject());
        this.rQuestions = new SimpleIntegerProperty(test.getQuests().size());
        this.rScore = new SimpleIntegerProperty(0);
        this.testID = id;
        this.button = new Button("Начать");
        this.button.setOnAction((ActionEvent event) -> {
            try {
                startTest(this.testID);
                Stage stage = (Stage) this.button.getScene().getWindow();
                stage.close();
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        });
    }
    private void startTest(int id) throws SQLException, IOException {
        Database.globalSTMT = Database.connection.prepareStatement("select * from tests where id = '"+ id +"'");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("startTest.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Тест: " + rName.get());
        stage.setScene(new Scene(root));

        stage.show();
    }

    public int getrID() {
        return rID.get();
    }

    public String getrSubject() {
        return rSubject.get();
    }

    public SimpleStringProperty rSubjectProperty() {
        return rSubject;
    }

    public void setrSubject(String rSubject) {
        this.rSubject.set(rSubject);
    }

    public Integer getTestID() {
        return testID;
    }

    public Integer getrScore() {
        return rScore.get();
    }

    public SimpleIntegerProperty rScoreProperty() {
        return rScore;
    }

    public void setrScore(Integer rScore) {
        this.rScore.set(rScore);
    }

    public Button getButton () {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
    public int getID() {
        return rID.get();
    }

    public SimpleIntegerProperty rIDProperty() {
        return rID;
    }

    public void setrID(int rID) {
        this.rID.set(rID);
    }

    public String getrName() {
        return rName.get();
    }

    public SimpleStringProperty rNameProperty() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName.set(rName);
    }

    public int getrQuestions() {
        return rQuestions.get();
    }

    public SimpleIntegerProperty rQuestionsProperty() {
        return rQuestions;
    }

    public void setrQuestions(int rQuestions) {
        this.rQuestions.set(rQuestions);
    }
}
