package com.example.traffic_fine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class FinePayment {

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
    public void loadInformation(){

        String s[] = Dash.user.userInfo.split(",");

        status.setText(s[15]);
        amountDue.setText(s[12]);
        trackingNo.setText(s[13]);

    }

    @FXML
    public void payFine(){

        Client.sendText("payment");

    }
}
