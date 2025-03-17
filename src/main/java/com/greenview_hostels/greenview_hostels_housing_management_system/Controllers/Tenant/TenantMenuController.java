package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import com.greenview_hostels.greenview_hostels_housing_management_system.Views.TenantMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TenantMenuController implements Initializable {
    public Button Dashboard_btn;
    public Button PayRent_btn;
    public Button IssueNotice_btn;
    public Button FileComplaint_btn;
    public Button Receipts_btn;
    public Button Payments_btn;
    public Button Inbox_btn;
    public Button LogOut_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Dashboard_btn.setOnAction(actionEvent -> showDashboard());
        PayRent_btn.setOnAction(actionEvent -> showRentPaymentWindow());
        IssueNotice_btn.setOnAction(actionEvent -> showNoticeIssueWindow());
        Receipts_btn.setOnAction(actionEvent -> showReceiptsWindow());
        Payments_btn.setOnAction(actionEvent -> showPaymentsWindow());
        FileComplaint_btn.setOnAction(actionEvent -> showRequestServiceWindow());
        LogOut_btn.setOnAction(actionEvent -> logout());
    }

    private void showDashboard(){
        Model.getInstance().getViewsfactory().getTenantSelectedMenuItem().set(TenantMenuOptions.DASHBOARD);
    }

    private void showRentPaymentWindow(){
        Model.getInstance().getViewsfactory().getTenantSelectedMenuItem().set(TenantMenuOptions.PAY_RENT);
    }

    private void showNoticeIssueWindow(){
        Model.getInstance().getViewsfactory().getTenantSelectedMenuItem().set(TenantMenuOptions.ISSUE_NOTICE);
    }

    private void showRequestServiceWindow(){
        Model.getInstance().getViewsfactory().getTenantSelectedMenuItem().set(TenantMenuOptions.SERVICE_REQUEST);
    }

    private void showReceiptsWindow(){
        Model.getInstance().getViewsfactory().getTenantSelectedMenuItem().set(TenantMenuOptions.RECEIPTS);
    }

    private void showPaymentsWindow(){
        Model.getInstance().getViewsfactory().getTenantSelectedMenuItem().set(TenantMenuOptions.PAYMENTS);
    }

    private void logout(){
        Stage stage=(Stage)LogOut_btn.getScene().getWindow();
        Model.getInstance().getViewsfactory().CloseWindow(stage);
        Model.getInstance().getViewsfactory().showLoginAccountSelectorWindow();
    }
}
