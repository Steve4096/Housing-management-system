package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import com.greenview_hostels.greenview_hostels_housing_management_system.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Add_Tenant_btn.setOnAction(actionEvent -> AddTenant());
        Tenants_btn.setOnAction(actionEvent -> Tenants());
        Payments_btn.setOnAction(actionEvent -> Payments());
        Houses_btn.setOnAction(actionEvent -> Houses());
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
}
