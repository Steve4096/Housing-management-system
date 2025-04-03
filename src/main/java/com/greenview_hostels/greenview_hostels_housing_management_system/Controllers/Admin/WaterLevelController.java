package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin;

import com.greenview_hostels.greenview_hostels_housing_management_system.Models.Model;
import com.greenview_hostels.greenview_hostels_housing_management_system.Models.WaterLevelMonitor;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WaterLevelController implements WaterLevelMonitor.WaterLevelListener {
    public ProgressBar WaterlevelIndicator;
    public Label WaterLevel_lbl;
    public Label WaterVolume_lbl;
    public Label Waterlevelstatus_lbl;
    public Circle WaterLevelStatusIndicator;
    public Label LastRefillDate_lbl;
    public Label TankCapacity_lbl;
    public Button Refill_btn;

    private final int TANK_CAPACITY=1000000;

    private WaterLevelMonitor monitor;

    public void initialize(){
        monitor=new WaterLevelMonitor(this);
        TankCapacity_lbl.setText(TANK_CAPACITY+" LITRES");
        Refill_btn.setOnAction(actionEvent -> refill());
    }

    @Override
    public void onWaterLevelUpdate(int level,boolean isRefilled) {
        Platform.runLater(()->{
            int currentWaterLevel=(TANK_CAPACITY*level)/100;
            WaterlevelIndicator.setProgress(level/100.0);
            WaterLevel_lbl.setText(level+"%");
            WaterVolume_lbl.setText(currentWaterLevel+" L");

            //Update status color
            if(level>50){
                Waterlevelstatus_lbl.setText("NORMAL");
                WaterLevelStatusIndicator.setFill(Color.GREEN);
            } else if (level>=30) {
                Waterlevelstatus_lbl.setText("LOW");
                WaterLevelStatusIndicator.setFill(Color.ORANGE);
            }else {
                Waterlevelstatus_lbl.setText("CRITICAL");
                WaterLevelStatusIndicator.setFill(Color.RED);
            }

            //Update last refilled date button
            String lastRefillTime=null;
            if (isRefilled){
                lastRefillTime= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                LastRefillDate_lbl.setText(lastRefillTime);
            }

            //Save to database
            Model.getInstance().getDatabaseConnection().updateWaterTankTable(level,currentWaterLevel,lastRefillTime);
        });
    }

    private void refill(){
            String timeStamp= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LastRefillDate_lbl.setText(timeStamp);
    }
}
