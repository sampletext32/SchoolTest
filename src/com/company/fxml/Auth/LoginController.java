package com.company.fxml.Auth;

import com.company.Database;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    public TextField login;
    public PasswordField password;
    public Button btn;
    public Button register;
    public Text error;

    public void onClick() throws SQLException, IOException {
        PreparedStatement stmt = Database.connection.prepareStatement(String.format(
                "SELECT * FROM users WHERE login = '%s' AND password = '%s'", login.getText(), password.getText()));
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            error.setText("");
            Database.currentUserId = Integer.parseInt(rs.getString("id"));

            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader;
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            switch (rs.getString("role")) {
                case "admin": {
                    fxmlLoader = new FXMLLoader(getClass().getResource("../Admin/AdminWindow.fxml"));
                    stage.setTitle("Панель администратора");
                    break;
                }
                case "learner": {
                    fxmlLoader = new FXMLLoader(getClass().getResource("../Learner/learnerWindow.fxml"));
                    stage.setTitle("Панель ученик");
                    break;
                }
                case "teacher": {
                    fxmlLoader = new FXMLLoader(getClass().getResource("../Teacher/teacherWindow.fxml"));
                    stage.setTitle("Панель учителя");
                    break;
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + rs.getString("role"));
            }
            Parent root = fxmlLoader.load();


            stage.setScene(new Scene(root));
            stage.show();
        } else {
            error.setText("Неверный логин или пароль!");
        }

    }

    public void onRegister() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RegisterScreen.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Регистрация");
        stage.initModality(Modality.APPLICATION_MODAL);
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
