package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Receipt;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Tenant;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
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

    public Tenant tenant;




    public void setTenant(Tenant tenant){
        this.tenant=tenant;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tenantNamecolumn.setCellValueFactory(data -> data.getValue().tenantNameProperty());
        unitNumberColumn.setCellValueFactory(data -> data.getValue().unitNumberProperty());
        receiptNumberColumn.setCellValueFactory(data -> data.getValue().receiptNumberProperty());
        amountColumn.setCellValueFactory(data -> data.getValue().amountProperty());
        paymentTypeColumn.setCellValueFactory(data -> data.getValue().paymentTypeProperty());
        monthPaidForColumn.setCellValueFactory(data -> data.getValue().rentMonthProperty());
        dateIssuedColumn.setCellValueFactory(data -> data.getValue().dateIssuedProperty());

        // Load data
        ObservableList<Receipt> receiptList = Model.getInstance().receiptDetails();
        receiptsTableView.setItems(receiptList);
    }
}
