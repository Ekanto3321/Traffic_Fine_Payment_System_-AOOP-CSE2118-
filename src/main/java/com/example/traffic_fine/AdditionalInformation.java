package com.example.traffic_fine;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AdditionalInformation {

    @FXML
    TextField age,nID,lisenceNo, vehicleType,vID;
    @FXML
    TextArea address;
    @FXML
    Text text;


    @FXML
    public void submit(){
        if(!age.getText().isEmpty()&&!nID.getText().isEmpty()&&!lisenceNo.getText().isEmpty()&&!address.getText().isEmpty()&&!vID.getText().isEmpty()&&!vehicleType.getText().isEmpty()) {
            Client.sendText("update," + vID.getText()+ ","+ age.getText() + "," + nID.getText() + "," + lisenceNo.getText() + ","+vehicleType.getText()+"," + address.getText());
            text.setText("submitted successfully!");
        }
        else text.setText("fields are empty");
    }


}
