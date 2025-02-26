package com.greenview_hostels.greenview_hostels_housing_management_system.Models;

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
}
