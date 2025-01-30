package com.greenview_hostels.greenview_hostels_housing_management_system;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage)  {
        Model.getInstance().getViewsfactory().showForgotPasswordScreen();
    }


}