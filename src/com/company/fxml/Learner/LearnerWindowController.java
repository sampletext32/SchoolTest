package com.company.fxml.Learner;

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
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
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

public class LearnerWindowController implements Initializable {
    public Text name;
    public Text classesName;
    public Text hello;
    public TableView<LearnerTable> tableView;
    public TableColumn<LearnerTable, Integer> tableColumnId;
    public TableColumn<LearnerTable, String> tableColumnName;
    public TableColumn<LearnerTable, String> tableColumnSubject;
    public TableColumn<LearnerTable, Integer> tableColumnAmountOfQuestions;
    public TableColumn<LearnerTable, Integer> tableColumnScore;
    public TableColumn<LearnerTable, Button> tableColumnButtons;
    public Button buttonGoBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            PreparedStatement stmt = Database.connection.prepareStatement("select * from users where id = '" + Database.currentUserId + "'");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            name.setText(rs.getString("name"));
            classesName.setText(rs.getString("classes_name"));


            int num = 1;

            tableColumnId.setCellValueFactory((new PropertyValueFactory<LearnerTable, Integer>("rID")));
            tableColumnName.setCellValueFactory((new PropertyValueFactory<LearnerTable, String>("rName")));
            tableColumnSubject.setCellValueFactory((new PropertyValueFactory<LearnerTable, String>("rSubject")));
            tableColumnAmountOfQuestions.setCellValueFactory((new PropertyValueFactory<LearnerTable, Integer>("rQuestions")));
            tableColumnScore.setCellValueFactory((new PropertyValueFactory<LearnerTable, Integer>("rScore")));
            tableColumnButtons.setCellValueFactory((new PropertyValueFactory<LearnerTable, Button>("button")));

             stmt = Database.connection.prepareStatement("select * from tests");
             rs = stmt.executeQuery();

            ObservableList<LearnerTable> data = FXCollections.observableArrayList();
            ObjectMapper mapper = new ObjectMapper();
            while (rs.next()) {
                String output = rs.getString("test");
                Test test = mapper.readValue(output, Test.class);
                if (test.getGroupName().equals(classesName.getText())) {
                    data.add(new LearnerTable(num++, test, Integer.parseInt(rs.getString("id"))));
                }
            }

            stmt = Database.connection.prepareStatement(String.format(
                    "SELECT * FROM results where learner_name = '%s' and classes_name = '%s'",
                    name.getText(), classesName.getText()));

            rs = stmt.executeQuery();
            while (rs.next()) {
                for (LearnerTable datum : data) {
                    if (datum.getTestID() == Integer.parseInt(rs.getString("test_id"))) {
                        double score = Double.parseDouble(rs.getString("score"));
                        double maxScore = Double.parseDouble(rs.getString("max_score"));
                        int finalScore = (int) (100 * score/maxScore);
                        datum.setrScore(finalScore);
                    }
                }
            }


            tableView.setItems(data);
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            extracted(e);
        }

    }

    private static void extracted(IOException e) {
        e.printStackTrace();
    }

    public void onGoBack(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) buttonGoBack.getScene().getWindow();
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
