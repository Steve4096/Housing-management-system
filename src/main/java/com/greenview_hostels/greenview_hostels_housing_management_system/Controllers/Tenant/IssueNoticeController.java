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
        if(!Model.getInstance().getDatabaseConnection().checkIfNoticeFiled(IDNo)) {
            if (isNoticeValid) {
                int unit_number = Integer.parseInt(unitNumber);
                java.sql.Date sqlDate = java.sql.Date.valueOf(date);
                Model.getInstance().getDatabaseConnection().fileNotice(IDNo, unit_number, sqlDate);
                showSuccessMessage();
            }
        }else {
            showError("YOU HAVE ALREADY FILED A NOTICE. YOU CAN'T FILE ANOTHER WHILE THE OTHER IS PENDING");
        }
    }

    private void isDateSelectedValid(){
        Date_box.valueProperty().addListener((observableValue, localDate, newVal) ->
        {
            if (newVal==null){
                showError("PLEASE SELECT A VALID DATE");
                isNoticeValid=false;
            } else if (newVal.isBefore(thirtyDaysAfter)) {
                showError("ERROR!THE SELECTED DATE SHOULD BE 30 DAYS MORE THAN THE CURRENT DATE");
                isNoticeValid=false;
            }else {
                isNoticeValid=true;
            }
        });
    }

    private void showError(String message){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("");
        alert.setContentText(message);
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
