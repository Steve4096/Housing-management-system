package com.greenview_hostels.greenview_hostels_housing_management_system.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Notices {
    private final StringProperty tenantName;
    private final StringProperty unitNumber;
    private final ObjectProperty<LocalDate> dateNoticeIssued;
    private final ObjectProperty<LocalDate> dateIntendToLeave;

    public Notices(String tenantName,String unitNumber,LocalDate dateNoticeIssued,LocalDate dateIntendToLeave){
        this.tenantName=new SimpleStringProperty(tenantName);
        this.unitNumber=new SimpleStringProperty(unitNumber);
        this.dateNoticeIssued=new SimpleObjectProperty<>(dateNoticeIssued);
        this.dateIntendToLeave=new SimpleObjectProperty<>(dateIntendToLeave);
    }

    public StringProperty tenantNameProperty() { return tenantName; }
    public StringProperty unitNumberProperty() { return unitNumber; }
    public ObjectProperty<LocalDate> dateNoticeIssuedProperty() { return dateNoticeIssued; }
    public ObjectProperty<LocalDate> dateIntendToLeaveProperty() { return dateIntendToLeave; }

}
