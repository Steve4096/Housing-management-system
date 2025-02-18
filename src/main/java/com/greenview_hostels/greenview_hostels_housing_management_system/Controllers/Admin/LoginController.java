package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField Username_txtarea;
    public TextField Pwd_txtarea;
    public Button Signin_btn;
    public Label Error_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Signin_btn.setOnAction(actionEvent -> AdminLogin());
    }

    private void AdminLogin(){
        Stage stage=(Stage) Signin_btn.getScene().getWindow();
        Model.getInstance().getViewsfactory().showAdminDashboard();
        Model.getInstance().getViewsfactory().CloseWindow(stage);
    }
}
