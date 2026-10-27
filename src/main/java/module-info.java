module com.example.winkel_simulatie {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;
    requires jdk.jshell;
    requires java.sql;
    requires org.junit.jupiter.api;


    opens core to javafx.fxml;
    exports core;

    opens objects.shelf to com.google.gson;
    opens objects.graph to com.google.gson, org.junit.platform.commons;
    opens objects.graph.Vertex to com.google.gson;
}