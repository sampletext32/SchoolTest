package com.company.fxml.Teacher;

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
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TeacherTable {
    private final SimpleIntegerProperty rID;
    private final SimpleStringProperty rName;
    private final Integer testID;
    private final SimpleIntegerProperty rQuestions;
    private Button removeButton;
    private Button showButton;

    public TeacherTable(int i, Test test, int id) {
        this.rID = new SimpleIntegerProperty(i);
        this.rName = new SimpleStringProperty(test.getName());
        this.rQuestions = new SimpleIntegerProperty(test.getQuests().size());
        this.testID = id;
        this.removeButton = new Button("Удалить");
        this.removeButton.setOnAction((ActionEvent event) -> {
            PreparedStatement stmt = null;
            try {
                stmt = Database.connection.prepareStatement("DELETE FROM tests WHERE id = '" + id + "'");
                stmt.execute();

                Stage stage = (Stage) removeButton.getScene().getWindow();
                stage.close();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("teacherWindow.fxml"));
                Parent root = (Parent) fxmlLoader.load();

                stage = new Stage();

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Панель учителя");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        });
        this.showButton = new Button("Посмотреть");
        this.showButton.setOnAction((ActionEvent event) -> {
            try {
                Database.globalSTMT = Database.connection.prepareStatement("select * from tests where id = '" + id + "'");
                Stage stage = (Stage) showButton.getScene().getWindow();
                stage.close();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showTest.fxml"));
                Parent root = (Parent) fxmlLoader.load();

                stage = new Stage();

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Просмотр теста");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public Integer getTestID() {
        return testID;
    }

    public Button getShowButton() {
        return showButton;
    }

    public void setShowButton(Button showButton) {
        this.showButton = showButton;
    }

    public Button getRemoveButton () {
        return removeButton;
    }

    public void setRemoveButton(Button button) {
        this.removeButton = button;
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
