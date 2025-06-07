package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class PaymentsController implements Initializable {
    public TextField Search_txtfield;
    public ListView Payments_listview;
    public Button Search_btn;
    public Button Filter;
    public Pagination pagination;
    public DatePicker Date_from_Picker;
    public DatePicker Date_to_picker;
    public Button Filter_btn;
    public TableView paymentsTable;
    public TableColumn<Payment,String> tenantNameColumn;
    public TableColumn<Payment,String> unitNumberColumn;
    public TableColumn<Payment, BigDecimal> amountPaidColumn;
    public TableColumn<Payment,LocalDate> rentMonthColumn;
    public TableColumn<Payment,LocalDate> transactionDateColumn;
    public TableColumn<Payment,String> paymentTypeColumn;


    private static final int ROWS_PER_PAGE = 10;
    private final ObservableList<Payment> masterData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureColumns();
        loadData();
        //setupPagination();
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
        ObservableList<Payment> paymentList = Model.getInstance().paymentsForAll();
        paymentsTable.setItems(paymentList);
    }

//    private void setupPagination() {
//        int totalRows = Model.getInstance().getDatabaseConnection().getTotalPaymentCount(); // You need to implement this
//        int pageCount = (int) Math.ceil((double) totalRows / ROWS_PER_PAGE);
//        pagination.setPageCount(pageCount);
//        pagination.setCurrentPageIndex(0);
//        pagination.setPageFactory(this::createPage);
//    }

//    private TableView<Payment> createPage(int pageIndex) {
//        int offset = pageIndex * ROWS_PER_PAGE;
//        ObservableList<Payment> paymentsPage = Model.getInstance().getDatabaseConnection().getPaymentsPage(offset,ROWS_PER_PAGE);
//        paymentsTable.setItems(paymentsPage);
//        return paymentsTable;
//    }


    private void loadData(){
        masterData.addAll(Model.getInstance().paymentsForAll());
    }
}
