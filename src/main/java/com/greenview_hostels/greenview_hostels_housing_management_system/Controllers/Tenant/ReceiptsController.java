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
    public ComboBox paymentTypeFilterCombobox;
    public ComboBox monthFilterCombobox;
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

}
