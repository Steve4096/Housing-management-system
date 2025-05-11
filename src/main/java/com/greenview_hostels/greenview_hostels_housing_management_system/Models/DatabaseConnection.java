package com.greenview_hostels.greenview_hostels_housing_management_system.Models;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseConnection {
private Connection conn;

public DatabaseConnection(){
    try {
        this.conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/greenview_centa","root","");
        if(this.conn!=null){
            System.out.println("Connection to the database is successful");
        }
    }catch (Exception e){
        e.printStackTrace();
        System.out.println("Failed to establish connection");
    }
}

    //Tenant section
    public ResultSet getClientData(String Email_address){
    PreparedStatement preparedStatement;
    ResultSet resultSet=null;
    try {
        String clientData="SELECT * FROM tenants WHERE Email_address=?";
        preparedStatement=this.conn.prepareStatement(clientData);
        preparedStatement.setString(1,Email_address);
        //preparedStatement.setString(2,Password);
        resultSet= preparedStatement.executeQuery();

        //String correspondingHouseNumber="SELECT t.Tenant_ID,o.Tenant_ID,o.Property_ID,o.Date_vacated,p.Property_ID,p.Unit_number from tenants t JOIN occupancy o on t.Tenant_ID=o.Tenant_ID"
    }catch (Exception e){
        e.printStackTrace();
    }
    return resultSet;
    }

    public void fileNotice(Integer IDNo,int Unit_number,Date selectedDate){
    try {
        /*//Fetch tenant ID based on tenant name
        ResultSet resultSet1;
        PreparedStatement preparedStatement3;
        String getTenantID="SELECT Tenant_ID FROM tenants where Tenant_name=?";
        preparedStatement3=this.conn.prepareStatement(getTenantID);
        preparedStatement3.setString(1,Tenant_name);
        resultSet1=preparedStatement3.executeQuery();
        String Tenant_ID=null;
        if(resultSet1.next()){
            Tenant_ID=resultSet1.getString("Tenant_ID");
            System.out.println("Tenant_ID gotten"+Tenant_ID);
        }*/



        //Fetch property ID based on unit number
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String getPropertyID="SELECT Property_ID FROM properties WHERE Unit_number=?";
        preparedStatement=this.conn.prepareStatement(getPropertyID);
        preparedStatement.setInt(1,Unit_number);
        resultSet=preparedStatement.executeQuery();
        int propertyID=0;
        if(resultSet.next()){
            propertyID=resultSet.getInt("Property_ID");
            System.out.println("Property_ID gotten"+propertyID);
        }



        //Insert into notices table
        PreparedStatement preparedStatement1;
        String fileNotice="INSERT INTO notices(Tenant_ID,Property_ID,Date_intend_to_leave,Date_notice_issued) values(?,?,?,NOW())";
        preparedStatement1=this.conn.prepareStatement(fileNotice);
        preparedStatement1.setInt(1,IDNo);
        preparedStatement1.setInt(2,propertyID);
        preparedStatement1.setDate(3,selectedDate);
        //preparedStatement.setDate(4,currentDate);
        preparedStatement1.executeUpdate();

        //Update corresponding result in tenants table
        String updateTenantTable="UPDATE tenants SET status='VACATING' WHERE Tenant_ID=?";
        PreparedStatement preparedStatement2=this.conn.prepareStatement(updateTenantTable);
        preparedStatement2.setInt(1,IDNo);
        preparedStatement2.executeUpdate();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    }

    public void fileComplaint(Integer IDNo,int Unit_number,String complaintType,String complaintDescription){
    try {
        //Fetch property ID based on unit number
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        String getpropertyID="SELECT Property_ID FROM properties WHERE Unit_number=?";
        preparedStatement=this.conn.prepareStatement(getpropertyID);
        preparedStatement.setInt(1,Unit_number);
        resultSet=preparedStatement.executeQuery();
        int PropertyID=0;
        if(resultSet.isBeforeFirst()){
            resultSet.next();
            PropertyID=resultSet.getInt("Property_ID");
            System.out.println("Property_ID gotten"+PropertyID);
        }

        //Insert into complaints table
        PreparedStatement preparedStatement1;
        String fileComplaint="INSERT INTO complaints(Tenant_ID,Property_ID,Complaint_type,Complaint_description,Date_complaint_filed) values(?,?,?,?,NOW())";
        preparedStatement1=this.conn.prepareStatement(fileComplaint);
        preparedStatement1.setInt(1,IDNo);
        preparedStatement1.setInt(2,PropertyID);
        preparedStatement1.setString(3,complaintType);
        preparedStatement1.setString(4,complaintDescription);
        preparedStatement1.executeUpdate();
    }catch (Exception e){
        e.printStackTrace();
    }
    }



    //Admin section
public ResultSet getAdminData(String username,String password){
    PreparedStatement preparedStatement;
    ResultSet resultSet=null;
    try {
        String admin="SELECT * FROM admin WHERE username=? AND password=?";
        preparedStatement=this.conn.prepareStatement(admin);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        resultSet=preparedStatement.executeQuery();
    }catch (Exception e){
        e.printStackTrace();
    }
return resultSet;
}

public void registerNewTenant(String Tenant_ID,String name,String Phone_no,String Email_address,String Unit_number){
    PreparedStatement preparedStatement;

    //Insert into tenants table
    try {
        String RegisterTenant="INSERT INTO tenants(Tenant_ID,Tenant_name,Phone_number,Email_address,Status,Password) values(?,?,?,?,'Active','Greenview2025')";
        preparedStatement=this.conn.prepareStatement(RegisterTenant);
        preparedStatement.setString(1,Tenant_ID);
        preparedStatement.setString(2,name);
        //preparedStatement.setString(3,Lname);
        preparedStatement.setString(3,Phone_no);
        preparedStatement.setString(4,Email_address);
        preparedStatement.executeUpdate();

        //Fetch corresponding property_ID based on house number selected
        String getPropertyID="SELECT Property_ID FROM properties WHERE Unit_number=?";
        PreparedStatement preparedStatement1;
        ResultSet resultSet;
        preparedStatement1=this.conn.prepareStatement(getPropertyID);
        preparedStatement1.setString(1,Unit_number);
        resultSet= preparedStatement1.executeQuery();
        int propertyID=0;
        if(resultSet.next()){
            propertyID=resultSet.getInt("Property_ID");
            System.out.println("Property_ID gotten"+propertyID);
        }

        //After getting the property_ID based on the unit_number entered,store it in the occupancy table along with the other relevant details
        PreparedStatement occupancy;
        String addTenantToOccupancyTable="INSERT INTO occupancy(Property_ID,Tenant_ID,Date_occupied,Date_vacated) values(?,?,NOW(),NULL)";
        occupancy=this.conn.prepareStatement(addTenantToOccupancyTable);
        occupancy.setInt(1,propertyID);
        occupancy.setString(2,Tenant_ID);
        occupancy.executeUpdate();
    }catch (Exception e){
        e.printStackTrace();
    }
}

public ResultSet showExistingTenantDetails(){
    PreparedStatement preparedStatement;
    ResultSet resultSet=null;
    try {
        String tenantDetails="SELECT t.Tenant_ID,t.Tenant_name,t.Phone_number,t.Email_address,p.Property_ID,p.Unit_number,p.Unit_type,o.Date_occupied from tenants t JOIN occupancy o ON t.Tenant_ID=o.Tenant_ID JOIN properties p on o.Property_ID=p.Property_ID";
        preparedStatement=this.conn.prepareStatement(tenantDetails);
        resultSet= preparedStatement.executeQuery();
    }catch (Exception e){
        e.printStackTrace();
    }
    return resultSet;
}

public void Addproperty(String unitNumber, String unitType, BigDecimal Rent_amount){
    PreparedStatement preparedStatement;
    try {
        String addProperty="INSERT INTO properties(Unit_number,Unit_type,Rent_amount)values (?,?,?)";
        preparedStatement=this.conn.prepareStatement(addProperty);
        preparedStatement.setString(1,unitNumber);
        preparedStatement.setString(2,unitType);
        preparedStatement.setBigDecimal(3,Rent_amount);
        preparedStatement.executeUpdate();
    }catch (Exception e){
        e.printStackTrace();
    }
}

public ResultSet ReturnUnoccuppiedhouses(){
    PreparedStatement preparedStatement;
    ResultSet resultSet=null;
    try {
        String vacantHouses="SELECT p.Property_ID,p.Unit_number FROM properties p LEFT JOIN occupancy o on p.Property_ID=o.Property_ID WHERE o.Property_ID IS null OR o.Date_vacated IS not null";
        preparedStatement=this.conn.prepareStatement(vacantHouses);
        resultSet=preparedStatement.executeQuery();
    }catch (Exception e){
        e.printStackTrace();
    }
    return resultSet;
}

public ResultSet showExistingProperties(){
    PreparedStatement preparedStatement;
    ResultSet resultSet=null;
    try {
        String existingProperties="SELECT Unit_number,Unit_type,Rent_amount FROM properties";
        preparedStatement=this.conn.prepareStatement(existingProperties);
        resultSet=preparedStatement.executeQuery();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    return resultSet;
}

public void updateWaterTankTable(int level,int volume, String lastRefilled){
    PreparedStatement preparedStatement;
    try {
        String updateWaterTank="INSERT INTO water_tank(Tank_ID,Water_level,Water_volume,Last_refilled) values(1,?,?,?)";
        preparedStatement=this.conn.prepareStatement(updateWaterTank);
        preparedStatement.setInt(1,level);
        preparedStatement.setInt(2,volume);
        if (lastRefilled!=null){
            //Convert time to timestamp
            LocalDateTime dateTime=LocalDateTime.parse(lastRefilled, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            preparedStatement.setTimestamp(3,Timestamp.valueOf(dateTime));
        }else {
            preparedStatement.setNull(3, Types.TIMESTAMP);
        }
        preparedStatement.executeUpdate();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
}
