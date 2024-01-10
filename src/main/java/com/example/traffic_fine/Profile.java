package com.example.traffic_fine;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class Profile implements Initializable {

    public static String userData,check="";

    @FXML
    TextField name,age,address,NID,lisenceNo,vehicleNo,vehicleType,previousOffences;


    private Timeline refreshTimeline;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        refreshTimeline = new Timeline(
                new KeyFrame(javafx.util.Duration.seconds(1), event -> {
                    String s[] = Dash.user.userInfo.split(",");

                    name.setText(s[0]);
                    vehicleNo.setText(s[2]);
                    if(s.length>3){
                        age.setText(s[3]);
                        NID.setText(s[4]);
                        lisenceNo.setText(s[5]);
                        vehicleType.setText(s[6]);
                        address.setText(s[7]);

                    }

                })
        );


        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }
}
