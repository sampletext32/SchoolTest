package com.company.fxml.Learner;

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
import javafx.scene.control.CheckBox;
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
import java.util.Locale;
import java.util.ResourceBundle;

public class LearnerTestController implements Initializable  {
    public Text error;
    private int counter = 0;
    public Test test;
    private int testId;
    public Text testName;
    public Button back;
    public Button next;
    public Text qText;
    public Pane checks;

    public void onNext(ActionEvent actionEvent) throws Exception {
        List<Integer> answer = new ArrayList<>();
        Quest currentQuest = test.getQuests().get(counter);
        for (int i = 0; i < checks.getChildren().size(); i++) {
            CheckBox currentCheck = (CheckBox) checks.getChildren().get(i);
            if (currentCheck.isSelected()) {
                answer.add(i);
            }
        }
         if (answer.size() != 0) {
             test.addScores(currentQuest.checkAnswer(answer));

             counter++;
             if (counter == test.getQuests().size()) {
                 this.end();
             } else {
                 currentQuest = test.getQuests().get(counter);
                 qText.setText(currentQuest.getText());

                 for (int i = 0; i < checks.getChildren().size(); i++) {
                     ((CheckBox) checks.getChildren().get(i))
                             .setText(currentQuest.getAnswers().get(i).getText());
                     ((CheckBox) checks.getChildren().get(i))
                             .setSelected(false);
                 }
             }
         } else {
             error.setText("Вы должны выбрать ответ");
         }

    }

    public void end() throws IOException, SQLException {
        Stage stage = (Stage) next.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showResult.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Тест: " + test.getName());
        stage.setScene(new Scene(root));

        ShowResultController controller = fxmlLoader.getController();
        controller.test = test;
        controller.testName.setText(test.getName());
        controller.score.setText(String.format("%.2f/%d" ,test.getScore(), test.getMaxScore()));
        controller.testState.setText(test.finish());
        PreparedStatement stmt = Database.connection.prepareStatement("select * from users where id = '" + Database.currentUserId + "'");
        ResultSet rs = stmt.executeQuery();
        rs.next();
        stmt = Database.connection.prepareStatement(String.format("delete from results where test_id = '%d' and student_id = '%d'",
                testId, Integer.parseInt(rs.getString("id"))));
        stmt.execute();

        stmt = Database.connection.prepareStatement(String.format(Locale.US,
                "insert into results(test_id, test_name, learner_id, learner_name, classes_name, score, max_score) " +
                        "values('%d', '%s', '%d', '%s', '%s', '%f', '%d')",
                testId, test.getName(), Integer.parseInt(rs.getString("id")),
                rs.getString("name"), rs.getString("classes_name"),
                test.getScore(), test.getMaxScore()
        ));
        stmt.execute();
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

            for (int i = 0; i < this.checks.getChildren().size(); i++) {
                ((CheckBox) this.checks.getChildren().get(i))
                        .setText(test.getQuests().get(0).getAnswers().get(i).getText());
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
