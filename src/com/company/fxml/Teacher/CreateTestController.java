package com.company.fxml.Teacher;

import com.company.Database;
import com.company.TestElements.Test;
import com.company.models.SchoolClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateTestController implements Initializable {
    public TextField name;
    public TextField amount;
    public Button btn;
    public Button goBack;
    public ComboBox<String> classesName;
    public Text error;
    private String currentSubject;
    private final ObservableList<String> classes = FXCollections.observableArrayList();

    public void onGoBack(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("teacherWindow.fxml"));
        stage = new Stage();
        stage.setTitle("Панель учителя");
        stage.initModality(Modality.APPLICATION_MODAL);
        Parent root = (Parent) fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void onClick(ActionEvent actionEvent) throws Exception {
        if (name.getText().isEmpty() || amount.getText().isEmpty() || classesName.getValue() == null) {
            error.setText("Вы заполнили не все поля");
        } else {
            try{
                int num = Integer.parseInt(amount.getText());
                Stage stage = (Stage) btn.getScene().getWindow();
                stage.close();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createQuest.fxml"));
                Parent root = (Parent) fxmlLoader.load();

                stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Создание вопроса");
                stage.setScene(new Scene(root));

                CreateQuestController controller = fxmlLoader.getController(); //получаем контроллер для второй формы
                controller.setN(Integer.parseInt(amount.getText())); // передаем необходимые параметры
                controller.test = new Test(name.getText(), classesName.getValue(), currentSubject);

                stage.show();
            } catch (NumberFormatException e) {
                error.setText("Количиство вопросов должно быть числом");

            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            PreparedStatement stmt = Database.connection.prepareStatement("SELECT * FROM system_test.classes");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SchoolClass group = new SchoolClass(rs.getString("name"));
                classes.add(group.toString());
            }
            classesName.setItems(classes);
            stmt = Database.connection.prepareStatement("select * from users where id = '" + Database.currentUserId + "'");
            rs = stmt.executeQuery();
            rs.next();
            currentSubject = rs.getString("subject_name");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
