package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Database;
import main.FXMLHelper;
import models.TeacherAccount;

public class TeacherLoginScreen {
    public TextField textFieldLogin;
    public PasswordField passwordField;

    public void onButtonSignInClick(ActionEvent actionEvent) {
        String login = textFieldLogin.getText();
        String password = passwordField.getText();

        if (login.trim().length() == 0 || password.trim().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("LogIn Failed");
            alert.setHeaderText("Unable to Log In");
            alert.setContentText("Login or Password were empty\nPlease Try Again.");
            alert.showAndWait();
            textFieldLogin.clear();
            passwordField.clear();
            return;
        }

        TeacherAccount teacherAccount = Database.getTeacherByLoginAndPassword(login, password);

        if (teacherAccount == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("LogIn Failed");
            alert.setHeaderText("Unable to Log In");
            alert.setContentText("Teacher not found");
            alert.showAndWait();
            return;
        }

        TeacherAccount.LoggedAccount = teacherAccount;

        TeacherMainScreen teacherMainScreen = FXMLHelper.loadScreenReturnController("TeacherMainScreen");
        teacherMainScreen.preload();
    }
}

