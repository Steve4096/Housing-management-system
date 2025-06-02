package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
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

public class ReceiptsController implements Initializable {
    public TableColumn<Receipt,String> tenantNamecolumn;
    public TableColumn<Receipt,String> unitNumberColumn;
    public TableColumn<Receipt,String> receiptNumberColumn;
    public TableColumn<Receipt, BigDecimal> amountColumn;
    public TableColumn<Receipt,String> paymentTypeColumn;
    public TableColumn<Receipt, LocalDate> monthPaidForColumn;
    public TableColumn<Receipt,LocalDate> dateIssuedColumn;
    public TableView receiptsTableView;
    public ComboBox<String> paymentTypeFilterCombobox;
    public ComboBox<String> monthFilterCombobox;
    public TextField amountFilterTextBox;

    public Tenant tenant;


    private final ObservableList<Receipt> masterData = FXCollections.observableArrayList();



    public void setTenant(Tenant tenant){
        this.tenant=tenant;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureColumns();
        loadData();
        ObservableList<String> paymentTypeOptions = FXCollections.observableArrayList("All", "Rent", "Deposit");
        ObservableList<String> months=FXCollections.observableArrayList("All","January","February","March","April","May","June","July","August","September","October","November","December");
        paymentTypeFilterCombobox.setItems(paymentTypeOptions);
        monthFilterCombobox.setItems(months);
        setupFilters();
    }

    private void configureColumns(){
        tenantNamecolumn.setCellValueFactory(data -> data.getValue().tenantNameProperty());
        unitNumberColumn.setCellValueFactory(data -> data.getValue().unitNumberProperty());
        receiptNumberColumn.setCellValueFactory(data -> data.getValue().receiptNumberProperty());
        amountColumn.setCellValueFactory(data -> data.getValue().amountProperty());
        paymentTypeColumn.setCellValueFactory(data -> data.getValue().paymentTypeProperty());
        monthPaidForColumn.setCellValueFactory(data -> data.getValue().rentMonthProperty());
        dateIssuedColumn.setCellValueFactory(data -> data.getValue().dateIssuedProperty());

        // Format month display
        monthPaidForColumn.setCellFactory(col -> new TableCell<>() {
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
        ObservableList<Receipt> receiptList = Model.getInstance().receiptDetails();
        receiptsTableView.setItems(receiptList);
    }

    private void loadData(){
        masterData.addAll(Model.getInstance().receiptDetails());
    }

    public void filterData(){
        FilteredList<Receipt> filteredData = new FilteredList<>(masterData, p -> true);
    }


    private void setupFilters() {
        // Load all data
        ObservableList<Receipt> receipts = Model.getInstance().receiptDetails();
        masterData.setAll(receipts);

       // Default selections
        paymentTypeFilterCombobox.getSelectionModel().selectFirst();
        monthFilterCombobox.getSelectionModel().selectFirst();

        // Wrap in FilteredList
        FilteredList<Receipt> filteredData = new FilteredList<>(masterData, p -> true);

        // Listeners to apply filters
        ChangeListener<Object> filterListener = (obs, oldVal, newVal) -> filteredData.setPredicate(receipt -> {
            // Payment type filter
            boolean paymentTypeMatches = paymentTypeFilterCombobox.getValue().equals("All") ||
                    receipt.getPaymentType().equalsIgnoreCase(paymentTypeFilterCombobox.getValue());

            // Amount filter
            boolean amountMatches = amountFilterTextBox.getText().isEmpty() ||
                    receipt.getAmount().toPlainString().contains(amountFilterTextBox.getText());

            // Rent month filter — handle null rentMonth
            boolean monthMatches;
            if (monthFilterCombobox.getValue().equals("All")) {
                monthMatches = true;
            } else {
                if (receipt.getRentMonth() == null) {
                    monthMatches = false;
                } else {
                    String receiptMonth = receipt.getRentMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                    monthMatches = receiptMonth.equalsIgnoreCase(monthFilterCombobox.getValue());
                }
            }

            return paymentTypeMatches && amountMatches && monthMatches;
        });

        paymentTypeFilterCombobox.valueProperty().addListener(filterListener);
        amountFilterTextBox.textProperty().addListener(filterListener);
        monthFilterCombobox.valueProperty().addListener(filterListener);

// SortedList for TableView sorting
        SortedList<Receipt> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(receiptsTableView.comparatorProperty());

        receiptsTableView.setItems(sortedData);

    }

    }
