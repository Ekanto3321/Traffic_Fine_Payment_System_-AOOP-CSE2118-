package com.example.traffic_fine;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Feat3 implements Initializable {

    @FXML
    private TextField taxtoken;

    @FXML
    private TextField paytax;

    private Timeline refreshTimeline;

    @FXML
    public void submit(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTimeline = new Timeline(
                new KeyFrame(javafx.util.Duration.seconds(1), event -> {
                    String s[] = Dash.user.userInfo.split(",");
                    taxtoken.setText(s[3]);



                })
        );
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }
}
