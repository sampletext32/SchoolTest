package com.company.fxml.Learner;

import com.company.TestElements.Test;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class ShowResultController{
    public Text testName;
    public Text testState;
    public Text score;
    public Button btn;
    public Test test;

    public void onClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("learnerWindow.fxml"));
        stage = new Stage();
        stage.setTitle("Панель ученика");
        stage.initModality(Modality.APPLICATION_MODAL);
        Parent root = (Parent) fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
