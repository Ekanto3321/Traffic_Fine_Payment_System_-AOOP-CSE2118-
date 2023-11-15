package com.example.traffic_fine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class FinePayment {

    Stage stage;
    Parent root;
    @FXML
    Label u_name, u_ID,u_credit;


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
        u_name.setText(Dash.user.name);
        u_credit.setText(String.valueOf(Dash.user.cred));
        u_ID.setText(Dash.user.vID);

    }
}
