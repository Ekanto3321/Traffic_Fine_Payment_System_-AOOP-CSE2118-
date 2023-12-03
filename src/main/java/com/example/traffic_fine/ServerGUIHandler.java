package com.example.traffic_fine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ServerGUIHandler {


    String logs="hello";

    //FXML CONTROLS
    @FXML
    TextArea logsTA;

    @FXML
    public void loadLogs(ActionEvent e){
        logsTA.appendText(logs+"\n");
    }


}
