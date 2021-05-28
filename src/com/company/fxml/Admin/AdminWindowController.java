package com.company.fxml.Admin;

import com.company.Database;
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
import javafx.scene.control.TextField;
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

public class AdminWindowController implements Initializable {

    public Text name;
    public TableView<AdminTableClasses> tableGroups;
    public TableColumn<AdminTableClasses, Integer> tableColumnId;
    public TableColumn<AdminTableClasses, String> tableColumnName;
    public TableColumn<AdminTableClasses, Integer> tGroupSize;
    public TableColumn<AdminTableClasses, Button> tRemoveButton;
    public TableView<AdminTableSubjects> tableSubjects;
    public TableColumn<AdminTableSubjects, Integer> tSubjectID;
    public TableColumn<AdminTableSubjects, String> tSubjectName;
    public TableColumn<AdminTableSubjects, Button> tSubjectButton;
    public Button addGroup;
    public Button addSubject;
    public TextField groupInput;
    public TextField subjectInput;
    public TableColumn<AdminTableClasses, Button>  tShowButton;
    public Button goBack;

    public void onAddGroup(ActionEvent actionEvent) throws SQLException, IOException {
        if (!groupInput.getText().isEmpty()) {
            PreparedStatement stmt = Database.connection.prepareStatement("insert into system_test.classes(name) values('"+ groupInput.getText()+"')");
            stmt.execute();

            Stage stage = (Stage) groupInput.getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminWindow.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Панель администратора");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void onAddSubject(ActionEvent actionEvent) throws SQLException, IOException {
        if (!subjectInput.getText().isEmpty()) {
            PreparedStatement stmt = Database.connection.prepareStatement(
                    "insert into system_test.subjects(name) values('" + subjectInput.getText() + "')"
            );
            stmt.execute();

            Stage stage = (Stage) groupInput.getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminWindow.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Панель администратора");
            stage.setScene(new Scene(root));
            stage.show();
        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            int numG = 1;
            int numS = 1;

            tableColumnId.setCellValueFactory((new PropertyValueFactory<AdminTableClasses, Integer>("rID")));
            tableColumnName.setCellValueFactory((new PropertyValueFactory<AdminTableClasses, String>("rName")));
            tGroupSize.setCellValueFactory((new PropertyValueFactory<AdminTableClasses, Integer>("rSize")));
            tRemoveButton.setCellValueFactory((new PropertyValueFactory<AdminTableClasses, Button>("removeButton")));
            tShowButton.setCellValueFactory((new PropertyValueFactory<AdminTableClasses, Button>("showButton")));

            tSubjectID.setCellValueFactory((new PropertyValueFactory<AdminTableSubjects, Integer>("rID")));
            tSubjectName.setCellValueFactory((new PropertyValueFactory<AdminTableSubjects, String>("rName")));
            tSubjectButton.setCellValueFactory((new PropertyValueFactory<AdminTableSubjects, Button>("button")));

            PreparedStatement stmtG = Database.connection.prepareStatement("select * from system_test.classes");
            PreparedStatement stmtS = Database.connection.prepareStatement("select * from subjects");

            ResultSet rsG = stmtG.executeQuery();
            ResultSet rsS = stmtS.executeQuery();

            ObservableList<AdminTableClasses> dataG = FXCollections.observableArrayList();
            ObservableList<AdminTableSubjects> dataS = FXCollections.observableArrayList();

            ObjectMapper mapper = new ObjectMapper();

            while (rsG.next()) {
                PreparedStatement stmtCount = Database.connection.prepareStatement(
                        "select * from users where classes_name = '" + rsG.getString("name") + "'");

                ResultSet rsCount = stmtCount.executeQuery();

                int counter = 0;
                while(rsCount.next()) {
                    counter++;
                }

                dataG.add(new AdminTableClasses(numG++, rsG.getString("name"), counter));
            }

            while (rsS.next()) {
                dataS.add(new AdminTableSubjects(numS++, rsS.getString("name")));
            }

            tableGroups.setItems(dataG);
            tableSubjects.setItems(dataS);

            PreparedStatement stmt = Database.connection.prepareStatement(
                    "select * from users where id = '" + Database.currentUserId + "'");

            ResultSet rs = stmt.executeQuery();

            rs.next();

            name.setText(rs.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
