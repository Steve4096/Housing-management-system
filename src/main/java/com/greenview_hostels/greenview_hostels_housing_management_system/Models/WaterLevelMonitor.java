package com.greenview_hostels.greenview_hostels_housing_management_system.Models;

import javafx.scene.control.ProgressBar;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WaterLevelMonitor {
    private int waterlevel=80;
    private final Random random=new Random();
    private final WaterLevelListener listener;
    private final ScheduledExecutorService executorService= Executors.newScheduledThreadPool(1);

    public interface WaterLevelListener{
        void onWaterLevelUpdate(int level,boolean isRefilled);
    }

    public WaterLevelMonitor(WaterLevelListener listener){
        this.listener=listener;
        startSimulation();
    }

    private void startSimulation(){
        executorService.scheduleAtFixedRate(()->{
            //Simulate water usage
            int change=random.nextInt(6)+1;
            waterlevel=Math.max(waterlevel-change,0); //Ensure it doesnt go below 0

            boolean isRefilled=false;

            //Simulate refill
            if (waterlevel<20 && random.nextBoolean()){
                waterlevel=100;
                isRefilled=true;
                System.out.println("Tank refilled");
            }

            //Notify UI
            listener.onWaterLevelUpdate(waterlevel,isRefilled);

            //Stop if tank is empty
            if(waterlevel==0){
                System.out.println("Tank is empty.Urgently refill");
            }
        },0,5, TimeUnit.SECONDS); //Updates every 5 seconds
    }
}
