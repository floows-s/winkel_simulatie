module com.example.winkel_simulatie {
    requires javafx.controls;
    requires javafx.fxml;


    opens core to javafx.fxml;
    exports core;
}