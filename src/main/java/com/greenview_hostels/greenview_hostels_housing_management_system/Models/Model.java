package com.greenview_hostels.greenview_hostels_housing_management_system.Models;

import com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant.IssueNoticeController;
import com.greenview_hostels.greenview_hostels_housing_management_system.Views.Viewsfactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Model {
    private static Model model;
    private static Viewsfactory viewsfactory;
    private final DatabaseConnection databaseConnection;
    public List<String> availableProperties;
    //public ObservableList<Tenant> showExistingTenants = FXCollections.observableArrayList();

    //Client variables
    public Tenant tenant;
    public String tenantID;
    public String tenantName;
    public String fname;
    public String lname;
    public String phoneNo;
    public String emailAddress;
    public LocalDateTime dateMovedIn;

    private boolean tenantLoginSuccessFlag;


    //Admin variables
    private Model() {
        this.viewsfactory = new Viewsfactory();
        this.databaseConnection = new DatabaseConnection();

        //Client section
        this.tenantLoginSuccessFlag=false;
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

    //Tenant methods
    public boolean getTenantLoginSuccessFlag(){
        return this.tenantLoginSuccessFlag;
    }

    public void setTenantLoginSuccessFlag(boolean flag){
        this.tenantLoginSuccessFlag=flag;
    }

    public Tenant getTenant(){
        System.out.println("Tenant gotten from model is:"+tenant);
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        System.out.println("Tenant fetched from model is:"+tenant);
        this.tenant = tenant;
    }

    public void evaluateClientCredentials(String Email_address, String Password){
        ResultSet resultSet= databaseConnection.getClientData(Email_address);
        try {
            if (resultSet.isBeforeFirst()){
                resultSet.next();
                System.out.println("Fetched data for:"+" "+Email_address);
                String password=resultSet.getString("Password");
                boolean passwordsMatch=Password.equals(password);
                if (passwordsMatch){
                    Tenant loggedInTenant=new Tenant(tenantID,tenantName,phoneNo,emailAddress,dateMovedIn);
                    loggedInTenant.tenantIDProperty().set(resultSet.getString("Tenant_ID"));
                    loggedInTenant.tenantNameProperty().set(resultSet.getString("Tenant_name"));
                    //loggedInTenant.lnameProperty().set(resultSet.getString("Last_name"));
                    loggedInTenant.phoneNoProperty().set(resultSet.getString("Phone_number"));
                    loggedInTenant.emailAddressProperty().set(resultSet.getString("Email_address"));
                    tenantLoginSuccessFlag=true;
                    Model.getInstance().setTenant(loggedInTenant);

                    /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Tenant/IssueNotice.fxml"));
                    Parent root = loader.load(); // Load the FXML file
                    IssueNoticeController noticeController = loader.getController(); // Get existing controller
                    noticeController.setTenant(loggedInTenant); // Pass tenant data to the controller*/

                    Model.getInstance().getViewsfactory().showFileNotice(loggedInTenant);

                    System.out.println("Logged in tenant is:"+loggedInTenant);
                }else {
                    tenantLoginSuccessFlag=false;
                    System.out.println("Incorrect password for:"+Email_address);
                }
                }else {
                tenantLoginSuccessFlag=false;
                System.out.println("Invalid email address");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*public void populateHouseNumber(Tenant tenant, Label houseNoLabel, ComboBox<String> houseNoComboBox) {
        ObservableList<Tenant> allTenants=model.showExistingTenantDetails();
        ObservableList<Property> properties = tenant.getProperties();

        if (properties.size() == 1) {
            houseNoLabel.setVisible(true);
            houseNoLabel.setText(properties.get(0).unitNumberProperty().get());
            houseNoComboBox.setVisible(false);
        } else if (properties.size() > 1) {
            ObservableList<String> unitNumbers = FXCollections.observableArrayList();
            for (Property property : properties) {
                unitNumbers.add(property.unitNumberProperty().get());
            }
            houseNoComboBox.setVisible(true);
            houseNoComboBox.setItems(unitNumbers);
            houseNoLabel.setVisible(false);
        } else {
            houseNoLabel.setVisible(true);
            houseNoLabel.setText("No house found");
            houseNoComboBox.setVisible(false);
        }
    }*/

    public void populateHouseNumber(Label houseNoLabel, ComboBox<String> houseNoComboBox) {
        ObservableList<Tenant> allTenants = showExistingTenantDetails();
        Tenant loggedInTenant = getTenant();

        // Find the logged-in tenant
        Tenant matchingTenant = allTenants.stream()
                .filter(tenant -> tenant.tenantIDProperty().get().equals(loggedInTenant.tenantIDProperty().get()))
                .findFirst()
                .orElse(null);

        if (matchingTenant != null) {
            ObservableList<Property> properties = matchingTenant.getProperties();

            // Ensure UI updates are done on the JavaFX Application Thread
            Platform.runLater(() -> {
                if (properties.size() == 1) {
                    houseNoLabel.setVisible(true);
                    houseNoLabel.setText(properties.get(0).unitNumberProperty().get());
                    houseNoComboBox.setVisible(false);
                } else if (properties.size() > 1) {
                    ObservableList<String> unitNumbers = FXCollections.observableArrayList();
                    for (Property property : properties) {
                        unitNumbers.add(property.unitNumberProperty().get());
                    }
                    houseNoComboBox.setVisible(true);
                    houseNoComboBox.setItems(unitNumbers);
                    houseNoLabel.setVisible(false);
                } else {
                    houseNoLabel.setVisible(true);
                    houseNoLabel.setText("No house found");
                    houseNoComboBox.setVisible(false);
                }
            });
        }
    }

    //Admin methods
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

    /*public ObservableList<Tenant> showExistingTenantDetails() {
        ObservableList<Tenant> showExistingTenants=FXCollections.observableArrayList();
        ResultSet resultSet = databaseConnection.showExistingTenantDetails();
        try {
            while (resultSet.next()) {
                System.out.println("Retrieved tenant:"+resultSet.getString("First_name"));
                showExistingTenants.add(new Tenant(
                        resultSet.getString("Tenant_ID"),
                        resultSet.getString("First_name"),
                        resultSet.getString("Last_name"),
                        resultSet.getString("Phone_number"),
                        resultSet.getString("Email_address")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return showExistingTenants;
    }*/

    /*public ObservableList<Tenant> showExistingTenantDetails() {
        ObservableList<Tenant> tenantDetails = FXCollections.observableArrayList();
        ResultSet resultSet = databaseConnection.showExistingTenantDetails();

        try {
            while (resultSet.next()) {
                String tenantID = resultSet.getString("Tenant_ID");

                // Check if tenant already exists in the list
                Tenant existingTenant = tenantDetails.stream()
                        .filter(tenant1 -> tenant1.tenantIDProperty().get().equals(tenantID))
                        .findFirst()
                        .orElse(null);

                // Create a new tenant if not found
                if (existingTenant == null) {
                    existingTenant = new Tenant(
                            tenantID,
                            resultSet.getString("First_name"),
                            resultSet.getString("Last_name"),
                            resultSet.getString("Phone_number"),
                            resultSet.getString("Email_address")
                    );
                    tenantDetails.add(existingTenant);
                }

                // Create and add the tenant's property
                Property property = new Property(
                        resultSet.getString("Unit_number"),
                        resultSet.getString("Unit_type")
                );
                existingTenant.addProperty(property);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Prints the error instead of crashing
        } finally {
            try {
                if (resultSet != null) resultSet.close(); // Ensure ResultSet is closed
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return tenantDetails;
    }*/

    public ObservableList<Property> showExistingProperties(){
        ObservableList<Property> properties=FXCollections.observableArrayList();
        ResultSet resultSet= databaseConnection.showExistingProperties();
        try {
            while (resultSet.next()){
                properties.add(new Property(
                        resultSet.getString("Unit_number"),
                        resultSet.getString("Unit_type")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    //Utility methods(used by both tenant and admin)
    public ObservableList<Tenant> showExistingTenantDetails() {
        ObservableList<Tenant> tenantDetails = FXCollections.observableArrayList();
        ResultSet resultSet = databaseConnection.showExistingTenantDetails();

        try {
            while (resultSet.next()) {
                String tenantID = resultSet.getString("Tenant_ID");
                String tenantName = resultSet.getString("Tenant_name");
                //String lastName = resultSet.getString("Last_name");
                String phoneNumber = resultSet.getString("Phone_number");
                String email = resultSet.getString("Email_address");
                String unitNumber = resultSet.getString("Unit_number");
                String dateMovedInString = resultSet.getString("Date_occupied");

                //Converting dateMovedIn to LocalDateTime
                LocalDateTime dateMovedIn=null;
                if(dateMovedInString!=null){
                    dateMovedIn=LocalDateTime.parse(dateMovedInString,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                }

                System.out.println("Tenant: " + tenantID + " | " + tenantName + " | " + phoneNumber + " | " + unitNumber + " | " + dateMovedIn);

                Tenant existingTenant = tenantDetails.stream()
                        .filter(tenant1 -> tenant1.tenantIDProperty().get().equals(tenantID))
                        .findFirst()
                        .orElse(null);

                if (existingTenant == null) {
                    existingTenant = new Tenant(tenantID, tenantName, phoneNumber, email,dateMovedIn);
                    tenantDetails.add(existingTenant);
                }

                Property property = new Property(unitNumber, ""); // Assuming unitType is not needed
                existingTenant.addProperty(property);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return tenantDetails;
    }

}
