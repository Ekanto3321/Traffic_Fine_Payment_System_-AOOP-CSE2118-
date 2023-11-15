package com.example.traffic_fine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoginScreen {

    @FXML
    private Button loginBtn;
    @FXML
    private TextField usernameTF, pwTF;
    @FXML
    private Label loginStatus;
    Stage stage;
    Parent root;

    ArrayList<String> userData = new ArrayList<>();

    @FXML
    public void logIn(ActionEvent e) throws IOException {
        readUserData();

        if(!usernameTF.getText().isEmpty() && !pwTF.getText().isEmpty()) {
            for (int i = 0; i < userData.size(); i++) {
                //splitting info string
                String data[] = userData.get(i).split(",");
                if (data[0].equals(usernameTF.getText()) && data[1].equals(pwTF.getText())) {

                    //sending user object with all information to dash class
                    User user = new User(data[0],data[1],data[2],Integer.parseInt(data[3]));
                    Dash.setUser(user);
                    stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    root = FXMLLoader.load(this.getClass().getResource("Dash.fxml"));
                    Scene scene = new Scene(root);
                    stage.setTitle("Dash");
                    stage.setScene(scene);
                    stage.show();
                    break;
                } else {
                    loginStatus.setText("Username or Password doesn't match");
                }
            }
        }
        else loginStatus.setText("Username and Password fields are empty!");
    }
    @FXML
    public void signUp(ActionEvent e) throws IOException {
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        root = FXMLLoader.load(this.getClass().getResource("signUpScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Sign Up");
        stage.setScene(scene);
        stage.show();
    }

    public void readUserData() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("info.txt"));
        String st;
        while((st = br.readLine())!=null){
            userData.add(st);
        }
        br.close();
    }

}
