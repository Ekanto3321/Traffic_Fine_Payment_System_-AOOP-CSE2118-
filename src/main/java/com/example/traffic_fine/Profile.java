package com.example.traffic_fine;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Profile {

    public static String userData,check="";

    @FXML
    TextField name,age,address,NID,lisenceNo,vehicleNo,vehicleType,previousOffences;

    @FXML
    public void loadInformation(){

        String s[] = Dash.user.userInfo.split(",");

        name.setText(s[0]);
        vehicleNo.setText(s[2]);
        age.setText(s[3]);
        NID.setText(s[4]);
        lisenceNo.setText(s[5]);
        vehicleType.setText(s[6]);
        address.setText(s[7]);

    }
}
