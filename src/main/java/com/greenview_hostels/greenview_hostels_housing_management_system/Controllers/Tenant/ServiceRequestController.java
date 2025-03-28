package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Tenant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ServiceRequestController implements Initializable {
    public Label Name_lbl;
    public Label House_lbl;
    public ComboBox House_or_Store_selector;
    public ComboBox Request_nature_selector;
    public TextArea Request_description_txtarea;
    public Button Submit_btn;

    public Tenant tenant;

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
        loadTenantSpecificDetails();
    }

    ObservableList<String> options= FXCollections.observableArrayList(
            "Complaint","Repair/Maintenance"
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Request_nature_selector.setItems(options);
        Submit_btn.setOnAction(actionEvent -> resetAllFields());
    }

    private void loadTenantSpecificDetails(){
        Name_lbl.setText(tenant.fnameProperty().get().concat(" ").concat(tenant.lnameProperty().get()));
    }

    private void resetAllFields(){
        Request_nature_selector.setValue("");
        Request_description_txtarea.setText("");
    }
}
