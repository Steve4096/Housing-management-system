module com.greenview_hostels.greenview_hostels_housing_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    requires java.net.http;
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires okhttp3;
    requires jdk.httpserver;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;
    //requires org.apache.commons.codec;

    opens com.greenview_hostels.greenview_hostels_housing_management_system to javafx.fxml;
    opens com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant to javafx.fxml;
    opens com.greenview_hostels.greenview_hostels_housing_management_system.Models to javafx.base;

    exports com.greenview_hostels.greenview_hostels_housing_management_system;
    opens com.greenview_hostels.greenview_hostels_housing_management_system.Controllers to javafx.base, javafx.fxml;
    opens com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Admin to javafx.base, javafx.fxml;
}