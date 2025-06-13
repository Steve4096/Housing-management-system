package com.greenview_hostels.greenview_hostels_housing_management_system.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Complaints {

    private final StringProperty tenantName;
    private final StringProperty unitNumber;
    private final StringProperty complaintType;
    private final StringProperty complaintDescription;
    private final ObjectProperty<LocalDate> dateComplaintFiled;

    public Complaints(String tenantName,String unitNumber,String complaintType,String complaintDescription,LocalDate dateComplaintFiled){
        this.tenantName=new SimpleStringProperty(tenantName);
        this.unitNumber=new SimpleStringProperty(unitNumber);
        this.complaintType=new SimpleStringProperty(complaintType);
        this.complaintDescription=new SimpleStringProperty(complaintDescription);
        this.dateComplaintFiled=new SimpleObjectProperty<>(dateComplaintFiled);
    }

    public StringProperty tenantNameProperty() { return tenantName; }
    public StringProperty unitNumberProperty() { return unitNumber; }
    public StringProperty complaintTypeProperty() { return complaintType; }
    public StringProperty complaintDescriptionProperty() { return complaintDescription; }
    public ObjectProperty<LocalDate> dateComplaintFiledProperty() { return dateComplaintFiled; }
}
