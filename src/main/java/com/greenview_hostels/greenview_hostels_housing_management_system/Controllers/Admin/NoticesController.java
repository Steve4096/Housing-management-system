package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Notices;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Payment;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class NoticesController implements Initializable {
    public TableColumn<Notices,String> TenantName;
    public TableColumn<Notices,String> UnitNumber;
    public TableColumn<Notices, LocalDate> DayNoticeFiled;
    public TableColumn<Notices,LocalDate> DateIntendToLeave;
    public TableView noticesTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureColumns();
    }

    private void configureColumns(){
        TenantName.setCellValueFactory(data -> data.getValue().tenantNameProperty());
        UnitNumber.setCellValueFactory(data -> data.getValue().unitNumberProperty());
        DayNoticeFiled.setCellValueFactory(data -> data.getValue().dateNoticeIssuedProperty());
        DateIntendToLeave.setCellValueFactory(data -> data.getValue().dateIntendToLeaveProperty());

        // Load data
        ObservableList<Notices> noticesList = Model.getInstance().getNotices();
        noticesTable.setItems(noticesList);
    }
}
