package com.example.traffic_fine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Dash {
    static User user;
    Stage stage;
    Parent root;
    @FXML
    private TextField tf;

    @FXML
    private Label userLB;

    @FXML
    private Button bt;
    @FXML
    private TextArea notifTA;

    @FXML
    public void button(ActionEvent e){
        userLB.setText("User: " + user.name+ "\nVehicle: " + user.vID+"\nUser fine factor: "+user.cred);
        System.out.println(user.cred);
    }

    @FXML //switches from Dash to login Screen
    public void logOut(ActionEvent e) throws IOException {
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        root = FXMLLoader.load(this.getClass().getResource("loginScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void payFine(ActionEvent e) throws IOException {
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        root = FXMLLoader.load(this.getClass().getResource("finePaymentScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Fines");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void showNotif(){
        notifTA.setText(user.name+"'s "+"Notifications go here");
    }

    //gets name of user that logged in
    public static void setUser(User u){
        user = u;
    }

}
