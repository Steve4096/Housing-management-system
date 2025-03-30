package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Tenant;
import javafx.beans.binding.Bindings;
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

    public void setTenant(Tenant tenant){
        System.out.println("Setting tenant to: "+tenant);
        this.tenant=tenant;
        System.out.println("Tenant set to: "+tenant);
        showTenantSpecificDetails();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void showTenantSpecificDetails(){
        if (tenant!=null){
            Name_lbl.textProperty().unbind();
            Email_address_lbl.textProperty().unbind();
            Phone_no_lbl.textProperty().unbind();

            /*ID_lbl.setText(tenant.tenantIDProperty().get());
            Name_lbl.setText(tenant.fnameProperty().get().concat(" ").concat(tenant.lnameProperty().get()));
            Email_address_lbl.setText(tenant.emailAddressProperty().get());
            Phone_no_lbl.setText(tenant.phoneNoProperty().get());*/
            ID_lbl.textProperty().bind(Bindings.concat(tenant.tenantIDProperty()));
            Name_lbl.textProperty().bind(Bindings.concat(tenant.fnameProperty()," ",tenant.lnameProperty()));
            Email_address_lbl.setText(tenant.emailAddressProperty().get());
            Phone_no_lbl.setText(tenant.phoneNoProperty().get());
        }
    }
}
