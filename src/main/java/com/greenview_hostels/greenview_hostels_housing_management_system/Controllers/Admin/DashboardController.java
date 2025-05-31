package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Payment;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Receipt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Label RentPaid_lbl;
    public Label PenaltiesPayable_lbl;
    public Label FreeHouses_lbl;
    public Label Complaints_lbl;
    public Label Notices_lbl;
    public TableView recentTransactionsTable;
    public TableColumn<Payment,String> tenantNameColumn;
    public TableColumn<Payment,String> unitNumberColumn;
    public TableColumn<Payment, BigDecimal> amountPaidColumn;
    public TableColumn<Payment,LocalDate>rentMonthColumn;
    public TableColumn<Payment,LocalDate> transactionDateColumn;
    public TableColumn<Payment,String> paymentTypeColumn;

    private final ObservableList<Payment> masterData = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countFreeHouses();
        countTotalNoticesFiled();
        countComplaintsFiled();
        configureColumns();
        loadData();
    }

    private void configureColumns(){
        tenantNameColumn.setCellValueFactory(data -> data.getValue().tenantNameProperty());
        unitNumberColumn.setCellValueFactory(data -> data.getValue().unitNumberProperty());
        //receiptNumberColumn.setCellValueFactory(data -> data.getValue().receiptNumberProperty());
        amountPaidColumn.setCellValueFactory(data -> data.getValue().amountProperty());
        paymentTypeColumn.setCellValueFactory(data -> data.getValue().paymentTypeProperty());
        rentMonthColumn.setCellValueFactory(data -> data.getValue().rentMonthProperty());
        transactionDateColumn.setCellValueFactory(data -> data.getValue().dateIssuedProperty());

        // Format month display
        rentMonthColumn.setCellFactory(col -> new TableCell<>() {
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
        ObservableList<Payment> receiptList = Model.getInstance().get5MostRecentPayments();
        recentTransactionsTable.setItems(receiptList);
    }

    private void loadData(){
        masterData.addAll(Model.getInstance().get5MostRecentPayments());
    }



    private void countFreeHouses(){
        int freeHouses= Model.getInstance().getDatabaseConnection().countUnoccupiedHouses();
        FreeHouses_lbl.setText(String.valueOf(freeHouses));
    }

    private void countTotalNoticesFiled(){
        int noticesFiled=Model.getInstance().getDatabaseConnection().countNoticesFiled();
        Notices_lbl.setText(String.valueOf(noticesFiled));
    }

    private void countComplaintsFiled(){
        int complaintsFiled=Model.getInstance().getDatabaseConnection().countComplaintsFiled();
        Complaints_lbl.setText(String.valueOf(complaintsFiled));
    }

}
