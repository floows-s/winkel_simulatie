module com.example.winkel_simulatie {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;


    opens core to javafx.fxml;
    exports core;

    // GSON OPENS
    opens objects.shelf to com.google.gson;
}