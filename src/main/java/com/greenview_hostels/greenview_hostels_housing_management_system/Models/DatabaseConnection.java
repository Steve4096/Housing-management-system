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
        //Fetching property ID
        int propertyID=getPropertyID(String.valueOf(Unit_number));


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
        int PropertyID=getPropertyID(String.valueOf(Unit_number));

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

    public void payDeposit(Integer IDNo,String unit_number,BigDecimal amount,String receiptNumber){
    String makeDeposit="INSERT INTO PAYMENTS(Tenant_ID,Property_ID,Payment_type,Amount,Payment_date) values(?,?,'DEPOSIT',?,NOW())";
    String makeReceipt ="INSERT INTO RECEIPTS(Payment_ID,Date_issued,Receipt_number,Issued_by)values(?,NOW(),?,'System')";
       try {
           int paymentIDGenerated=0;
           conn.setAutoCommit(false);
           //Fetch property ID
           int propertyID=getPropertyID(unit_number);
           //Insert into payments
           try(PreparedStatement paymentStatement=this.conn.prepareStatement(makeDeposit,Statement.RETURN_GENERATED_KEYS)){
               paymentStatement.setInt(1,IDNo);
               paymentStatement.setInt(2,propertyID);
               paymentStatement.setBigDecimal(3,amount);
               int affectedRows=paymentStatement.executeUpdate();
               if(affectedRows==0){
                   System.out.println("0 rows affected. Failed to pay deposit");
               }else {
                   System.out.println("Paystatement completed successfully");
               }

               try (ResultSet generatedKeys=paymentStatement.getGeneratedKeys()){
                   if (generatedKeys.next()){
                       paymentIDGenerated=generatedKeys.getInt(1);
                       System.out.println("Payment ID generated: "+paymentIDGenerated);
                   }else {
                       System.out.println("No payment ID generated");
                   }
               }
           }

           try(PreparedStatement storeReceipt=this.conn.prepareStatement(makeReceipt)){
               storeReceipt.setInt(1,paymentIDGenerated);
               storeReceipt.setString(2,receiptNumber);
               storeReceipt.executeUpdate();
               System.out.println("Receipt stored successfully");
           }
           this.conn.commit();
       } catch (Exception e) {
           try {
              this.conn.rollback();
           } catch (SQLException ex) {
               System.err.println("Error while rolling back");
               ex.printStackTrace();
               //throw new RuntimeException(ex);
           }
           System.err.println("Failed to pay deposit");
           e.printStackTrace();
       }finally {
               try {
                   this.conn.setAutoCommit(true);
               } catch (SQLException ex) {
                   throw new RuntimeException(ex);
           }
       }
    }

    public boolean checkDepositPayment(int tenantID){
    String checkDeposit="SELECT SUM(amount) FROM PAYMENTS WHERE Payment_type='DEPOSIT' AND Tenant_ID=?";
    try (PreparedStatement preparedStatement=this.conn.prepareStatement(checkDeposit)){
        preparedStatement.setInt(1,tenantID);
        ResultSet resultSet=preparedStatement.executeQuery();
        if(resultSet.next()){
            BigDecimal deposit=resultSet.getBigDecimal(1);
            return deposit!=null && deposit.compareTo(BigDecimal.valueOf(10000))==0;
        }
    }catch (SQLException e){
        e.printStackTrace();
    }
    return false;
    }

    public void payRent(Integer IDNo,String unit_number,BigDecimal amount,Date rentMonth,String receiptNumber){
        String makePayment="INSERT INTO PAYMENTS(Tenant_ID,Property_ID,Payment_type,Amount,Rent_month,Payment_date) values(?,?,'Rent',?,?,NOW())";
        String makeReceipt ="INSERT INTO RECEIPTS(Payment_ID,Date_issued,Receipt_number,Issued_by)values(?,NOW(),?,'System')";
        try {
            int paymentIDGenerated=0;
            this.conn.setAutoCommit(false);
            //Fetch property ID
            int propertyID=getPropertyID(unit_number);
            //insert into payments table
            try(PreparedStatement rentstatement=this.conn.prepareStatement(makePayment,Statement.RETURN_GENERATED_KEYS)){
                rentstatement.setInt(1,IDNo);
                rentstatement.setInt(2,propertyID);
                rentstatement.setBigDecimal(3,amount);
                rentstatement.setDate(4,rentMonth);
                int affectedRows=rentstatement.executeUpdate();
                if(affectedRows==0){
                    System.err.println("0 rows affected. Failed to pay rent");
                }else {
                    System.out.println("Rent successfully paid");
                }
                try (ResultSet generatedKeys=rentstatement.getGeneratedKeys()){
                    if (generatedKeys.next()){
                         paymentIDGenerated=generatedKeys.getInt(1);
                        System.out.println("Payment ID generated: "+paymentIDGenerated);
                    }
                }
            }
            try (PreparedStatement receiptStatement=this.conn.prepareStatement(makeReceipt)){
                receiptStatement.setInt(1,paymentIDGenerated);
                receiptStatement.setString(2,receiptNumber);
                receiptStatement.executeUpdate();
                System.out.println("Receipt stored successfully");
            }
            this.conn.commit();
        } catch (Exception e) {
            try {
                this.conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error while rolling back");
                ex.printStackTrace();
            }//throw new RuntimeException(e);
            System.err.println("Failed to pay rent");
            e.printStackTrace();
        }
            finally {
                try {
                    this.conn.setAutoCommit(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
        }
    }

    /*public ResultSet printReceiptDetails(){
    String receiptDetails="SELECT t.Tenant_ID,t.Tenant_name,p.Property_ID,p.Unit_number,pt.Payment_type,pt.Rent_month,pt.Amount,pt.Payment_ID,r.Receipt_number,r.Date_issued FROM tenants t JOIN Payments pt on t.Tenant_ID=pt.Tenant_ID JOIN Properties p on p.Property_ID=pt.Property_ID JOIN RECEIPTS r on pt.Payment_ID=r.Payment_ID";
    try (PreparedStatement preparedStatement=this.conn.prepareStatement(receiptDetails)){
        ResultSet resultSet=preparedStatement.executeQuery();
        return resultSet;
    }catch (SQLException e){
        e.printStackTrace();
        return null;
    }
    }*/

    public ResultSet printReceiptDetails() {
        String receiptDetails = "SELECT t.Tenant_ID, t.Tenant_name, p.Property_ID, p.Unit_number,pt.Payment_type, pt.Rent_month, pt.Amount, pt.Payment_ID,r.Receipt_number, r.Date_issued FROM tenants t JOIN Payments pt ON t.Tenant_ID = pt.Tenant_ID JOIN Properties p ON p.Property_ID = pt.Property_ID JOIN RECEIPTS r ON pt.Payment_ID = r.Payment_ID";

        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = this.conn.prepareStatement(receiptDetails);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
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

    public int countUnoccupiedHouses() {
        String unoccupiedHouses = "SELECT COUNT(*) AS unoccupied_houses FROM properties LEFT JOIN occupancy ON properties.Property_ID=occupancy.Property_ID WHERE occupancy.Property_ID IS NULL OR occupancy.Date_vacated IS NOT NULL";
        try (
                PreparedStatement preparedStatement = this.conn.prepareStatement(unoccupiedHouses);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (resultSet.next()) {
                return resultSet.getInt("unoccupied_houses");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countNoticesFiled(){
    String noticesFiled="SELECT COUNT(*) AS notices_filed FROM notices";
    try (
            PreparedStatement preparedStatement=this.conn.prepareStatement(noticesFiled);
            ResultSet resultSet=preparedStatement.executeQuery()
    ){
        if (resultSet.next()){
            return resultSet.getInt("notices_filed");
        }
    }catch (SQLException e){
        e.printStackTrace();
    }
    return 0;
    }

    public int countComplaintsFiled(){
    String complaintsFiled="SELECT COUNT(*) AS complaints_filed FROM complaints";
    try (
            PreparedStatement preparedStatement=this.conn.prepareStatement(complaintsFiled);
            ResultSet resultSet=preparedStatement.executeQuery()
    ){
        if (resultSet.next()){
            return resultSet.getInt("complaints_filed");
        }
    }catch (SQLException e){
        e.printStackTrace();
    }
    return 0;
    }

    public void registerNewTenant(String Tenant_ID,String name,String Phone_no,String Email_address,String Unit_number){
        String registerTenant="INSERT INTO tenants(Tenant_ID,Tenant_name,Phone_number,Email_address,Status,Password) values(?,?,?,?,?,'Active','Greenview2025')";
        String addTenantToOccupancyTable="INSERT INTO occupancy(Property_ID,Tenant_ID,Date_occupied,Date_vacated) values(?,?,NOW(),NULL)";
        try {
            conn.setAutoCommit(false);

            //Get Property ID
            int propertyID=getPropertyID(Unit_number);

            //Insert tenant
            try (PreparedStatement tenantStatement=this.conn.prepareStatement(registerTenant)){
                tenantStatement.setString(1,Tenant_ID);
                tenantStatement.setString(2,name);
                tenantStatement.setString(3,Phone_no);
                tenantStatement.setString(4,Email_address);
                tenantStatement.executeUpdate();
            }

            //Insert into occupancy
            try (PreparedStatement occupancyStatement=this.conn.prepareStatement(addTenantToOccupancyTable)){
                occupancyStatement.setInt(1,propertyID);
                occupancyStatement.setString(2,Tenant_ID);
                occupancyStatement.executeUpdate();
            }
            conn.commit();
        }catch (Exception e){
            try {
                conn.rollback();
            }catch (SQLException ex){
                ex.printStackTrace();
            }finally {
                try {
                    conn.setAutoCommit(true);
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
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

public ResultSet showNoticesFiled(){
        String noticesFiled="SELECT t.tenant_name,t.tenant_ID,n.date_notice_issued,n.date_intend_to_leave,p.property_ID,p.unit_number from tenants t JOIN notices n ON t.tenant_ID=n.tenant_ID JOIN properties p on n.property_ID=p.property_ID";
        try (PreparedStatement preparedStatement=this.conn.prepareStatement(noticesFiled);
             ResultSet resultSet=preparedStatement.executeQuery()){
            return resultSet;
        }catch (SQLException e){
            System.err.println("Error while fetching notice details");
            e.printStackTrace();
            return null;
        }
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

//Used by both
    public int getPropertyID(String unitNumber){
        String getPropertyID="SELECT Property_ID FROM properties WHERE Unit_number=?";
        try (PreparedStatement preparedStatement=this.conn.prepareStatement(getPropertyID)){
            preparedStatement.setString(1,unitNumber);
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return resultSet.getInt("Property_ID");
                } else {
                    throw new RuntimeException("No property found with the given unit number" + unitNumber);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException("Error while fetching property ID",e);
        }
    }
}
