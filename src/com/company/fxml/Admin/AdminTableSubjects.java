package com.company.fxml.Admin;

import com.company.Database;
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

public class AdminTableSubjects {
    private final SimpleIntegerProperty rID;
    private final SimpleStringProperty rName;
    private Button button;

    public AdminTableSubjects(int i, String name) {
        this.rID = new SimpleIntegerProperty(i);
        this.rName = new SimpleStringProperty(name);

        this.button = new Button("Удалить");
        this.button.setOnAction((ActionEvent event) -> {
            PreparedStatement stmt = null;
            try {
                stmt = Database.connection.prepareStatement("DELETE FROM subjects WHERE name = '" + name + "'");
                stmt.execute();

                Stage stage = (Stage) button.getScene().getWindow();
                stage.close();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminWindow.fxml"));
                Parent root = (Parent) fxmlLoader.load();

                stage = new Stage();

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Панель администратора");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        });

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

    public String getrName() {
        return rName.get();
    }

    public SimpleStringProperty rNameProperty() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName.set(rName);
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
