package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LoginController implements Initializable {
    public Button Back_btn;
    public TextField Email_textarea;
    //public TextField Pwd_txtarea;
    public Hyperlink Forgot_pwd_link;
    public Button Signin_btn;
    public Label Invalid_email_address_lbl;
    public Label Error_lbl;
    public PasswordField Pwd_pwdfield;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupLoginButton();
       // Forgot_pwd_link.setOnAction(actionEvent -> RedirectToResetPasswordScreen());
        CheckEmailAddressFormat();
        Back_btn.setOnAction(actionEvent -> showLoginAccountSelector());
        Signin_btn.setOnAction(actionEvent -> login());
    }

    private void showLoginAccountSelector(){
        Stage stage=(Stage) Back_btn.getScene().getWindow();
        Model.getInstance().getViewsfactory().CloseWindow(stage);
        Model.getInstance().getViewsfactory().showLoginAccountSelectorWindow();
    }

//    private void RedirectToResetPasswordScreen(){
//        Stage stage=(Stage) Forgot_pwd_link.getScene().getWindow();
//        Model.getInstance().getViewsfactory().CloseWindow(stage);
//        Model.getInstance().getViewsfactory().showForgotPasswordScreen();
//    }

   private void CheckEmailAddressFormat(){
        Email_textarea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches(".*[@].*")){
                    Invalid_email_address_lbl.setText("Email address must contain @");
                }else {
                    Invalid_email_address_lbl.setText("");
                }
            }
        });
   }

   private void login(){
        String email= Email_textarea.getText();
        String password=Pwd_pwdfield.getText();
        Model.getInstance().evaluateClientCredentials(email,password);
        if (Model.getInstance().getTenantLoginSuccessFlag()){
            successfulLogin();
        }else {
            invalidCredentials();
        }
   }

   private void successfulLogin(){
        Stage stage=(Stage) Signin_btn.getScene().getWindow();
        Model.getInstance().getViewsfactory().CloseWindow(stage);
        Model.getInstance().getViewsfactory().showTenantDashboard();
   }

   private void invalidCredentials(){
        Error_lbl.setText("INCORRECT PASSWORD OR EMAIL ADDRESS");
        resetAllFields();
   }

   private void resetAllFields(){
        Email_textarea.setText("");
        Pwd_pwdfield.setText("");
   }

   private void setupLoginButton(){
        Signin_btn.disableProperty().bind(Bindings.createBooleanBinding(()->
                Email_textarea.getText().trim().isEmpty() || Pwd_pwdfield.getText().trim().isEmpty(),
                Email_textarea.textProperty(),Pwd_pwdfield.textProperty()));
   }
}

