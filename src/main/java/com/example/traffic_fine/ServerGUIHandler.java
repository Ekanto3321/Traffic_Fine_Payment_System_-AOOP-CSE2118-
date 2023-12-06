package com.example.traffic_fine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*

FIX THE LOGGING SYSTEM

 */

public class ServerGUIHandler {
    static String logs="";

    //FXML CONTROLS
    @FXML
    TextArea logsTA;
    @FXML
    TextField name,age,address,NID,lisenceNo,vehicleNo,vehicleType,previousOffences,searchBox;

    @FXML
    public void loadLogs(ActionEvent e){
        logsTA.appendText(ServerLogs.readLogs());

    }


    @FXML
    public void loadData(){
        ArrayList<String> list = new ArrayList<>();
        BufferedReader dataReader = null;
        try {
            dataReader = new BufferedReader(new FileReader("data.txt"));
            String st;
            while((st = dataReader.readLine())!=null){
                list.add(st);
            }
            dataReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < list.size(); i++) {
            String s[] = list.get(i).split(",");

            if(searchBox.getText().equals(s[0])){
                name.setText(s[0]);
                vehicleNo.setText(s[2]);
                age.setText(s[3]);
                NID.setText(s[4]);
                lisenceNo.setText(s[5]);
                vehicleType.setText(s[6]);
                address.setText(s[7]);


            }
        }

    }

    public static void updateLogs(String s){
        logs = (new StringBuilder()).append(s+"\n").toString();
    }


}
