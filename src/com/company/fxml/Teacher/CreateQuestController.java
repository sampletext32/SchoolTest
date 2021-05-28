package com.company.fxml.Teacher;

import com.company.TestElements.Answer;
import com.company.Database;
import com.company.TestElements.Quest;
import com.company.TestElements.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateQuestController {
    public Button next;
    public Text error;
    private int n;
    @FXML
    public Test test;
    public TextField qName;
    public Button btn;
    public Pane ans;
    public Pane check;
    public AnchorPane window;

    public void setN(int n) {
        this.n = n;
    }
    public void onNext(ActionEvent actionEvent) throws Exception {
        Quest quest = new Quest(qName.getText());
        int counter = 0;
        Boolean hasError = true;
        for (int i = 0; i < 4; i++) {
            // Получаем значения из полей
            TextField tf = (TextField) ans.getChildren().get(i);
            CheckBox ch = (CheckBox) check.getChildren().get(i);

            for (int j = 0; j < check.getChildren().size(); j++) {
                CheckBox chBx = (CheckBox) check.getChildren().get(j);
                if (chBx.isSelected()) {
                    counter++;
                }
            }
            if (tf.getText().isEmpty()) {
                error.setText("Вы заполнили не все поля");
            } else if (counter == 0) {
                error.setText("Вы не выбрали правильный ответ");
            } else {
                error.setText("");
                hasError = false;
                quest.addAnswer(new Answer(tf.getText(), ch.isSelected()));
                // Обновляем поля
                qName.setText("");
                ((TextField) ans.getChildren().get(i)).setText("");

                ((CheckBox) check.getChildren().get(i)).setSelected(false);
            }
            // Добавляем ответ в вопрос

        }

        if (!hasError) {
            n--;
            // Добавляем вопрос в тест
            test.addQuest(quest);

            if (n == 0) this.end();
        }
    }

    public void end() throws IOException, SQLException {
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, test);
        PreparedStatement stmt = Database.connection.prepareStatement(String.format(
                "INSERT INTO tests (`test`, `creator`) VALUES ('%s', '%d');", writer.toString(), Database.currentUserId));
        stmt.execute();
//        // Закрываем окно
        Stage stage = (Stage) qName.getScene().getWindow();
        stage.close();


        // Передаем параметры в StartTestControl
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("teacherWindow.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Панель учителя");
        stage.setScene(new Scene(root));

        stage.show();
    }
}
