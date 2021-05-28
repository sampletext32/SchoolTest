package com.company.fxml.Admin;

import com.company.Database;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminTableClasses {
    private final SimpleIntegerProperty rID;
    private final SimpleIntegerProperty rSize;
    private final SimpleStringProperty rName;
    private Button removeButton;
    private Button showButton;

    public AdminTableClasses(int i, String name, int size) {
        this.rID = new SimpleIntegerProperty(i);
        this.rSize = new SimpleIntegerProperty(size);
        this.rName = new SimpleStringProperty(name);

        this.removeButton = new Button("Удалить");
        this.removeButton.setOnAction((e) -> {
            PreparedStatement stmt = null;
            try {
                stmt = Database.connection.prepareStatement("DELETE FROM test_system.groups WHERE name = '" + name + "'");
                stmt.execute();

                Stage stage = (Stage) removeButton.getScene().getWindow();
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

        this.showButton = new Button("Посмотреть результат");
        this.showButton.setOnAction((e) -> {
            try {
            Stage stage = (Stage) removeButton.getScene().getWindow();
            stage.close();

            Database.globalSTMT = Database.connection.prepareStatement("select * from results where group_name = '" + name + "'");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showGroupResult.fxml"));
            Parent root = null;

            root = (Parent) fxmlLoader.load();

            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Просмотр результатов класса " + getrName());
            stage.setScene(new Scene(root));
            stage.show();
            } catch (IOException | SQLException ioException) {
                ioException.printStackTrace();
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

    public int getrSize() {
        return rSize.get();
    }

    public SimpleIntegerProperty rSizeProperty() {
        return rSize;
    }

    public void setrSize(int rSize) {
        this.rSize.set(rSize);
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

    public Button getRemoveButton() {
        return removeButton;
    }

    public void setRemoveButton(Button removeButton) {
        this.removeButton = removeButton;
    }

    public Button getShowButton() {
        return showButton;
    }

    public void setShowButton(Button showButton) {
        this.showButton = showButton;
    }
}
