package com.greenview_hostels.greenview_hostels_housing_management_system.Views;

import com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin.AdminController;
import com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant.*;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Tenant;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Viewsfactory  {
    //Client views section
    private ObjectProperty<TenantMenuOptions> tenantSelectedMenuItem;
    private AnchorPane splashScreen;
    private AnchorPane dashboard;
    private AnchorPane forgotPasswordScreen;
    private ScrollPane rentPayment;
    private AnchorPane fileNotice;
    private AnchorPane receiptsWindow;
    private AnchorPane serviceRequest;
    private AnchorPane paymentsWindow;


    //Admin views section
    private ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private AnchorPane admindashboard;
    private AnchorPane housesview;
    private AnchorPane paymentsview;
    private AnchorPane tenantsview;
    private ScrollPane registrationwindow;
    private AnchorPane addpropertywindow;
    private AnchorPane waterLevelMonitoringWindow;

    //Viewsfactory Constructor
    public Viewsfactory(){
        this.adminSelectedMenuItem=new SimpleObjectProperty<>(AdminMenuOptions.DASHBOARD);
        this.tenantSelectedMenuItem=new SimpleObjectProperty<>(TenantMenuOptions.DASHBOARD);
    }

//Tenant section
    public ObjectProperty<TenantMenuOptions> getTenantSelectedMenuItem(){
        return tenantSelectedMenuItem;
    }

    public void showTenantLoginWindow(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Fxml/Tenant/Login.fxml"));
        CreateStage(loader,null);
    }

    public void showForgotPasswordScreen(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Fxml/Tenant/ForgotPassword.fxml"));
        CreateStage(loader,null);
    }

    public void showTenantDashboard() {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Tenant/Tenant.fxml"));
                TenantController tenantController = new TenantController();
                loader.setController(tenantController);
                CreateStage(loader, null);
    }

    public AnchorPane TenantDashboard(Tenant tenant){
        if(dashboard==null){
            try {
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/Fxml/Tenant/Dashboard.fxml"));
                dashboard=loader.load();
                DashboardController dashboardController=loader.getController();
                dashboardController.setTenant(tenant);
                }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return dashboard;
    }

    public ScrollPane getRentPaymentWindow(Tenant tenant){
        if (rentPayment==null){
            try {
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/Fxml/Tenant/PayRent.fxml"));
                rentPayment=loader.load();
                PayRentController controller=loader.getController();
                controller.setTenant(tenant);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return rentPayment;
    }

    public AnchorPane showFileNotice(Tenant tenant) {
        if (fileNotice==null){
            try {
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/Fxml/Tenant/IssueNotice.fxml"));
                fileNotice=loader.load();
                IssueNoticeController issueNoticeController=loader.getController();
                issueNoticeController.setTenant(tenant);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return fileNotice;
    }

    /*public AnchorPane getReceiptsWindow() {
        if(receiptsWindow==null){
            try {
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/Fxml/Tenant/"))
            }
        }
    }*/

    public AnchorPane showPreviousPayments(){
        if(paymentsWindow==null){
            try {
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/Fxml/Tenant/PaymentHistory.fxml"));
                paymentsWindow=loader.load();
                PaymentHistoryController paymentHistoryController=loader.getController();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return paymentsWindow;
    }

    public AnchorPane showServiceRequest(Tenant tenant){
        if(serviceRequest==null){
            try {
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/Fxml/Tenant/ServiceRequest.fxml"));
                serviceRequest=loader.load();
                ServiceRequestController serviceRequestController=loader.getController();
                serviceRequestController.setTenant(tenant);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return serviceRequest;
    }

    public void resetAllWindows(){
        dashboard=null;
        serviceRequest=null;
        fileNotice=null;
        rentPayment=null;
    }

    //Admin section
    public void showAdminLoginWindow(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Fxml/Admin/Login.fxml"));
        CreateStage(loader,null);
    }

    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem(){
        return adminSelectedMenuItem;
    }

    public void showAdminDashboard(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController adminController=new AdminController();
        loader.setController(adminController);
        CreateStage(loader,null);
    }

    public AnchorPane getDashboard(){
        if(admindashboard==null){
            try {
                admindashboard=new FXMLLoader(getClass().getResource("/Fxml/Admin/Dashboard.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return admindashboard;
    }

    public ScrollPane getRegistrationwindow(){
        if(registrationwindow==null){
            try {
                registrationwindow=new FXMLLoader(getClass().getResource("/Fxml/Admin/Registration.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return registrationwindow;
    }

    public AnchorPane getTenantslist(){
        if(tenantsview==null){
            try {
                tenantsview=new FXMLLoader(getClass().getResource("/Fxml/Admin/Tenants.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return tenantsview;
    }

    public AnchorPane getPaymentsviewWindow(){
        if(paymentsview==null){
            try {
                paymentsview=new FXMLLoader(getClass().getResource("/Fxml/Admin/Payments.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return paymentsview;
    }

    public AnchorPane getHousesview(){
        if(housesview==null){
            try {
            housesview=new FXMLLoader(getClass().getResource("/Fxml/Admin/Houses.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return housesview;
    }

    public AnchorPane getAddpropertywindow(){
        if(addpropertywindow==null){
            try {
                addpropertywindow=new FXMLLoader(getClass().getResource("/Fxml/Admin/AddProperty.fxml")).load();
                System.out.println("Window opened");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return addpropertywindow;
    }

    public AnchorPane showWaterLevelMonitoringWindow(){
        if (waterLevelMonitoringWindow==null){
            try {
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/Fxml/Admin/WaterLevelMonitor.fxml"));
                waterLevelMonitoringWindow=loader.load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return waterLevelMonitoringWindow;
    }


    //Both Tenant and Admin
    public Stage showSplashScreen(){
        Stage splashStage=new Stage();
        try {
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/Fxml/Splashscreen.fxml"));
            Scene scene=new Scene(fxmlLoader.load());
            //Stage stage=new Stage();
            splashStage.setTitle("Splashscreen");
            splashStage.setScene(scene);
            splashStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
        return splashStage;
    }

    private void CreateStage(FXMLLoader loader,Stage currentStage){
        try {
            Scene scene=new Scene(loader.load());
            Stage newStage=new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Greenview Hostels Housing Management System");
            newStage.setResizable(true);
            newStage.show();

            //Close the current stage
            if(currentStage!=null){
                currentStage.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showLoginAccountSelectorWindow(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Fxml/LoginAccountSelector.fxml"));
        CreateStage(loader,null);
    }

    public void CloseWindow(Stage stage){
        stage.close();
    }



}
