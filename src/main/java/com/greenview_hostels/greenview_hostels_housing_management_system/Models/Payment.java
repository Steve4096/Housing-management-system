package com.greenview_hostels.greenview_hostels_housing_management_system.Models;

import javafx.beans.property.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {
    private final StringProperty tenantName;
    private final StringProperty unitNumber;
    //private final StringProperty receiptNumber;
    private final ObjectProperty<BigDecimal> amount;
    private final StringProperty paymentType;
    private final ObjectProperty<LocalDate> rentMonth;
    private final ObjectProperty<LocalDate> dateOfTransaction;

    public Payment(String tenantName, String unitNumber,BigDecimal amount,
                   String paymentType, LocalDate rentMonth, LocalDate dateIssued) {
        this.tenantName = new SimpleStringProperty(tenantName);
        this.unitNumber = new SimpleStringProperty(unitNumber);
        //this.receiptNumber = new SimpleStringProperty(receiptNumber);
        this.amount = new SimpleObjectProperty<>(amount);
        this.paymentType = new SimpleStringProperty(paymentType);
        this.rentMonth = new SimpleObjectProperty<>(rentMonth);
        this.dateOfTransaction = new SimpleObjectProperty<>(dateIssued);
    }

    public StringProperty tenantNameProperty() { return tenantName; }
    public StringProperty unitNumberProperty() { return unitNumber; }
    //public StringProperty receiptNumberProperty() { return receiptNumber; }
    public ObjectProperty<BigDecimal> amountProperty() { return amount; }
    public StringProperty paymentTypeProperty() { return paymentType; }
    public ObjectProperty<LocalDate> rentMonthProperty() { return rentMonth; }
    public ObjectProperty<LocalDate> dateIssuedProperty() { return dateOfTransaction; }
}
