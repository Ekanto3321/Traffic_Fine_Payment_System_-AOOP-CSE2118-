module com.example.demo6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.traffic_fine to javafx.fxml;
    exports com.example.traffic_fine;
    exports Cypher;
    opens Cypher to javafx.fxml;
}