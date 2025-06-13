package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Property;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Receipt;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Tenant;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Button CloseProfileTab_btn;
    public Label House_no_lbl;
    public Label Rental_status_lbl;
    public Button UpdateProfile_btn;
    public Label ID_lbl;
    public Label Phone_no_lbl;
    public Label Email_address_lbl;
    public Label Name_lbl;

    public Tenant tenant;
    public Property property;

    public void setTenant(Tenant tenant){
        System.out.println("Setting tenant to: "+tenant);
        this.tenant=tenant;
        System.out.println("Tenant set to: "+tenant);
        showTenantSpecificDetails();
    }

    private final ObservableList<Receipt> masterData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void showTenantSpecificDetails(){
        if (tenant!=null){
            Name_lbl.textProperty().unbind();
            Email_address_lbl.textProperty().unbind();
            Phone_no_lbl.textProperty().unbind();
            House_no_lbl.textProperty().unbind();

            ID_lbl.textProperty().bind(Bindings.concat(tenant.tenantIDProperty()));
            Name_lbl.textProperty().bind(Bindings.concat(tenant.tenantNameProperty()));
            Email_address_lbl.setText(tenant.emailAddressProperty().get());
            Phone_no_lbl.setText(tenant.phoneNoProperty().get());
            House_no_lbl.setText(tenant.unitNumberProperty().get());
        }
    }



}
