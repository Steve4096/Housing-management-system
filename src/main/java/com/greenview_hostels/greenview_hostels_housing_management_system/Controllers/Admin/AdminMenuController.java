package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import com.greenview_hostels.greenview_hostels_housing_management_system.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button Dashboard_btn;
    public Button Payments_btn;
    public Button Add_Tenant_btn;
    public Button Tenants_btn;
    public Button UtilityLevels_btn;
    public Button Complaints_btn;
    public Button Notices_btn;
    public Button Houses_btn;
    public Button LogOut_btn;
    public Button Add_property_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Dashboard_btn.setOnAction(actionEvent -> Dashboard());
        Add_Tenant_btn.setOnAction(actionEvent -> AddTenant());
        Tenants_btn.setOnAction(actionEvent -> Tenants());
        UtilityLevels_btn.setOnAction(actionEvent -> utilityLevels());
        Payments_btn.setOnAction(actionEvent -> Payments());
        Notices_btn.setOnAction(actionEvent -> noticesFiled());
        Complaints_btn.setOnAction(actionEvent -> complaintsFiled());
        Houses_btn.setOnAction(actionEvent -> Houses());
        Add_property_btn.setOnAction(actionEvent -> Addproperty());
        LogOut_btn.setOnAction(actionEvent -> onLogout());
    }

    private void Dashboard(){
        Model.getInstance().getViewsfactory().getAdminSelectedMenuItem().set(AdminMenuOptions.DASHBOARD);
    }

    private void Payments(){
    Model.getInstance().getViewsfactory().getAdminSelectedMenuItem().set(AdminMenuOptions.PAYMENTS);
}

private void AddTenant(){
        Model.getInstance().getViewsfactory().getAdminSelectedMenuItem().set(AdminMenuOptions.REGISTRATION);
}

private void Tenants(){
        Model.getInstance().getViewsfactory().getAdminSelectedMenuItem().set(AdminMenuOptions.TENANTS);
}

private void Houses(){
        Model.getInstance().getViewsfactory().getAdminSelectedMenuItem().set(AdminMenuOptions.HOUSES);
}

private void Addproperty(){
        Model.getInstance().getViewsfactory().getAdminSelectedMenuItem().set(AdminMenuOptions.ADD_PROPERTY);
}

private void utilityLevels(){
        Model.getInstance().getViewsfactory().getAdminSelectedMenuItem().set(AdminMenuOptions.UTILITY_LEVEL);
}

private void noticesFiled(){
        Model.getInstance().getViewsfactory().getAdminSelectedMenuItem().set(AdminMenuOptions.NOTICES);
}

private void complaintsFiled(){
        Model.getInstance().getViewsfactory().getAdminSelectedMenuItem().set(AdminMenuOptions.COMPLAINTS);
}

private void onLogout(){
    Stage stage=(Stage) LogOut_btn.getScene().getWindow();
    Model.getInstance().getViewsfactory().CloseWindow(stage);
    Model.getInstance().getViewsfactory().showLoginAccountSelectorWindow();
}
}


