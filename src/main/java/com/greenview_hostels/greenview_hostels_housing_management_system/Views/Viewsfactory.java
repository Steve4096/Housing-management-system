package com.greenview_hostels.greenview_hostels_housing_management_system.Views;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Viewsfactory  {
    private AnchorPane splashScreen;
    private AnchorPane dashboard;
    private AnchorPane forgotPasswordScreen;


    public AnchorPane showSplashScreen(){
        if (splashScreen==null){
            try {
                FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/Fxml/Splashscreen.fxml"));
                Scene scene=new Scene(fxmlLoader.load());
                Stage stage=new Stage();
                stage.setTitle("Splashscreen");
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return splashScreen;
    }

    public AnchorPane showForgotPasswordScreen(){
        if (forgotPasswordScreen==null){
            try {
                FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/Fxml/Tenant/ForgotPassword.fxml"));
                Scene scene=new Scene(fxmlLoader.load());
                Stage stage=new Stage();
                stage.setTitle("Splashscreen");
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return forgotPasswordScreen;
    }





}
