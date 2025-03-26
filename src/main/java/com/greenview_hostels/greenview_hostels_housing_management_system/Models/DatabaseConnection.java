package com.greenview_hostels.greenview_hostels_housing_management_system.Models;

import java.math.BigDecimal;
import java.sql.*;

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
        String clientData="SELECT * FROM clients WHERE Email_address=? AND Password=?";
        preparedStatement=this.conn.prepareStatement(clientData);
        preparedStatement.setString(1,Email_address);
        //preparedStatement.setString(2,Password);
        resultSet= preparedStatement.executeQuery();
    }catch (Exception e){
        e.printStackTrace();
    }
    return resultSet;
    }

    public void fileNotice(String Tenant_ID,int Property_ID,Date currentDate,Date selectedDate){
    PreparedStatement preparedStatement;
    try {
        String fileNotice="INSERT INTO notices(Tenant_ID,Property_ID,Date_intend_to_leave,Date_notice_issued) values(?,?,?,NOW())";
        preparedStatement=this.conn.prepareStatement(fileNotice);
        preparedStatement.setString(1,Tenant_ID);
        preparedStatement.setInt(2,Property_ID);
        preparedStatement.setDate(3,selectedDate);
        //preparedStatement.setDate(4,currentDate);
        preparedStatement.executeUpdate();
    } catch (Exception e) {
        throw new RuntimeException(e);
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

public void registerNewTenant(String Tenant_ID,String Fname,String Lname,String Phone_no,String Email_address,String Property_ID){
    PreparedStatement preparedStatement;
    PreparedStatement occupancy;

    //Insert into tenants table
    try {
        String RegisterTenant="INSERT INTO tenants(Tenant_ID,First_name,Last_name,Phone_number,Email_address,Status,Password) values(?,?,?,?,?,'Active','Greenview2025')";
        preparedStatement=this.conn.prepareStatement(RegisterTenant);
        preparedStatement.setString(1,Tenant_ID);
        preparedStatement.setString(2,Fname);
        preparedStatement.setString(3,Lname);
        preparedStatement.setString(4,Phone_no);
        preparedStatement.setString(5,Email_address);
        preparedStatement.executeUpdate();

        //Fetch corresponding property_ID based on house number selected
        String getPropertyID="SELECT Property_ID FROM properties WHERE Unit_number=?";
        PreparedStatement preparedStatement1;
        ResultSet resultSet;
        preparedStatement1=this.conn.prepareStatement(getPropertyID);
        preparedStatement1.setString(1,Property_ID);
        resultSet= preparedStatement1.executeQuery();
        int propertyID=0;
        if(resultSet.next()){
            propertyID=resultSet.getInt("Property_ID");
            System.out.println("Property_ID gotten"+propertyID);
        }

        //After getting the property_ID based on the unit_number entered,store it in the occupancy table along with the other relevant details
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
        String tenantDetails="SELECT t.Tenant_ID,t.First_name,t.Last_name,t.Phone_number,p.Property_ID,p.Unit_number,o.Date_occupied from tenants t JOIN occupancy o ON t.Tenant_ID=o.Tenant_ID JOIN properties p on o.Property_ID=p.Property_ID";
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
}
