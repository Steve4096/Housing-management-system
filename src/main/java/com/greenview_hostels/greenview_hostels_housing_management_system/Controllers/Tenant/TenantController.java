package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Tenant;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TenantController implements Initializable {
    public BorderPane Tenant_parent;
    //public Tenant tenant=Model.getInstance().getTenant();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewsfactory().getTenantSelectedMenuItem().addListener((observableValue, tenantMenuOptions, newVal) ->{
            switch (newVal){
                case DASHBOARD -> loadDashboard();
                case PAY_RENT -> loadRentPayment();
                case PAYMENTS -> Tenant_parent.setCenter(Model.getInstance().getViewsfactory().showPreviousPayments());
                case ISSUE_NOTICE -> loadIssueNotice();
                case SERVICE_REQUEST -> loadServiceRequest();
                case RECEIPTS -> loadReceipts();
            }
        } );
        loadDashboard();
    }

    private void loadDashboard(){
        Tenant tenant=Model.getInstance().getTenant();
        Tenant_parent.setCenter(Model.getInstance().getViewsfactory().TenantDashboard(tenant));
    }

    private void loadIssueNotice(){
        Tenant tenant=Model.getInstance().getTenant();
        Tenant_parent.setCenter(Model.getInstance().getViewsfactory().showFileNotice(tenant));
    }

    private void loadRentPayment(){
        Tenant tenant=Model.getInstance().getTenant();
        Tenant_parent.setCenter(Model.getInstance().getViewsfactory().getRentPaymentWindow(tenant));
    }

    private void loadServiceRequest(){
        Tenant tenant=Model.getInstance().getTenant();
        Tenant_parent.setCenter(Model.getInstance().getViewsfactory().showServiceRequest(tenant));
    }

    private void loadReceipts(){
        Tenant tenant=Model.getInstance().getTenant();
        Tenant_parent.setCenter(Model.getInstance().getViewsfactory().getReceiptsWindow(tenant));
    }
}
