package com.greenview_hostels.greenview_hostels_housing_management_system.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class Tenant {
    private final StringProperty tenantID;
    private final StringProperty tenantName;
    //private final StringProperty lname;
    private final StringProperty phoneNo;
    private final StringProperty emailAddress;

    private final ObservableList<Property> properties;
    private final ObjectProperty<LocalDateTime> dateMovedIn;

    public Tenant(String tenantID,String tenantName,String phoneNo,String emailAddress,LocalDateTime dateMovedIn){
        this.tenantID=new SimpleStringProperty(tenantID);
        this.tenantName=new SimpleStringProperty(tenantName);
        //this.lname=new SimpleStringProperty(lname);
        this.phoneNo=new SimpleStringProperty(phoneNo);
        this.emailAddress=new SimpleStringProperty(emailAddress);
        this.properties= FXCollections.observableArrayList();
        this.dateMovedIn=new SimpleObjectProperty<>(dateMovedIn);
    }

    public void addProperty(Property property){
        this.properties.add(property);
    }

    public ObservableList<Property> getProperties(){
        return properties;
    }

    public ObjectProperty<LocalDateTime> dateMovedInProperty(){
        return dateMovedIn;
    }

    public void setDateMovedIn(LocalDateTime dateMovedIn){
        this.dateMovedIn.set(dateMovedIn);
    }
    /*public ObservableList<Property> unitNumberProperty(){
        return unitNumberProperty();
    }*/

    public StringProperty unitNumberProperty(){
        if(!properties.isEmpty()){
            return properties.get(0).unitNumberProperty();
        }
        return new SimpleStringProperty("");
    }

    public ObservableList<Property> unitTypeProperty(){
        return unitTypeProperty();
    }

    /*//Getter methods
    public String getTenantID(){
        return tenantID.get();
    }

    public String getFname(){
        return fname.get();
    }

    public String getLname(){
        return lname.get();
    }

    public String getPhoneNo(){
        return phoneNo.get();
    }*/

    public StringProperty tenantIDProperty(){
        return tenantID;
    }

    public StringProperty tenantNameProperty(){
        return tenantName;
    }

    /*public StringProperty lnameProperty(){
        return lname;
    }*/

    public StringProperty phoneNoProperty(){
        return phoneNo;
    }

    public StringProperty emailAddressProperty(){
        return emailAddress;
    }
}
