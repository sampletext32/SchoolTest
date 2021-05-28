package com.company.fxml.Admin;

import com.company.Database;
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

public class ShowClassesResultController implements Initializable {

    public Text classesName;
    public TableView<ClassesResultTable> table;
    public TableColumn<ClassesResultTable, Integer> tID;
    public TableColumn<ClassesResultTable, String> tLearnerName;
    public TableColumn<ClassesResultTable, String> tTestName;
    public TableColumn<ClassesResultTable, Integer> tResult;
    public Button goBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            int num = 1;

            tID.setCellValueFactory((new PropertyValueFactory<ClassesResultTable, Integer>("rID")));
            tLearnerName.setCellValueFactory((new PropertyValueFactory<ClassesResultTable, String>("rLearnerName")));
            tTestName.setCellValueFactory((new PropertyValueFactory<ClassesResultTable, String>("rTestName")));
            tResult.setCellValueFactory((new PropertyValueFactory<ClassesResultTable, Integer>("rResult")));

            PreparedStatement stmt = Database.globalSTMT;
            ResultSet rs = stmt.executeQuery();

            ObservableList<ClassesResultTable> data = FXCollections.observableArrayList();
            while (rs.next()) {
                classesName.setText(rs.getString("classes_name"));
                String testName = rs.getString("test_name");
                String studentName = rs.getString("learner_name");
                double score = Double.parseDouble(rs.getString("score"));
                double maxScore = Double.parseDouble(rs.getString("max_score"));
                int finalScore = (int) (100 * score/maxScore);
                data.add(new ClassesResultTable(num++, studentName, testName,  finalScore));
            }


            table.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onGoBack(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminWindow.fxml"));
        stage = new Stage();
        stage.setTitle("Панель администратора");
        stage.initModality(Modality.APPLICATION_MODAL);
        Parent root = (Parent) fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
