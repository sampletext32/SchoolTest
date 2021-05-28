package com.company.fxml.Teacher;

import com.company.Database;
import com.company.TestElements.Quest;
import com.company.TestElements.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowTestController implements Initializable  {
    private int counter = 0;
    public Test test;
    private int testId;
    public Text testName;
    public Button back;
    public Button next;
    public Text qText;
    public Pane answers;

    public void onNext(ActionEvent actionEvent) throws Exception {
        List<Integer> answer = new ArrayList<>();

        Quest currentQuest = test.getQuests().get(counter++);
        for (int i = 0; i < answers.getChildren().size(); i++) {
            Text currentAnswer = (Text) answers.getChildren().get(i);
            currentAnswer.setText(currentQuest.getAnswers().get(i).getText());
        }
        if (counter == test.getQuests().size()) {
            this.end();
        } else {
            currentQuest = test.getQuests().get(counter);
            qText.setText(currentQuest.getText());

            for (int i = 0; i < answers.getChildren().size(); i++) {
                Text currentAnswer = (Text) answers.getChildren().get(i);
                currentAnswer.setText(currentQuest.getAnswers().get(i).getText());
            }
        }
    }

    public void end() throws IOException, SQLException {
        Stage stage = (Stage) next.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("teacherWindow.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Панель учителя");
        stage.setScene(new Scene(root));

        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            PreparedStatement stmt = Database.globalSTMT;
            ResultSet rs = stmt.executeQuery();

            rs.next();

            this.test = mapper.readValue(rs.getString("test"), Test.class);
            this.testId = Integer.parseInt(rs.getString("id"));
            this.testName.setText(test.getName());
            this.qText.setText(test.getQuests().get(0).getText());

            for (int i = 0; i < answers.getChildren().size(); i++) {
                Text currentAnswer = (Text) answers.getChildren().get(i);
                currentAnswer.setText(test.getQuests().get(0).getAnswers().get(i).getText());
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
