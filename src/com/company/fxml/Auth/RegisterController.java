package com.company.fxml.Auth;

import com.company.Database;
import com.company.models.Role;
import com.company.models.SchoolClass;
import com.company.models.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public TextField textFieldLogin;
    public PasswordField textFieldPassword;
    public TextField textFieldName;
    public ChoiceBox<String> choice;
    public ChoiceBox<Role> role;
    public Label choiceText;
    private final ObservableList<String> classes = FXCollections.observableArrayList();
    private final ObservableList<String> subjects = FXCollections.observableArrayList();
    public Button btn;
    public Text error;

    public void onClick() throws SQLException {
        PreparedStatement stmtCheckAdmin = Database.connection.prepareStatement(
                "SELECT * FROM users WHERE role = 'admin'");
        ResultSet rsCheckAdmin = stmtCheckAdmin.executeQuery();
        if (textFieldLogin.getText().isEmpty() || textFieldPassword.getText().isEmpty() || textFieldName.getText().isEmpty()) {
            error.setText("Вы заполнили не все поля");
        } else if (role.getValue().getCode().equals("admin") && rsCheckAdmin.next()) {
            error.setText("Администратор уже существует");
        } else if (role.getValue().getCode().equals("teacher") && choice.getValue() == null) {
            error.setText("Вы не выбрали дисциплину");
        } else if (role.getValue().getCode().equals("learner") && choice.getValue() == null) {
            error.setText("Вы не выбрали класс");
        } else {
            PreparedStatement stmtCheck = Database.connection.prepareStatement(String.format(
                    "SELECT * FROM users WHERE login = '%s' AND password = '%s'", textFieldLogin.getText(), textFieldPassword.getText()));
            ResultSet rs = stmtCheck.executeQuery();

            if (rs.next()) {
                error.setText("Пользователь с таким логином уже существует!");
            } else {
                error.setText("1");
                PreparedStatement stmt;
                if (role.getValue().getCode().equals("learner")) {
                    stmt = Database.connection.prepareStatement(String.format(
                            "insert into users(login, password, role, name, classes_name) values('%s', '%s', '%s', '%s', '%s')",
                            textFieldLogin.getText(), textFieldPassword.getText(), role.getValue().getCode(), textFieldName.getText(), choice.getValue()
                    ));
                } else if (role.getValue().getCode().equals("teacher")) {
                    stmt = Database.connection.prepareStatement(String.format(
                            "insert into users(login, password, role, name, subject_name) values('%s', '%s', '%s', '%s', '%s')",
                            textFieldLogin.getText(), textFieldPassword.getText(), role.getValue().getCode(), textFieldName.getText(), choice.getValue()
                    ));
                } else {
                    stmt = Database.connection.prepareStatement(String.format(
                            "insert into users(login, password, role, name) values('%s', '%s', '%s', '%s')",
                            textFieldLogin.getText(), textFieldPassword.getText(), role.getValue().getCode(), textFieldName.getText()
                    ));
                }

                stmt.execute();

                Stage stage = (Stage) btn.getScene().getWindow();
                stage.close();
            }
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choice.setItems(FXCollections.observableArrayList());
        try {
            PreparedStatement stmt = Database.connection.prepareStatement("SELECT * FROM system_test.classes");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SchoolClass group = new SchoolClass(rs.getString("name"));
                classes.add(group.toString());
            }
            stmt = Database.connection.prepareStatement("select * from subjects");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject(rs.getString("name"));
                subjects.add(subject.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<Role> roles = FXCollections.observableArrayList(
                new Role("Администратор", "admin"),
                new Role("Ученик", "learner"),
                new Role("Учитель", "teacher")
        );
        role.setItems(roles);
        choice.setVisible(false);
        role.setOnAction((e) -> {
            error.setText("");
            if (role.getValue().getCode().equals("learner")) {
                choiceText.setText("Выберете класс");
                choice.setItems(classes);
                choice.setVisible(true);
            } else if (role.getValue().getCode().equals("teacher")) {
                choiceText.setText("Выберете предмет");
                choice.setItems(subjects);
                choice.setVisible(true);
            } else {
                choiceText.setText("");
                choice.setItems(FXCollections.observableArrayList());
                choice.setVisible(false);
            }
            btn.setDisable(false);
        });

    }
}
