package com.greenview_hostels.greenview_hostels_housing_management_system.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tenant {
    private final StringProperty tenantID;
    private final StringProperty fname;
    private final StringProperty lname;
    private final StringProperty phoneNo;
    private final StringProperty emailAddress;

    public Tenant(String tenantID,String fname,String lname,String phoneNo,String emailAddress){
        this.tenantID=new SimpleStringProperty(tenantID);
        this.fname=new SimpleStringProperty(fname);
        this.lname=new SimpleStringProperty(lname);
        this.phoneNo=new SimpleStringProperty(phoneNo);
        this.emailAddress=new SimpleStringProperty(emailAddress);
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

    public StringProperty fnameProperty(){
        return fname;
    }

    public StringProperty lnameProperty(){
        return lname;
    }

    public StringProperty phoneNoProperty(){
        return phoneNo;
    }

    public StringProperty emailAddressProperty(){
        return emailAddress;
    }
}
