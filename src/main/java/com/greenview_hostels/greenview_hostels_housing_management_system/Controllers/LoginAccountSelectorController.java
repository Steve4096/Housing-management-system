package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginAccountSelectorController implements Initializable {
    public ImageView Admin_icon;
    public ImageView Tenant_icon;
    public Button Continue_btn;
    public VBox Admin_VBox;
    public VBox Tenant_VBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Admin_VBox.setOnMouseClicked(mouseEvent -> OpenAdminLoginWindow());
        Tenant_VBox.setOnMouseClicked(mouseEvent -> showTenantLoginWindow());
    }

    private void OpenAdminLoginWindow(){
        Stage stage=(Stage)Admin_VBox.getScene().getWindow();
        Model.getInstance().getViewsfactory().CloseWindow(stage);
        Model.getInstance().getViewsfactory().showAdminLoginWindow();
    }

    private void showTenantLoginWindow(){
        Stage stage=(Stage)Tenant_VBox.getScene().getWindow();
        Model.getInstance().getViewsfactory().CloseWindow(stage);
        Model.getInstance().getViewsfactory().showTenantLoginWindow();
    }
}
