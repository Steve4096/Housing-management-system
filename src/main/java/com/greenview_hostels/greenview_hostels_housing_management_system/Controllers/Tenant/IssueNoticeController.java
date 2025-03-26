package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class IssueNoticeController  implements Initializable {
    public Button Submit_btn;
    public DatePicker Date_box;
    public Label Name_lbl;
    public Label House_no_lbl;
    public Label error_lbl;

    LocalDate currentDateTime=LocalDate.now();
    LocalDate selectedDateTime=Date_box.getValue();
    LocalDate thirtyDaysBefore=currentDateTime.minusDays(30);
    private boolean isNoticeValid;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void isDateSelectedValid(){
        Date_box.valueProperty().addListener((observableValue, localDate, newVal) ->
        {
            if (newVal == thirtyDaysBefore) {
                isNoticeValid=true;
                //showSuccessMessage();
            }else {
                showErrorAlert();
                isNoticeValid=false;
            }
        });
    }

    private void showErrorAlert(){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText("ERROR!THE SELECTED DATE SHOULD BE 30 DAYS MORE THAN THE CURRENT DATE");
        alert.showAndWait();
    }

    private void showSuccessMessage(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("SUCCESS");
        alert.setContentText("YOUR NOTICE HAS BEEN SUBMITTED SUCCESSFULLY");
        alert.showAndWait();
    }


}
