module com.example.demo6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.traffic_fine to javafx.fxml;
    exports com.example.traffic_fine;
}