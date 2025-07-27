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
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Model {
    private static Model model;
    private static Viewsfactory viewsfactory;
    private final DatabaseConnection databaseConnection;
    public ObservableList<String> availableProperties=FXCollections.observableArrayList();
   // public List<String> availableProperties;
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

    public void evaluateClientCredentials(String Email_address, String password){
        ResultSet resultSet= databaseConnection.getClientData(Email_address);
        try {
            if (resultSet.isBeforeFirst()){
                resultSet.next();
                System.out.println("Fetched data for:"+" "+Email_address);
                String hashedPassword=resultSet.getString("Password");
                boolean passwordsMatch=BCrypt.checkpw(password,hashedPassword);
                if (passwordsMatch){
                    Tenant loggedInTenant=new Tenant(tenantID,tenantName,phoneNo,emailAddress,dateMovedIn);
                    loggedInTenant.tenantIDProperty().set(resultSet.getString("Tenant_ID"));
                    loggedInTenant.tenantNameProperty().set(resultSet.getString("Tenant_name"));
                    loggedInTenant.phoneNoProperty().set(resultSet.getString("Phone_number"));
                    loggedInTenant.emailAddressProperty().set(resultSet.getString("Email_address"));
                    tenantLoginSuccessFlag=true;
                    Model.getInstance().setTenant(loggedInTenant);


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

    public ObservableList<Receipt> receiptDetails(){
        ObservableList<Receipt> receipts=FXCollections.observableArrayList();
        Tenant tenant=Model.getInstance().getTenant();
        ResultSet resultSet=databaseConnection.printReceiptDetails(tenant);
        try {
            while (resultSet.next()){
                String tenantName=resultSet.getString("Tenant_name");
                String unitNumber=resultSet.getString("Unit_number");
                String receiptNumber=resultSet.getString("Receipt_number");
                BigDecimal amount=resultSet.getBigDecimal("Amount");
                String paymentType=resultSet.getString("Payment_type");
                LocalDate monthPaidFor=resultSet.getObject("Rent_month",LocalDate.class);
                LocalDate dateIssued=resultSet.getObject("Date_issued",LocalDate.class);

                Receipt receipt=new Receipt(tenantName,unitNumber,receiptNumber,amount,paymentType,monthPaidFor,dateIssued);
                receipts.add(receipt);
                System.out.println("Receipt fetched from database is:"+receipt);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return receipts;
    }

    public ObservableList<Payment> paymentDetails(){
        ObservableList<Payment> payments=FXCollections.observableArrayList();
        Tenant tenant1=getInstance().getTenant();
        ResultSet resultSet=databaseConnection.printPaymentDetails(tenant1);
        try {
            while (resultSet.next()){
                String tenantName=resultSet.getString("Tenant_name");
                String unitNumber=resultSet.getString("Unit_number");
                String paymentType=resultSet.getString("Payment_type");
                BigDecimal amount=resultSet.getBigDecimal("Amount");
                LocalDate rentMonth=resultSet.getObject("Rent_month", LocalDate.class);
                LocalDate datePaymentMade=resultSet.getObject("Payment_date", LocalDate.class);

                Payment payment=new Payment(tenantName,unitNumber,amount,paymentType,rentMonth,datePaymentMade);
                payments.add(payment);
                System.out.println("Payment retrieved is: "+payment);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return payments;
    }

//    //Admin methods
//    public List<String> showAvailableProperties() {
//        ResultSet resultSet = databaseConnection.ReturnUnoccuppiedhouses();
//        availableProperties = new ArrayList<>();
//        try {
//            while (resultSet.next()) {
//                availableProperties.add(resultSet.getString("Unit_number"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return availableProperties;
//    }

    public ObservableList<String> showAvailableProperties() {
        ResultSet resultSet = databaseConnection.ReturnUnoccuppiedhouses();
        availableProperties.clear();
        try {
            while (resultSet.next()) {
                availableProperties.add(resultSet.getString("Unit_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return availableProperties;
    }



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

    public ObservableList<Payment> get5MostRecentPayments(){
        ObservableList<Payment> payments=FXCollections.observableArrayList();
        ResultSet resultSet=databaseConnection.get5MostRecentTransactions();
        try {
            while (resultSet.next()){
                String tenantName=resultSet.getString("Tenant_name");
                String unitNumber=resultSet.getString("Unit_number");
                BigDecimal amount=resultSet.getBigDecimal("Amount");
                String paymentType=resultSet.getString("Payment_type");
                LocalDate date=resultSet.getObject("Payment_date",LocalDate.class);
                LocalDate rentMonth=resultSet.getObject("Rent_month",LocalDate.class);

                Payment payment=new Payment(tenantName,unitNumber,amount,paymentType,rentMonth,date);
                payments.add(payment);
                System.out.println("Payment fetched from database is:"+payment);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return payments;
    }


    public ObservableList<Payment> paymentsForAll(){
        ObservableList<Payment> payments=FXCollections.observableArrayList();
        ResultSet resultSet=databaseConnection.getPaymentsForAll();
        try {
            while (resultSet.next()){
                String tenantName=resultSet.getString("Tenant_name");
                String unitNumber=resultSet.getString("Unit_number");
                BigDecimal amount=resultSet.getBigDecimal("Amount");
                String paymentType=resultSet.getString("Payment_type");
                LocalDate date=resultSet.getObject("Payment_date",LocalDate.class);
                LocalDate rentMonth=resultSet.getObject("Rent_month",LocalDate.class);

                Payment payment=new Payment(tenantName,unitNumber,amount,paymentType,rentMonth,date);
                payments.add(payment);
                System.out.println("Payment fetched from database is:"+payment);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return payments;
    }

    public ObservableList<Complaints> getComplaints(){
        ObservableList<Complaints> complaints=FXCollections.observableArrayList();
        ResultSet resultSet= getDatabaseConnection().getComplaints();
        try {
            while (resultSet.next()){
                String tenantName=resultSet.getString("Tenant_name");
                String unitNumber=resultSet.getString("Unit_number");
                String complaintType=resultSet.getString("Complaint_type");
                String complaintDescription=resultSet.getString("Complaint_description");
                LocalDate dateComplaintFiled=resultSet.getObject("Date_complaint_filed", LocalDate.class);

                Complaints complaints1=new Complaints(tenantName,unitNumber,complaintType,complaintDescription,dateComplaintFiled);
                complaints.add(complaints1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return complaints;
    }

    public ObservableList<Notices> getNotices(){
        ObservableList<Notices> notices=FXCollections.observableArrayList();
        ResultSet resultSet= databaseConnection.getNotices();
        try {
            while (resultSet.next()){
                String tenantName=resultSet.getString("Tenant_name");
                String unitNumber=resultSet.getString("Unit_number");
                LocalDate dateNoticeIssued=resultSet.getObject("Date_notice_issued", LocalDate.class);
                LocalDate dateIntendToLeave=resultSet.getObject("Date_intend_to_leave", LocalDate.class);

                Notices notices1=new Notices(tenantName,unitNumber,dateNoticeIssued,dateIntendToLeave);
                notices.add(notices1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return notices;
    }

    //Utility methods(used by both tenant and admin)
    //hash the password
    public String hashPassword(String plainPassword){
        return BCrypt.hashpw(plainPassword,BCrypt.gensalt());
    }


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
