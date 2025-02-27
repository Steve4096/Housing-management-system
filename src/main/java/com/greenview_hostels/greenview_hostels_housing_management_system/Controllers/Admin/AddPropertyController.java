package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPropertyController implements Initializable {
    public TextField Unit_number;
    public TextField Unit_type;
    public TextField Rent_amount;
    public Label Error_lbl;
    public Button Add_btn;
    public Label Rent_format_label;

    private String unitNumber;
    private String unitType;
    private BigDecimal rentAmount;
    //private String rent=Rent_amount.getText().trim();
    private boolean isRentAmountValid;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Checkrentamountformat();
        Add_btn.setOnAction(actionEvent -> Addproperty());
    }

    private BigDecimal ConvertrentamounttoBigDecimal(){
        String rent=Rent_amount.getText().trim();
        return new BigDecimal(rent);
    }

    private boolean Allfieldsfilled(){
        unitNumber=Unit_number.getText().trim();
        unitType=Unit_type.getText().trim();
        String rent=Rent_amount.getText().trim();
        if(unitNumber.isEmpty() || unitType.isEmpty() || rent.isEmpty()) {
            Error_lbl.setText("Please fill all fields before proceeding");
            return false;
        }Error_lbl.setText("");
        return true;
    }

   private void Checkrentamountformat(){
        Rent_amount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (newValue.matches("\\d+")){
                    Rent_format_label.setText("");
                    isRentAmountValid=true;
                }else {
                    Rent_format_label.setText("Rent payable should be in numbers");
                    isRentAmountValid=false;
                }
            }
        });
   }

   private void Addproperty() {
       if (Allfieldsfilled() && isRentAmountValid) {
           rentAmount = ConvertrentamounttoBigDecimal();
           Model.getInstance().getDatabaseConnection().Addproperty(unitNumber, unitType, rentAmount);
           resetAllFields();
           showSuccessMessage();
       }
   }

   private void resetAllFields(){
        Unit_number.setText("");
        Unit_type.setText("");
        Rent_amount.setText("");
   }

   private void showSuccessMessage(){
       Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle("SUCCESS");
       alert.setHeaderText(null);
       alert.setContentText("PROPERTY NUMBER"+" "+unitNumber+" "+"OF TYPE"+" "+unitType+" "+"HAS BEEN SUCCESSFULLY ADDED.");
       alert.showAndWait();
   }




}
