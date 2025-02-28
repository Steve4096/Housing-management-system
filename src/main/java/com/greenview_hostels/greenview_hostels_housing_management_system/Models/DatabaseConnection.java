package com.greenview_hostels.greenview_hostels_housing_management_system.Models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseConnection {
private Connection conn;

public DatabaseConnection(){
    try {
        this.conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/greenview_housing_management","root","");
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
        String clientData="SELECT * FROM clients WHERE Email_address=?";
        preparedStatement=this.conn.prepareStatement(clientData);
        preparedStatement.setString(1,Email_address);
        resultSet= preparedStatement.executeQuery();
    }catch (Exception e){
        e.printStackTrace();
    }
    return resultSet;
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

/*public void registerNewTenant(String Tenant_ID,String Fname,String Lname,String Phone_no,String Email_address){
    PreparedStatement preparedStatement;
    try {
        String RegisterTenant="INSERT INTO tenants(Tenant_ID,First_name,Last_name,Phone_number,Email_address,Status,Date_moved_in,Password) values(?,?,?,?,?,Active,NOW(),Greenview2025)";
        preparedStatement=this.conn.prepareStatement(RegisterTenant);
        preparedStatement.setString(1,Tenant_ID);
        preparedStatement.setString(2,Fname);
        preparedStatement.setString(3,Lname);
        preparedStatement.setString(4,Phone_no);
        preparedStatement.setString(5,Email_address);
        preparedStatement.executeUpdate();

        String addTenantToOccupancyTable="INSERT INTO occupancy("
    }catch (Exception e){
        e.printStackTrace();
    }
}*/

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
