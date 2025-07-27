package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TenantRegistrationController implements Initializable {
    public Button Back_btn;
    public TextField TenantID_txtfield;
   // public TextField Fname_txtfield;
    //public TextField Lname_txtfield;
    public TextField Phonenumber_txtfield;
    public TextField Email_txtfield;
    public Label Email_errorlbl;
    public Button Save_btn;
    public ChoiceBox<String> House_number_selector;
    public TextField TenantFullname_txtfield;

    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        House_number_selector.setOnShowing(event -> loadAvailableProperties());
//        loadAvailableProperties();
//        Save_btn.setOnAction(actionEvent -> registerNewClient());
//    }
//
//    private void loadAvailableProperties() {
//        setupSaveButton();
//        Model.getInstance().showAvailableProperties();
//        House_number_selector.getItems().setAll(Model.getInstance().availableProperties);
//
//    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Link ComboBox items to the observable list ONCE
        House_number_selector.setItems(Model.getInstance().availableProperties);

        // Load the initial list
        loadAvailableProperties();

        // Every time dropdown opens, reload fresh available houses
        House_number_selector.setOnShowing(event -> loadAvailableProperties());

        // Setup save button once
        setupSaveButton();

        // Setup save action
        Save_btn.setOnAction(actionEvent -> registerNewClient());
    }

    private void loadAvailableProperties() {
        // Clear and reload houses from DB into the observable list
        Model.getInstance().showAvailableProperties();
    }


    private void registerNewClient(){
        String IDNo=TenantID_txtfield.getText();
        String name=TenantFullname_txtfield.getText();
        //String lname=Lname_txtfield.getText();
        String phoneNo=Phonenumber_txtfield.getText();
        String emailAddress=Email_txtfield.getText();
        String houseNo= House_number_selector.getValue();
        Model.getInstance().getDatabaseConnection().registerNewTenant(IDNo,name,phoneNo,emailAddress,houseNo);
        //loadAvailableProperties();
        resetAllFields();
    }

    private void resetAllFields(){
        TenantID_txtfield.setText("");
        TenantFullname_txtfield.setText("");
       // Lname_txtfield.setText("");
        Phonenumber_txtfield.setText("");
        Email_txtfield.setText("");
        House_number_selector.setValue("");
    }

    private void setupSaveButton(){
        Save_btn.disableProperty().bind(Bindings.createBooleanBinding(()->
                        TenantID_txtfield.getText().trim().isEmpty() || TenantFullname_txtfield.getText().trim().isEmpty() || Phonenumber_txtfield.getText().trim().isEmpty()
                                ||Email_txtfield.getText().trim().isEmpty() ||(House_number_selector.getValue()==null || House_number_selector.getValue().toString().trim().isEmpty()),
                TenantID_txtfield.textProperty(),TenantFullname_txtfield.textProperty(),Phonenumber_txtfield.textProperty(),
                Email_txtfield.textProperty(),House_number_selector.valueProperty()));
    }
}
