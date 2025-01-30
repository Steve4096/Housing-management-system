package com.greenview_hostels.greenview_hostels_housing_management_system.Models;

import com.greenview_hostels.greenview_hostels_housing_management_system.Views.Viewsfactory;

public class Model {
    private static Model model;
    private static Viewsfactory viewsfactory;

    private Model(){
        this.viewsfactory=new Viewsfactory();
    }

        public static synchronized Model getInstance(){
        if (model==null){
            model=new Model();
        }
        return model;
        }

    public Viewsfactory getViewsfactory() {
        return viewsfactory;
    }
}
