package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {
    public Button Back_btn;
    public TextField TenantID_txtfield;
    public TextField Fname_txtfield;
    public TextField Lname_txtfield;
    public TextField Phonenumber_txtfield;
    public TextField Email_txtfield;
    public Label Email_errorlbl;
    public Button Save_btn;
    public ChoiceBox<String> House_number_selector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAvailableProperties();
        Save_btn.setOnAction(actionEvent -> registerNewClient());
    }

    private void loadAvailableProperties(){
        Model.getInstance().showAvailableProperties();
        House_number_selector.getItems().setAll(Model.getInstance().availableProperties);

    }

    private void registerNewClient(){
        String IDNo=TenantID_txtfield.getText();
        String fname=Fname_txtfield.getText();
        String lname=Lname_txtfield.getText();
        String phoneNo=Phonenumber_txtfield.getText();
        String emailAddress=Email_txtfield.getText();
        String houseNo= House_number_selector.getValue();
        Model.getInstance().getDatabaseConnection().registerNewTenant(IDNo,fname,lname,phoneNo,emailAddress,houseNo);
        resetAllFields();
    }

    private void resetAllFields(){
        TenantID_txtfield.setText("");
        Fname_txtfield.setText("");
        Lname_txtfield.setText("");
        Phonenumber_txtfield.setText("");
        Email_txtfield.setText("");
        House_number_selector.setValue("");
    }
}
