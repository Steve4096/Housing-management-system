package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Tenant;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    public Label IDNo_lbl;
    //private ObjectProperty<Tenant> tenant=new SimpleObjectProperty<>();

    public void setTenant(Tenant tenant) {
        this.tenant=tenant;
        loadTenantSpecificDetails();
        Model.getInstance().populateHouseNumber(House_lbl,House_or_Store_selector);
    }

    ObservableList<String> options= FXCollections.observableArrayList(
            "Administrative","Complaint","Repair/Maintenance"
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Request_nature_selector.setItems(options);
        //Model.getInstance().populateHouseNumber(House_lbl,House_or_Store_selector);
        Submit_btn.setOnAction(actionEvent -> fileComplaint());
    }

    private void loadTenantSpecificDetails(){
        Name_lbl.textProperty().unbind();
        //Name_lbl.textProperty().bind(Bindings.concat(tenant.fnameProperty()," ",tenant.lnameProperty()));
        Name_lbl.textProperty().bind(Bindings.concat(tenant.tenantNameProperty()));
        IDNo_lbl.textProperty().bind(Bindings.concat(tenant.tenantIDProperty()));
    }

    private void fileComplaint(){
        String id=IDNo_lbl.getText();
        Integer IDNo=Integer.parseInt(id);
        //String unitNumber=House_lbl.getText() || House_or_Store_selector.getValue();
        String unitNumber;
        if (House_or_Store_selector.getValue() != null) {
            unitNumber = House_or_Store_selector.getValue().toString();
        } else {
            unitNumber = House_lbl.getText();
        }
        String requestNature=Request_nature_selector.getValue().toString();
        String requestDescription=Request_description_txtarea.getText();
        if (isRequestValid()){
            int unit_number=Integer.parseInt(unitNumber);
            Model.getInstance().getDatabaseConnection().fileComplaint(IDNo,unit_number,requestNature,requestDescription);
            resetAllFields();
        }

    }

    private boolean isRequestValid(){
        boolean isRequestValid=true;
        if (Request_nature_selector.getValue()==null){
            isRequestValid=false;
            selectComplaintNatureAlert();
        } else if (Request_description_txtarea.getText().isEmpty()){
            isRequestValid=false;
            provideComplaintDescriptionAlert();
        }
        return isRequestValid;
    }

    private void selectComplaintNatureAlert(){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("");
        alert.setContentText("PLEASE SELECT A VALID COMPLAINT NATURE");
        alert.showAndWait();
    }

    private void provideComplaintDescriptionAlert(){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("");
        alert.setContentText("PLEASE PROVIDE A VALID COMPLAINT DESCRIPTION");
        alert.showAndWait();
    }



    private void resetAllFields(){
        Request_nature_selector.setValue("");
        Request_description_txtarea.setText("");
    }
}
