//package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin;
//
//import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Complaints;
//import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
//import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Notices;
//import javafx.collections.ObservableList;
//import javafx.fxml.Initializable;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//
//import java.net.URL;
//import java.time.LocalDate;
//import java.util.ResourceBundle;
//
//public class ComplaintsController implements Initializable {
//    public ComboBox dateFiledCombobox;
//    public ComboBox complaintTypeCombobox;
//    public TableView complaintsTable;
//    public TableColumn<Complaints,String> tenantNameColumn;
//    public TableColumn<Complaints,String> unitNumberColumn;
//    public TableColumn<Complaints,String> complaintTypeColumn;
//    public TableColumn<Complaints,String> complaintDescriptionColumn;
//    public TableColumn<Complaints, LocalDate> compalintDateColumn;
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        configureColumns();
//    }
//
//    private void configureColumns(){
//        tenantNameColumn.setCellValueFactory(data -> data.getValue().tenantNameProperty());
//        unitNumberColumn.setCellValueFactory(data -> data.getValue().unitNumberProperty());
//        complaintTypeColumn.setCellValueFactory(data -> data.getValue().complaintTypeProperty());
//        complaintDescriptionColumn.setCellValueFactory(data -> data.getValue().complaintDescriptionProperty());
//        compalintDateColumn.setCellValueFactory(data -> data.getValue().dateComplaintFiledProperty());
//
//
//        // Load data
//        ObservableList<Complaints> complaintsList = Model.getInstance().getComplaints();
//        complaintsTable.setItems(complaintsList);
//    }
//}

package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Complaints;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ComplaintsController implements Initializable {
    public ComboBox<String> dateFiledCombobox;
    public ComboBox<String> complaintTypeCombobox;
    public TableView<Complaints> complaintsTable;
    public TableColumn<Complaints, String> tenantNameColumn;
    public TableColumn<Complaints, String> unitNumberColumn;
    public TableColumn<Complaints, String> complaintTypeColumn;
    public TableColumn<Complaints, String> complaintDescriptionColumn;
    public TableColumn<Complaints, LocalDate> compalintDateColumn;

    // Original data and filtered list
    private ObservableList<Complaints> originalComplaintsList;
    private FilteredList<Complaints> filteredComplaintsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureColumns();
        setupComboBoxes();
        setupFilters();
    }

    private void configureColumns() {
        tenantNameColumn.setCellValueFactory(data -> data.getValue().tenantNameProperty());
        unitNumberColumn.setCellValueFactory(data -> data.getValue().unitNumberProperty());
        complaintTypeColumn.setCellValueFactory(data -> data.getValue().complaintTypeProperty());
        complaintDescriptionColumn.setCellValueFactory(data -> data.getValue().complaintDescriptionProperty());
        compalintDateColumn.setCellValueFactory(data -> data.getValue().dateComplaintFiledProperty());

        // Load original data
        originalComplaintsList = Model.getInstance().getComplaints();

        // Create filtered list
        filteredComplaintsList = new FilteredList<>(originalComplaintsList, complaint -> true);

        // Set filtered list to table
        complaintsTable.setItems(filteredComplaintsList);
    }

    private void setupComboBoxes() {
        // Setup complaint type combo box
        ObservableList<String> complaintTypes = FXCollections.observableArrayList(
                "All Types", "Administrative", "Maintenance", "Other"
        );
        complaintTypeCombobox.setItems(complaintTypes);
        complaintTypeCombobox.setValue("All Types");

        // Setup date filed combo box with common date ranges
        ObservableList<String> dateRanges = FXCollections.observableArrayList(
                "All Dates", "Today", "This Week", "This Month", "Last 30 Days", "Last 7 Days"
        );
        dateFiledCombobox.setItems(dateRanges);
        dateFiledCombobox.setValue("All Dates");
    }

    private void setupFilters() {
        // Add listeners to combo boxes to trigger filtering
        complaintTypeCombobox.setOnAction(event -> applyFilters());
        dateFiledCombobox.setOnAction(event -> applyFilters());
    }

    private void applyFilters() {
        filteredComplaintsList.setPredicate(complaint -> {
            // Get selected filter values
            String selectedComplaintType = complaintTypeCombobox.getValue();
            String selectedDateRange = dateFiledCombobox.getValue();

            // Check complaint type filter
            boolean matchesComplaintType = isComplaintTypeMatch(complaint, selectedComplaintType);

            // Check date filter
            boolean matchesDateRange = isDateRangeMatch(complaint, selectedDateRange);

            // Return true only if both filters match (AND logic)
            return matchesComplaintType && matchesDateRange;
        });
    }

    private boolean isComplaintTypeMatch(Complaints complaint, String selectedType) {
        if (selectedType == null || selectedType.equals("All Types")) {
            return true;
        }

        String complaintType = complaint.complaintTypeProperty().get();
        if (complaintType == null) {
            return false;
        }

        // Case-insensitive comparison
        return complaintType.equalsIgnoreCase(selectedType);
    }

    private boolean isDateRangeMatch(Complaints complaint, String selectedRange) {
        if (selectedRange == null || selectedRange.equals("All Dates")) {
            return true;
        }

        LocalDate complaintDate = complaint.dateComplaintFiledProperty().get();
        if (complaintDate == null) {
            return false;
        }

        LocalDate today = LocalDate.now();

        switch (selectedRange) {
            case "Today":
                return complaintDate.equals(today);

            case "This Week":
                LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
                return complaintDate.isAfter(startOfWeek.minusDays(1)) &&
                        complaintDate.isBefore(today.plusDays(1));

            case "This Month":
                return complaintDate.getMonth() == today.getMonth() &&
                        complaintDate.getYear() == today.getYear();

            case "Last 30 Days":
                return complaintDate.isAfter(today.minusDays(30)) &&
                        complaintDate.isBefore(today.plusDays(1));

            case "Last 7 Days":
                return complaintDate.isAfter(today.minusDays(7)) &&
                        complaintDate.isBefore(today.plusDays(1));

            default:
                return true;
        }
    }

    // Optional: Method to clear all filters
    public void clearFilters() {
        complaintTypeCombobox.setValue("All Types");
        dateFiledCombobox.setValue("All Dates");
        applyFilters();
    }

    // Optional: Method to get filtered results count
    public int getFilteredResultsCount() {
        return filteredComplaintsList.size();
    }
}