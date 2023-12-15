package com.example.traffic_fine;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FinePayment implements Initializable {

    Stage stage;
    Parent root;
    @FXML
    TextField status,amountDue,trackingNo,amountToPay;


    @FXML
    public void backToDash(ActionEvent e) throws IOException {
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        root = FXMLLoader.load(this.getClass().getResource("Dash.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void payFine(){
        Client.sendText("payment,"+amountToPay.getText());
        Dash.user.userInfo = Client.receiveText();
    }
    private Timeline refreshTimeline;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTimeline = new Timeline(
                new KeyFrame(javafx.util.Duration.seconds(1), event -> {
                    String s[] = Dash.user.userInfo.split(",");

                    if(s.length>10){
                        status.setText(s[15]);
                        amountDue.setText(s[12]);
                        trackingNo.setText(s[13]);
                    }

                })
        );
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }
}
