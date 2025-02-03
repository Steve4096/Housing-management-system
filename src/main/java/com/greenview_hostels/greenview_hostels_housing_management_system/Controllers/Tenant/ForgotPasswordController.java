package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        ResetPwd_btn.setOnAction(actionEvent -> checkIfPasswordsMatch());
        ValidatePassword();
    }

    private void checkIfPasswordsMatch(){
        String password=NewPwd_txtarea.getText();
        String newPwd=ConfirmPwd_txtarea.getText();
        if(password.equals(newPwd)){
            Pwd_dont_match_errorlbl.setText("");
        }else {
            Pwd_dont_match_errorlbl.setText("Passwords don't match");
        }
        System.out.println(password);
        System.out.println(newPwd);
    }

    private void ValidatePassword(){
        NewPwd_txtarea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue,String oldValue, String newValue) {
                StringBuilder errorMessage=new StringBuilder();
                //Check for atleast 1 uppercase letter
                if(!newValue.matches(".*[A-Z].*")){
                    errorMessage.append("Password must contain atleast 1 uppercase letter.");
                }
                //Check for special characters
                if(!newValue.matches(".*[^a-zA-Z0-9].*")){
                    errorMessage.append("Password must contain atleast 1 special character");
                }
                //Check for number
                if (!newValue.matches(".*\\d.*")){
                    errorMessage.append("Password must contain atleast 1 digit");
                }
                //Print the output to the PwdFormat_error label
                if(errorMessage.length()>0){
                    Pwdformat_errorlbl.setText(errorMessage.toString().trim());
                }else {
                    Pwdformat_errorlbl.setText("");
                }
            }
        });
    }


}
