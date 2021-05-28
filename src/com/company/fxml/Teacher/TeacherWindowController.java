package com.company.fxml.Teacher;

import com.company.Database;
import com.company.TestElements.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TeacherWindowController implements Initializable {
    public Text name;
    public Text subject;
    public TableView<TeacherTable> table;
    public TableColumn<TeacherTable, Integer> tID;
    public TableColumn<TeacherTable, Integer> tQuestions;
    public TableColumn<TeacherTable, String> tName;
    public TableColumn<TeacherTable, Button> tRemoveButton;
    public TableColumn<TeacherTable, Button> tShowButton;
    public Button addButton;
    public Button goBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            int num = 1;

            tID.setCellValueFactory((new PropertyValueFactory<TeacherTable, Integer>("rID")));
            tName.setCellValueFactory((new PropertyValueFactory<TeacherTable, String>("rName")));
            tQuestions.setCellValueFactory((new PropertyValueFactory<TeacherTable, Integer>("rQuestions")));
            tRemoveButton.setCellValueFactory((new PropertyValueFactory<TeacherTable, Button>("removeButton")));
            tShowButton.setCellValueFactory((new PropertyValueFactory<TeacherTable, Button>("showButton")));

            PreparedStatement stmt = Database.connection.prepareStatement(
                    "select * from tests where creator = '" + Database.currentUserId + "'");
            ResultSet rs = stmt.executeQuery();

            ObservableList<TeacherTable> data = FXCollections.observableArrayList();
            ObjectMapper mapper = new ObjectMapper();
            while (rs.next()) {
                String output = rs.getString("test");
                Test test = mapper.readValue(output, Test.class);
                data.add(new TeacherTable(num++, test, Integer.parseInt(rs.getString("id"))));
            }

            table.setItems(data);
//
            stmt = Database.connection.prepareStatement("select * from users where id = '" + Database.currentUserId + "'");
            rs = stmt.executeQuery();
            rs.next();
            name.setText(rs.getString("name"));
            subject.setText(rs.getString("subject_name"));

        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onAdd(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createTest.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Создание теста");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void onGoBack(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Auth/LoginScreen.fxml"));
        stage = new Stage();
        stage.setTitle("Авторизация");
        stage.initModality(Modality.APPLICATION_MODAL);
        Parent root = (Parent) fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
