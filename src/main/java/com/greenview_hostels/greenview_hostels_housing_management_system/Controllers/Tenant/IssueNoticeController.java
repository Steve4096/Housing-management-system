package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Tenant;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class IssueNoticeController  implements Initializable {
    public Button Submit_btn;
    public DatePicker Date_box;
    public Label Name_lbl;
    public Label House_no_lbl;
    public Label error_lbl;
    public Label CurrDate_lbl;

    public Tenant tenant;

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
        loadTenantSpecificDetails();
    }

    LocalDate currentDateTime=LocalDate.now();
    LocalDate thirtyDaysAfter =currentDateTime.plusDays(30);
    DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private boolean isNoticeValid;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //loadTenantSpecificDetails();
        isDateSelectedValid();
        CurrDate_lbl.setText(currentDateTime.format(dateTimeFormatter));
    }

    private void loadTenantSpecificDetails(){
        Name_lbl.setText(tenant.fnameProperty().get().concat(" ").concat(tenant.lnameProperty().get()));
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
                //showSuccessMessage();
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
