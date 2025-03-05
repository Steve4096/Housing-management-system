package com.greenview_hostels.greenview_hostels_housing_management_system.Models;

import com.greenview_hostels.greenview_hostels_housing_management_system.Views.Viewsfactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.util.*;

public class Model {
    private static Model model;
    private static Viewsfactory viewsfactory;
    private final DatabaseConnection databaseConnection;
    public List<String> availableProperties;
    //public ObservableList<Tenant> showExistingTenants = FXCollections.observableArrayList();

    private Model() {
        this.viewsfactory = new Viewsfactory();
        this.databaseConnection = new DatabaseConnection();
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public Viewsfactory getViewsfactory() {
        return viewsfactory;
    }

    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    public List<String> showAvailableProperties() {
        ResultSet resultSet = databaseConnection.ReturnUnoccuppiedhouses();
        availableProperties = new ArrayList<>();
        try {
            while (resultSet.next()) {
                availableProperties.add(resultSet.getString("Unit_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return availableProperties;
    }

    public ObservableList<Tenant> showExistingTenantDetails() {
        ObservableList<Tenant> showExistingTenants=FXCollections.observableArrayList();
        ResultSet resultSet = databaseConnection.showExistingTenantDetails();
        try {
            while (resultSet.next()) {
                System.out.println("Retrieved tenant:"+resultSet.getString("First_name"));
                showExistingTenants.add(new Tenant(
                        resultSet.getString("Tenant_ID"),
                        resultSet.getString("First_name"),
                        resultSet.getString("Last_name"),
                        resultSet.getString("Phone_number")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return showExistingTenants;
    }
}
