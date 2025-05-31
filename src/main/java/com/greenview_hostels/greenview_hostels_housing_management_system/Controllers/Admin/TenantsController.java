package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Property;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Tenant;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TenantsController implements Initializable  {
    public TextField Search_txtfield;
    public Button Search_btn;
    public ListView Tenants_listview;
    public TableView Tenants_tblview;
    public TableColumn Fname;
    public TableColumn Lname;
    public TableColumn PhoneNo;
    public TableColumn<Tenant,String> HouseNo;
    public TableColumn Date_moved_in;
    public TableColumn Tname;

    private ObservableList<Tenant> tenantList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Fname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        //Lname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        Tname.setCellValueFactory(new PropertyValueFactory<>("tenantName"));
        PhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));

        HouseNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tenant, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tenant, String> param) {
                Tenant tenant=param.getValue();
                if (!tenant.getProperties().isEmpty()){
                    return tenant.getProperties().get(0).unitNumberProperty();
            }else {
                    return new SimpleStringProperty("N/A");
                }
        }
    });
        Date_moved_in.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tenant,String>,ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tenant,String> param) {
                LocalDateTime dateMovedIn=param.getValue().dateMovedInProperty().get();
                if(dateMovedIn!=null){
                    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    return new SimpleStringProperty(dateMovedIn.format(formatter));
                }else {
                    return new SimpleStringProperty("N/A");
                }
            }
        });
        tenantList=Model.getInstance().showExistingTenantDetails();
        if(tenantList.isEmpty()){
            System.out.println("No records found");
        }else {
            System.out.println("Records retrieved:"+tenantList.size());
        }
        Tenants_tblview.setItems(tenantList);
    }
}
