package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Database;
import main.FXMLHelper;
import models.AdminAccount;

public class AdminLoginScreen {
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

        AdminAccount adminAccount = Database.getAdminByLoginAndPassword(login, password);

        if (adminAccount == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("LogIn Failed");
            alert.setHeaderText("Unable to Log In");
            alert.setContentText("Admin not found");
            alert.showAndWait();
            return;
        }

        AdminAccount.LoggedAccount = adminAccount;

        AdminMainScreen adminMainScreen = FXMLHelper.loadScreenReturnController("AdminMainScreen");
        adminMainScreen.preload();
    }
}
