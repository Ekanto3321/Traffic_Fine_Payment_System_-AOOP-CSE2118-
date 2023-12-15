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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Dash implements Initializable{
    static User user;
    Stage stage;
    Parent root;
    @FXML
    private TextField tf,off;

    @FXML
    private Label due;

    @FXML
    private BorderPane bp;

    @FXML
    private AnchorPane ap;

    @FXML
    private Label userLB;

    @FXML
    private Button bt;
    @FXML
    private TextArea notifTA,det;

    @FXML
    void button1(MouseEvent event) {
        bp.setCenter(ap);
    }

    @FXML
    void button2(MouseEvent event) throws IOException {
        loadpage("feat1");
    }

    @FXML
    void button3(MouseEvent event) throws IOException {
        loadpage("feat2");
    }
    @FXML
    void button4(MouseEvent event) throws IOException {
        loadpage("profile");
    }


    private void loadpage(String page) throws IOException {
        Parent root = null;

        try{
            root = FXMLLoader.load(this.getClass().getResource(page+".fxml"));

        }catch (IOException ex){
            Logger.getLogger(Dash.class.getName()).log(Level.SEVERE,null,ex);
        }
        bp.setCenter(root);
    }


    @FXML //switches from Dash to login Screen
    public void logOut(ActionEvent e) throws IOException {
        Client.sendText("exit");
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        root = FXMLLoader.load(this.getClass().getResource("loginScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void payButton(ActionEvent e) throws IOException {
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        root = FXMLLoader.load(this.getClass().getResource("finePaymentScreen.fxml"));
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
    public void loadInformation(){

        String s[] = Dash.user.userInfo.split(",");

        if(s.length>10) {
            off.setText(s[10]);
            due.setText(s[12]);
            det.setText(s[14]);
        }

    }

    //gets name of user that logged in
    public static void setUser(User u){
        user = u;
    }



    private Timeline refreshTimeline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTimeline = new Timeline(
                new KeyFrame(javafx.util.Duration.seconds(1), event -> {
                    // Call the loadInformation method to refresh the information
                    Client.sendText("fetch,"+user.name);
                    Dash.user.userInfo = Client.receiveText();
                    loadInformation();
                    String s[]=user.userInfo.split(",");
                    if(s.length>10&&s[8].equals("ongoing")) notifTA.setText(s[9]);

                })
        );
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }

    @FXML
    public void stopAutoRefresh() {
        if (refreshTimeline != null) {
            refreshTimeline.stop();
        }
    }

}
