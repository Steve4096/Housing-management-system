package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Complaints;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Notices;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ComplaintsController implements Initializable {
    public ComboBox dateFiledCombobox;
    public ComboBox complaintTypeCombobox;
    public TableView complaintsTable;
    public TableColumn<Complaints,String> tenantNameColumn;
    public TableColumn<Complaints,String> unitNumberColumn;
    public TableColumn<Complaints,String> complaintTypeColumn;
    public TableColumn<Complaints,String> complaintDescriptionColumn;
    public TableColumn<Complaints, LocalDate> compalintDateColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureColumns();
    }

    private void configureColumns(){
        tenantNameColumn.setCellValueFactory(data -> data.getValue().tenantNameProperty());
        unitNumberColumn.setCellValueFactory(data -> data.getValue().unitNumberProperty());
        complaintTypeColumn.setCellValueFactory(data -> data.getValue().complaintTypeProperty());
        complaintDescriptionColumn.setCellValueFactory(data -> data.getValue().complaintDescriptionProperty());
        compalintDateColumn.setCellValueFactory(data -> data.getValue().dateComplaintFiledProperty());


        // Load data
        ObservableList<Complaints> complaintsList = Model.getInstance().getComplaints();
        complaintsTable.setItems(complaintsList);
    }
}
