package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Tenant;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class PayRentController implements Initializable {
    public Label Name_lbl;
    public ComboBox<String> paymentType_cmbbox;
    public Label IdNo_lbl;
    public Label HouseStore_lbl;
    public ComboBox<String> HouseStoreSelector;
    public Label Balance_lbl;
    public TextField Amount_txtfield;
    public Label AmountErrorLbl;
    public Button Continue_btn;
    public Button Cancel_btn;
    public ComboBox<Month> Monthselector;
    public Label Errorlbl;
    public boolean isRentAmountValid;
    public Label monthtoPayForLbl;


    public Tenant tenant;




    public void setTenant(Tenant tenant){
        this.tenant=tenant;
        loadTenantSpecificDetails();
        setPaymentOptions();
        Model.getInstance().populateHouseNumber(HouseStore_lbl,HouseStoreSelector);
    }

   // BigDecimal rentAmount = convertRentAmountToBigDecimal();
    String idNo;
    int id;
    //String receiptNumber=generateReceiptNumber();
    String unitNumber;
    String monthNameSelected;
    Month monthSelected;
    LocalDate firstOfMonth;
    Date date;
    String year=LocalDate.now().getYear()+"";



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkRentAmountFormat();
        Monthselector.setItems(FXCollections.observableArrayList(Month.values()));

        paymentType_cmbbox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.equals("DEPOSIT")) {
                monthtoPayForLbl.setVisible(false);
                Monthselector.setVisible(false);
                //Amount_txtfield.setText("10000");
                //Amount_txtfield.setDisable(true);
                isRentAmountValid = true; // force valid since it's preset
            } else if (newValue != null && newValue.equals("RENT")) {
                monthtoPayForLbl.setVisible(true);
                Monthselector.setVisible(true);
                Amount_txtfield.clear();
                Amount_txtfield.setDisable(false);
            }
        });
        Cancel_btn.setOnAction(actionEvent -> resetAllFields());
        Continue_btn.setOnAction(actionEvent -> makePayment());
    }



    private void loadTenantSpecificDetails(){
        Name_lbl.setText(tenant.tenantNameProperty().get());
        //IdNo_lbl.setText(tenant.tenantIDProperty().get());
        IdNo_lbl.textProperty().bind(Bindings.concat(tenant.tenantIDProperty()));
    }

    private void setPaymentOptions(){
        idNo=IdNo_lbl.getText().trim();
        id=Integer.parseInt(idNo);
        ObservableList<String> paymentOptions= FXCollections.observableArrayList();
        if (!Model.getInstance().getDatabaseConnection().checkDepositPayment(id)){
            paymentOptions.addAll("DEPOSIT","RENT");
            paymentType_cmbbox.setPromptText("SELECT PAYMENT TYPE");
        }else {
            paymentOptions.add("RENT");
            //paymentType_cmbbox.setItems("RENT");
            paymentType_cmbbox.setValue("RENT");
        }
        paymentType_cmbbox.setItems(paymentOptions);
    }

    private String generateReceiptNumber(){
        Random random=new Random();
        String datePart= LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomNumber=100000+random.nextInt(900000);
        return "RCPT-"+datePart+"-"+randomNumber;
    }


    private void makePayment() {
        BigDecimal rentAmount = convertRentAmountToBigDecimal();
        // Assign unitNumber
        if (HouseStoreSelector.getValue() != null) {
            unitNumber = HouseStoreSelector.getValue().toString();
        } else {
            unitNumber = HouseStore_lbl.getText();
        }

        String paymentType = paymentType_cmbbox.getValue();

        if ("DEPOSIT".equals(paymentType)) {
            if (Model.getInstance().getDatabaseConnection().checkDepositPayment(id)) {
                showInfo("You have already deposited your amount");
            } else {
                if (checkIfDepositIsEnough()) {
                    String receiptNumber=generateReceiptNumber();
                    Model.getInstance().getDatabaseConnection().payDeposit(id, unitNumber, rentAmount, receiptNumber);
                    System.out.println("Receipt number generated is: "+receiptNumber);
                    showSuccessMessage("Your deposit has been paid successfully");
                    resetAllFields();
                } else {
                    showError("Deposit amount should be 10,000 only.");
               }
            }

        } else if ("RENT".equals(paymentType)) {
            if (!Model.getInstance().getDatabaseConnection().checkDepositPayment(id)) {
                showError("You must pay the full deposit amount before paying rent");
                return;
            }

                Object selectedValue = Monthselector.getValue();
                if (selectedValue == null) {
                    showError("Please select a valid month");
                    return;
                }

                try {
                    monthSelected = (Month) selectedValue;
                } catch (ClassCastException e) {
                    showError("Invalid month selected");
                    return;
                }

                monthNameSelected = monthSelected.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                firstOfMonth = LocalDate.of(LocalDate.now().getYear(), monthSelected, 1);
                date = java.sql.Date.valueOf(firstOfMonth);

            boolean isRentPaidEnough=checkRentAmountPaid();

                if(isRentPaidEnough&&isRentAmountValid) {
                    String receiptNumber = generateReceiptNumber();
                    Model.getInstance().getDatabaseConnection().payRent(id, unitNumber, rentAmount, date, receiptNumber);
                    showSuccessMessage("Your " + monthNameSelected + " rent has been paid successfully");
                    resetAllFields();
                }
        }
    }



    private boolean checkIfDepositIsEnough(){
        BigDecimal rentAmount = convertRentAmountToBigDecimal();
        return rentAmount.compareTo(BigDecimal.valueOf(10000))==0;
    }


    private boolean checkRentAmountFormat(){
        Amount_txtfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (newValue.matches("\\d+")){
                    isRentAmountValid=true;
                }else {
                    showError("Rent payable should be in numbers");
                    isRentAmountValid=false;
                }
            }
        });
        return isRentAmountValid;
    }

    private boolean checkRentAmountPaid(){
        BigDecimal minimumRentamount=new BigDecimal("100");
        BigDecimal rent=convertRentAmountToBigDecimal();
        return rent.compareTo(minimumRentamount)>=0;
    }

    private BigDecimal convertRentAmountToBigDecimal(){
        String rent=Amount_txtfield.getText().trim();
        return new BigDecimal(rent);
    }

    private void showError(String message){
        Errorlbl.setText(message);
        Errorlbl.setStyle("-fx-text-fill: red");
    }

    private void showInfo(String message){
        Errorlbl.setText(message);
        Errorlbl.setStyle("-fx-text-fill: green");
    }

    private void showSuccessMessage(String message){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("SUCCESS");
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void resetAllFields(){
        HouseStoreSelector.setValue("");
        HouseStore_lbl.setText("");
        Amount_txtfield.setText("");
        AmountErrorLbl.setText("");
        Amount_txtfield.setDisable(false);
        Monthselector.setValue(null);
        Monthselector.setVisible(true);
        paymentType_cmbbox.setValue(null);
    }

}
