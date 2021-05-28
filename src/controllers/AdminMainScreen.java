package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.Database;
import main.FXMLHelper;
import models.TeacherAccount;

import java.util.List;

public class AdminMainScreen implements FXMLHelper.PreloadableController {

    private List<TeacherAccount> teachers;

    public TableView<TeacherAccount> teachersTableView;

    public TableColumn<TeacherAccount, String> columnId;
    public TableColumn<TeacherAccount, String> columnLogin;
    public TableColumn<TeacherAccount, String> columnPassword;
    public TableColumn<TeacherAccount, String> columnUsername;


    @Override
    public <T> void preload(T... objects) {

        teachers = Database.selectTeachers();

        columnId.setCellValueFactory(param -> new SimpleStringProperty(Integer.toString(param.getValue().getId())));
        columnLogin.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLogin()));
        columnPassword.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPassword().replaceAll(".", "*")));
        columnUsername.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUsername()));
        fillTable();
    }


    private void fillTable() {
        teachersTableView.getItems().setAll(teachers);
    }

    public void onButtonAddClick(ActionEvent actionEvent) {
    }

    public void onButtonDeleteClick(ActionEvent actionEvent) {
    }

    public void onButtonEditClick(ActionEvent actionEvent) {
    }
}
