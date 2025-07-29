package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Property;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HousesController implements Initializable {
    public TextField Search_txtfield;
    public Button Search_btn;
    public ListView Houses_listview;
    public Button Add_property_btn;
    public TableView Properties_tableview;
    public TableColumn unitNumber_column;
    public TableColumn unitType_column;
    //public TableColumn Rent_amount_column;

    private ObservableList<Property> propertyList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        unitNumber_column.setCellValueFactory(new PropertyValueFactory<>("unitNumber"));
        unitType_column.setCellValueFactory(new PropertyValueFactory<>("unitType"));
        propertyList=Model.getInstance().showExistingProperties();
        if(propertyList.isEmpty()){
            System.out.println("No properties found");
        }else {
            System.out.println("Retrieved properties:"+propertyList.size());
        }
        Properties_tableview.setItems(propertyList);
        Add_property_btn.setOnAction(actionEvent -> Addproperty());
    }

    private void Addproperty(){
        Stage stage=(Stage) Add_property_btn.getScene().getWindow();
        Model.getInstance().getViewsfactory().CloseWindow(stage);
        Model.getInstance().getViewsfactory().getAddpropertywindow();
    }
}
