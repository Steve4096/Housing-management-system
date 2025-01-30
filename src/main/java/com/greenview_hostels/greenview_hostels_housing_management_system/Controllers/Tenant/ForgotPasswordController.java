package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable {
    public TextField Current_pwd;
    public TextField NewPwd_txtarea;
    public TextField ConfirmPwd_txtarea;
    public Button ResetPwd_btn;
    public Label Pwd_dont_match_errorlbl;
    public Label Pwdformat_errorlbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void checkIfPasswordsMatch(){
        String password=NewPwd_txtarea.getText();
        String newPwd=ConfirmPwd_txtarea.getText();
        if(password!=newPwd){
            Pwd_dont_match_errorlbl.setText("Passwords don't match");
        }
    }


}
