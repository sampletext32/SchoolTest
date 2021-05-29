package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.Database;
import main.FXMLHelper;
import models.Test;

import java.util.List;

public class TeacherMainScreen implements FXMLHelper.PreloadableController {

    private List<Test> tests;

    public TableView<Test> testsTableView;
    public TableColumn<Test, String> columnId;
    public TableColumn<Test, String> columnTitle;

    @Override
    public <T> void preload(T... objects) {
        tests = Database.selectAllTests();

        columnId.setCellValueFactory(param -> new SimpleStringProperty(Integer.toString(param.getValue().getId())));
        columnTitle.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTitle()));
        fillTable();
    }

    private void fillTable() {
        testsTableView.getItems().setAll(tests);
    }

    public void onButtonAddClick(ActionEvent actionEvent) {
    }

    public void onButtonDeleteClick(ActionEvent actionEvent) {
    }

    public void onButtonEditClick(ActionEvent actionEvent) {
    }
}

