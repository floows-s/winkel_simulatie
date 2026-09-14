module com.example.winkel_simulatie {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens core to javafx.fxml;
    exports core;
}