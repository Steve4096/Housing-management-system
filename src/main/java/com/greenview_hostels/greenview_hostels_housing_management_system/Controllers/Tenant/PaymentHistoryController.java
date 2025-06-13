package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Payment;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Receipt;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Tenant;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class PaymentHistoryController implements Initializable {
    public TableView paymentsTable;
    public TableColumn<Payment,String> tenantNameColumn;
    public TableColumn<Payment,String> unitNumberColumn;
    public TableColumn<Payment,String> paymentTypeColumn;
    public TableColumn<Payment, BigDecimal> amountColumn;
    public TableColumn<Payment,LocalDate> rentMonthcolumn;
    public TableColumn<Payment,LocalDate> paymentDateColumn;
    public TextField amountTxtField;
    public ComboBox<String> paymentTypeCmbBox;
    public ComboBox<String> paymentDateCmbbox;

    public Tenant tenant;
    public Payment payment;


    private final ObservableList<Payment> masterData = FXCollections.observableArrayList();

    public void setTenant(Tenant tenant){
        this.tenant=tenant;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureColumns();
        loadData();
        ObservableList<String> paymentTypeOptions = FXCollections.observableArrayList("All", "Rent", "Deposit");
        ObservableList<String> months=FXCollections.observableArrayList("All","January","February","March","April","May","June","July","August","September","October","November","December");
        paymentTypeCmbBox.setItems(paymentTypeOptions);
        paymentDateCmbbox.setItems(months);
        setupFilters();
    }

    private void configureColumns(){
        tenantNameColumn.setCellValueFactory(data -> data.getValue().tenantNameProperty());
        unitNumberColumn.setCellValueFactory(data -> data.getValue().unitNumberProperty());
        amountColumn.setCellValueFactory(data -> data.getValue().amountProperty());
        paymentTypeColumn.setCellValueFactory(data -> data.getValue().paymentTypeProperty());
        rentMonthcolumn.setCellValueFactory(data -> data.getValue().rentMonthProperty());
        paymentDateColumn.setCellValueFactory(data -> data.getValue().dateIssuedProperty());

        // Format month display
        rentMonthcolumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + item.getYear());
                }
            }
        });

        // Load data
        ObservableList<Payment> paymentsList = Model.getInstance().paymentDetails();
        paymentsTable.setItems(paymentsList);
    }

    private void loadData(){
        masterData.addAll(Model.getInstance().paymentDetails());
    }

    private void setupFilters() {
        // Load all data
        ObservableList<Payment> payments = Model.getInstance().paymentDetails();
        masterData.setAll(payments);

        // Default selections
        paymentTypeCmbBox.getSelectionModel().selectFirst();
        paymentDateCmbbox.getSelectionModel().selectFirst();

        // Wrap in FilteredList
        FilteredList<Payment> filteredData = new FilteredList<>(masterData, p -> true);

        // Listeners to apply filters
        ChangeListener<Object> filterListener = (obs, oldVal, newVal) -> filteredData.setPredicate(receipt -> {
            // Payment type filter
            boolean paymentTypeMatches = paymentTypeCmbBox.getValue().equals("All") ||
                    receipt.getPaymentType().equalsIgnoreCase(paymentTypeCmbBox.getValue());

            // Amount filter
            boolean amountMatches = amountTxtField.getText().isEmpty() ||
                    receipt.getAmount().toPlainString().contains(amountTxtField.getText());

            // Rent month filter — handle null rentMonth
            boolean monthMatches;
            if (paymentDateCmbbox.getValue().equals("All")) {
                monthMatches = true;
            } else {
                if (receipt.getRentMonth() == null) {
                    monthMatches = false;
                } else {
                    String receiptMonth = receipt.getRentMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                    monthMatches = receiptMonth.equalsIgnoreCase(paymentDateCmbbox.getValue());
                }
            }

            return paymentTypeMatches && amountMatches && monthMatches;
        });

        paymentTypeCmbBox.valueProperty().addListener(filterListener);
        amountTxtField.textProperty().addListener(filterListener);
        paymentDateCmbbox.valueProperty().addListener(filterListener);

// SortedList for TableView sorting
        SortedList<Payment> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(paymentsTable.comparatorProperty());

        paymentsTable.setItems(sortedData);

    }

}
