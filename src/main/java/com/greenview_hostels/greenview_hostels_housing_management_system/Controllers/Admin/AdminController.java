package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane Admin_Parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewsfactory().getAdminSelectedMenuItem().addListener((observableValue, adminMenuOptions, newVal) ->{
            switch (newVal){
                case DASHBOARD -> Admin_Parent.setCenter(Model.getInstance().getViewsfactory().getDashboard());
                case REGISTRATION -> Admin_Parent.setCenter(Model.getInstance().getViewsfactory().getRegistrationwindow());
                case TENANTS -> Admin_Parent.setCenter(Model.getInstance().getViewsfactory().getTenantslist());
                case PAYMENTS -> Admin_Parent.setCenter(Model.getInstance().getViewsfactory().getPaymentsviewWindow());
                case HOUSES -> Admin_Parent.setCenter(Model.getInstance().getViewsfactory().getHousesview());
                case ADD_PROPERTY -> Admin_Parent.setCenter(Model.getInstance().getViewsfactory().getAddpropertywindow());
                case UTILITY_LEVEL -> Admin_Parent.setCenter(Model.getInstance().getViewsfactory().showWaterLevelMonitoringWindow());
                case NOTICES -> Admin_Parent.setCenter(Model.getInstance().getViewsfactory().showNoticesFiled());
                case COMPLAINTS -> Admin_Parent.setCenter(Model.getInstance().getViewsfactory().showComplaintsFiled());
                default -> Admin_Parent.setCenter(Model.getInstance().getViewsfactory().getDashboard());
            }
        });
    }
}
