package com.greenview_hostels.greenview_hostels_housing_management_system.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class Property {
    private final StringProperty unitNumber;
    private final StringProperty unitType;
    //private final ObjectProperty<LocalDateTime> dateMovedIn;

    public Property(String unitNumber,String unitType){
        this.unitNumber=new SimpleStringProperty(unitNumber);
        this.unitType=new SimpleStringProperty(unitType);
        //this.dateMovedIn=new SimpleObjectProperty<>(dateMovedIn);
    }

    public StringProperty unitNumberProperty() {
        return unitNumber;
    }

    public StringProperty unitTypeProperty(){
        return unitType;
    }

    /*public ObjectProperty<LocalDateTime> dateMovedInProperty(){
        return dateMovedIn;
    }*/

}
