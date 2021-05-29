package controllers;

import javafx.event.ActionEvent;
import main.FXMLHelper;

public class ChooseRoleScreen {
    public void onButtonSignInAsAdminClick(ActionEvent actionEvent) {
        FXMLHelper.loadScreen("AdminLoginScreen");
    }

    public void onButtonSignInAsTeacherClick(ActionEvent actionEvent) {
    }

    public void onButtonSignInAsStudentClick(ActionEvent actionEvent) {
    }
    public void onButtonSignInAsRegistrationClick (ActionEvent actionEvent){
    }
}
