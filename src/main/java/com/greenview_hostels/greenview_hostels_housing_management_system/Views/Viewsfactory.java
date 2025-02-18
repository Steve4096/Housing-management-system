package com.greenview_hostels.greenview_hostels_housing_management_system.Views;

import com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin.AdminController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Viewsfactory  {
    //Client views section
    private AnchorPane splashScreen;
    private AnchorPane dashboard;
    private AnchorPane forgotPasswordScreen;

    //Admin views section
    private ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private AnchorPane admindashboard;
    private AnchorPane housesview;
    private AnchorPane paymentsview;
    private AnchorPane tenantsview;
    private AnchorPane registrationwindow;

    //Viewsfactory Constructor
    public Viewsfactory(){
        this.adminSelectedMenuItem=new SimpleObjectProperty<>(AdminMenuOptions.DASHBOARD);
    }

//Tenant section
    public void showTenantLoginWindow(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Fxml/Tenant/Login.fxml"));
        CreateStage(loader,null);
    }

    public void showForgotPasswordScreen(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Fxml/Tenant/ForgotPassword.fxml"));
        CreateStage(loader,null);
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
                admindashboard=new FXMLLoader(getClass().getResource("/Fxml/Admin/Dashboard")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return admindashboard;
    }

    public AnchorPane getRegistrationwindow(){
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

    public AnchorPane getPaymentsviewwindow(){
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
