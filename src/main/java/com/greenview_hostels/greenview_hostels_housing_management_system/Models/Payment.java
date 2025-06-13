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
                   String paymentType, LocalDate rentMonth, LocalDate dateOfTransaction) {
        this.tenantName = new SimpleStringProperty(tenantName);
        this.unitNumber = new SimpleStringProperty(unitNumber);
        this.amount = new SimpleObjectProperty<>(amount);
        this.paymentType = new SimpleStringProperty(paymentType);
        this.rentMonth = new SimpleObjectProperty<>(rentMonth);
        this.dateOfTransaction = new SimpleObjectProperty<>(dateOfTransaction);
    }

    public StringProperty tenantNameProperty() { return tenantName; }
    public StringProperty unitNumberProperty() { return unitNumber; }
    public ObjectProperty<BigDecimal> amountProperty() { return amount; }
    public StringProperty paymentTypeProperty() { return paymentType; }
    public ObjectProperty<LocalDate> rentMonthProperty() { return rentMonth; }
    public ObjectProperty<LocalDate> dateIssuedProperty() { return dateOfTransaction; }

    // Standard getters
    public String getTenantName() { return tenantName.get(); }
    public String getUnitNumber() { return unitNumber.get(); }
    public BigDecimal getAmount() { return amount.get(); }
    public String getPaymentType() { return paymentType.get(); }
    public LocalDate getRentMonth() { return rentMonth.get(); }
    public LocalDate getDateIssued() { return dateOfTransaction.get(); }

    // Standard setters
    public void setTenantName(String name) { tenantName.set(name); }
    public void setUnitNumber(String unit) { unitNumber.set(unit); }
    public void setAmount(BigDecimal amt) { amount.set(amt); }
    public void setPaymentType(String type) { paymentType.set(type); }
    public void setRentMonth(LocalDate month) { rentMonth.set(month); }
    public void setDateIssued(LocalDate date) { dateOfTransaction.set(date); }

}
