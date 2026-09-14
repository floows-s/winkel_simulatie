module com.example.winkel_simulatie {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.winkel_simulatie to javafx.fxml;
    exports com.example.winkel_simulatie;
}