package com.example.traffic_fine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SignUpScreen {

    @FXML
    private Button backBtn;
    @FXML
    TextField usernameTB, passwordTB, vIDTB;
    @FXML
    Text signUpStatus;
    Stage stage;
    Parent root;
    @FXML
    public void backToLogIn(ActionEvent e) throws IOException {
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        root = FXMLLoader.load(this.getClass().getResource("loginScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void confirmSignUp() throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter("info.txt",true));
        User user = new User(usernameTB.getText(),passwordTB.getText(),vIDTB.getText(),new Random().nextInt(500,1000));
        String s;
        s = user.name+","+user.password+","+ user.vID+","+user.cred+"\n";
        if(!usernameTB.getText().isEmpty() && !usernameTB.getText().isEmpty() && !vIDTB.getText().isEmpty()){
            bw.write(s);
            signUpStatus.setText("Sign Up successful!");
        }
        else signUpStatus.setText("Fields are empty!!");
        bw.close();

    }



}
