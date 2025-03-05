package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Tenant;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TenantsController implements Initializable  {
    public TextField Search_txtfield;
    public Button Search_btn;
    public ListView Tenants_listview;
    public TableView Tenants_tblview;
    public TableColumn Fname;
    public TableColumn Lname;
    public TableColumn PhoneNo;
    public TableColumn HouseNo;
    public TableColumn Date_moved_in;

    private ObservableList<Tenant> tenantList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Fname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        Lname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        PhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        tenantList=Model.getInstance().showExistingTenantDetails();
        if(tenantList.isEmpty()){
            System.out.println("No records found");
        }else {
            System.out.println("Records retrieved:"+tenantList.size());
        }
        Tenants_tblview.setItems(tenantList);
    }
}
