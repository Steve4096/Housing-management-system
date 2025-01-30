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

    opens com.greenview_hostels.greenview_hostels_housing_management_system to javafx.fxml;
    opens com.greenview_hostels.greenview_hostels_housing_management_system.Controllers to javafx.fxml;

    exports com.greenview_hostels.greenview_hostels_housing_management_system;
}