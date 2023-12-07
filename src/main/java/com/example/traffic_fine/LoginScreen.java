package com.example.traffic_fine;

import Cypher.CypherHandler;
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
        String username = usernameTF.getText();
        String password = pwTF.getText();

        if(!username.isEmpty() && !password.isEmpty()) {

            Client.sendText("login,"+username+","+password);

            String received = Client.receiveText();

            System.out.println("loginR: "+received);

            String data[] = received.split(",");

            if (data[0].equals("yes")) {
                //sending user object with all information to dash class
                User user = new User(data[1],data[2],data[3]);
                Dash.setUser(user);

                Client.sendText("fetch,"+user.name);
                Dash.user.userInfo = Client.receiveText();

                System.out.println(Dash.user.userInfo);

                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                root = FXMLLoader.load(this.getClass().getResource("Dash.fxml"));
                Scene scene = new Scene(root);
                stage.setTitle("Dash");
                stage.setScene(scene);
                stage.show();

            }
            else if(data[0].equals("no")) {
                    loginStatus.setText("Username or Password doesn't match");
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
