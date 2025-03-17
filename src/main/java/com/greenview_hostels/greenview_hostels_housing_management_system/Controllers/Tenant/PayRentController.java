package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class PayRentController implements Initializable {
    public Label HouseStore_lbl;
    public ComboBox HouseStoreSelector;
    public Label Balance_lbl;
    public TextField Amount_txtfield;
    public Label AmountErrorLbl;
    public TextField Payee_number_txtfield;
    public Button Continue_btn;
    public Button Cancel_btn;
    public ComboBox Monthselector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Monthselector.setItems(FXCollections.observableArrayList(
                Month.JANUARY.getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                Month.FEBRUARY.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.MARCH.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.APRIL.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.MAY.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.JUNE.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.JULY.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.AUGUST.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.SEPTEMBER.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.OCTOBER.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.NOVEMBER.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.DECEMBER.getDisplayName(TextStyle.FULL,Locale.ENGLISH)
        ));
    }
}
