package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Database;
import main.FXMLHelper;
import models.StudentAccount;

public class StudentRegisterScreen {
    public TextField textFieldLogin;
    public PasswordField passwordField;
    public TextField textFieldUsername;

    public void onButtonSignInClick(ActionEvent actionEvent) {
        String login = textFieldLogin.getText();
        String password = passwordField.getText();
        String username = textFieldUsername.getText();

        if (login.trim().length() == 0 || password.trim().length() == 0 || username.trim().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Register Failed");
            alert.setHeaderText("Unable to Register");
            alert.setContentText("Login, Password or Username were empty\nPlease Try Again.");
            alert.showAndWait();
            textFieldLogin.clear();
            passwordField.clear();
            return;
        }

        StudentAccount studentByLogin = Database.getStudentByLogin(login);
        if (studentByLogin != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Register Failed");
            alert.setHeaderText("Unable to Register");
            alert.setContentText("Login is occupied\nPlease Try Again.");
            alert.showAndWait();
            textFieldLogin.clear();
            passwordField.clear();
            return;
        }

        StudentAccount studentAccount = new StudentAccount(0, login, password, username);

        Database.addStudent(studentAccount);

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Register Success");
        alert.setHeaderText("Congratulations");
        alert.setContentText("You are now registered");
        alert.showAndWait();

        FXMLHelper.backScreen();
    }
}
