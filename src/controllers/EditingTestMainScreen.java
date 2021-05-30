package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.Database;
import main.FXMLHelper;
import models.Question;

import java.util.List;

public class EditingTestMainScreen implements FXMLHelper.PreloadableController{

    private List<Question> question;

    public TableView<Question> questionTableView;
    public TableColumn<Question, String> questionId;
    public TableColumn<Question, String> questionTitle;

    @Override
    public <T> void preload(T... objects) {
        question = Database.selectQuestion();

        questionId.setCellValueFactory(param -> new SimpleStringProperty(Integer.toString(param.getValue().getId())));
        questionTitle.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTitle()));
        fillTable();
    }
    private void fillTable() {
        questionTableView.getItems().setAll(question);
    }

    public void onButtonAddClick(ActionEvent actionEvent) {
    }

    public void onButtonDeleteClick(ActionEvent actionEvent) {
    }

    public void onButtonEditClick(ActionEvent actionEvent) {
    }
}
