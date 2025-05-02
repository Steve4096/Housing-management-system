package com.greenview_hostels.greenview_hostels_housing_management_system;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Stage splashStage = Model.getInstance().getViewsfactory().showSplashScreen();

        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(actionEvent -> {
                    splashStage.close();
                    OpenLoginAccountSelector();
                }
        );
        delay.play();


    }

    private void OpenLoginAccountSelector() {
        Model.getInstance().getViewsfactory().showLoginAccountSelectorWindow();
    }
}





