package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Property;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Tenant;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class IssueNoticeController  implements Initializable {
    public Button Submit_btn;
    public DatePicker Date_box;
    public Label Name_lbl;
    public Label ID_No_lbl;
    public Label House_no_lbl;
    public Label error_lbl;
    public Label CurrDate_lbl;
    public ComboBox HouseNo_Combobox;

    public Tenant tenant;



    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
        loadTenantSpecificDetails();
        Model.getInstance().populateHouseNumber(House_no_lbl,HouseNo_Combobox);
    }

    LocalDate currentDateTime=LocalDate.now();
    LocalDate thirtyDaysAfter =currentDateTime.plusDays(30);
    DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private boolean isNoticeValid;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isDateSelectedValid();
        CurrDate_lbl.setText(currentDateTime.format(dateTimeFormatter));
        Submit_btn.setOnAction(actionEvent -> fileNotice());
    }

    private void loadTenantSpecificDetails(){
        Name_lbl.textProperty().unbind();
        Name_lbl.textProperty().bind(Bindings.concat(tenant.tenantNameProperty()));
        ID_No_lbl.textProperty().bind(Bindings.concat(tenant.tenantIDProperty()));
    }

    /*public void populateHouseNumber(Tenant tenant){
        //ObservableList<Tenant> allTenants= Model.getInstance().showExistingTenantDetails();
        //Tenant loggedInTenant=Model.getInstance().getTenant();
        ObservableList<Property> properties= tenant.getProperties();
        if(properties.size()==1){
            House_no_lbl.setVisible(true);
            House_no_lbl.setText(properties.get(0).unitNumberProperty().get());
            HouseNo_Combobox.setVisible(false);
        } else if (properties.size()>1) {
            ObservableList<String> unitNumbers= FXCollections.observableArrayList();
            for (Property property:properties){
                unitNumbers.add(property.unitNumberProperty().get());
            }
            HouseNo_Combobox.setVisible(true);
            HouseNo_Combobox.setItems(unitNumbers);
            House_no_lbl.setVisible(false);
        }else {
            House_no_lbl.setVisible(true);
            House_no_lbl.setText("No house found");
            HouseNo_Combobox.setVisible(false);
        }
    }*/

    /*public void populateHouseNumber(){
        ObservableList<Tenant> allTenants=Model.getInstance().showExistingTenantDetails();
        Tenant loggedInTenant=Model.getInstance().getTenant();

        //Find the logged in tenant
        Tenant matchingTenant=allTenants.stream()
                .filter(tenant->tenant.tenantIDProperty().get().equals(loggedInTenant.tenantIDProperty().get()))
                .findFirst()
                .orElse(null);

        if (matchingTenant!=null){
            ObservableList<Property> properties=matchingTenant.getProperties();
            if (properties.size()==1){
                House_no_lbl.setVisible(true);
                House_no_lbl.setText(properties.get(0).unitNumberProperty().get());
                HouseNo_Combobox.setVisible(false);
            } else if (properties.size()>1) {
                ObservableList<String> unitNumbers=FXCollections.observableArrayList();
                for (Property property:properties){
                    unitNumbers.add(property.unitNumberProperty().get());
                }
                HouseNo_Combobox.setVisible(true);
                HouseNo_Combobox.setItems(unitNumbers);
                House_no_lbl.setVisible(false);
            }else {
                House_no_lbl.setVisible(true);
                House_no_lbl.setText("No house found");
                HouseNo_Combobox.setVisible(false);
            }
        }

    }*/

    private void fileNotice(){
        String IDNumber=ID_No_lbl.getText();
        Integer IDNo=Integer.parseInt(IDNumber);
        String unitNumber;
        if (HouseNo_Combobox.getValue() != null) {
            unitNumber = HouseNo_Combobox.getValue().toString();
        } else {
            unitNumber = House_no_lbl.getText();
        }

        LocalDate date=Date_box.getValue();
        if (isNoticeValid){
            int unit_number=Integer.parseInt(unitNumber);
            java.sql.Date sqlDate=java.sql.Date.valueOf(date);
            Model.getInstance().getDatabaseConnection().fileNotice(IDNo,unit_number,sqlDate);
            showSuccessMessage();
        }
    }

    private void isDateSelectedValid(){
        Date_box.valueProperty().addListener((observableValue, localDate, newVal) ->
        {
            if (newVal==null){
                showNullDateErrorAlert();
                isNoticeValid=false;
            } else if (newVal.isBefore(thirtyDaysAfter)) {
                showInvalidDateErrorMessage();
                isNoticeValid=false;
            }else {
                isNoticeValid=true;
            }
        });
    }

    private void showNullDateErrorAlert(){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("");
        alert.setContentText("PLEASE SELECT A VALID DATE");
        alert.showAndWait();
    }

    private void showInvalidDateErrorMessage(){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("");
        alert.setContentText("ERROR!THE SELECTED DATE SHOULD BE 30 DAYS MORE THAN THE CURRENT DATE");
        alert.showAndWait();
    }

    private void showSuccessMessage(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("");
        alert.setTitle("SUCCESS");
        alert.setContentText("YOUR NOTICE HAS BEEN SUBMITTED SUCCESSFULLY");
        alert.showAndWait();
    }


}
