package com.example.traffic_fine;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*

FIX THE LOGGING SYSTEM

 */

public class ServerGUIHandler implements Initializable {
    static String logs="";
    private Timeline refreshTimeline;
    //FXML CONTROLS
    @FXML
    TextArea logsTA, offenseDetails;
    @FXML
    TextField name,age,address,NID,lisenceNo,vehicleNo,vehicleType,previousOffences,searchBox,reportedOffense,offenseLocation,trackingNo,fineAmount,amountDue,paymentStat;



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
                if(s[8].equals("ongoing")){
                    amountDue.setText(s[12]);
                    paymentStat.setText(s[15]);
                }
            }

        }

    }
    @FXML
    public void assignCase(){
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

        if(reportedOffense.getText() != null && offenseLocation.getText() != null &&
                fineAmount.getText() != null && trackingNo.getText() != null && offenseDetails.getText() != null) {

            for (int i = 0; i < list.size(); i++) {
                String na[] = list.get(i).split(",");

                if (na[0].equals(name.getText())) {
                    String s[] = list.get(i).split(",");
                    String oldData = s[0]+","+s[1]+","+s[2]+","+s[3]+","+s[4]+","+s[5]+","+s[6]+","+s[7];
                    list.set(i, oldData + ",ongoing," + "A new fine has been added," + reportedOffense.getText() + ","
                            + offenseLocation.getText() + "," + fineAmount.getText() + "," + trackingNo.getText() + ","
                            + offenseDetails.getText() + "," + "unpaid");
                }
            }

            BufferedWriter dataWriter = null;
            try {
                dataWriter = new BufferedWriter(new FileWriter("data.txt"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String file = null, fileCheck = " ";
            for (int i = 0; i < list.size(); i++) {

                file += list.get(i) + "\n";
            }

            try {
                if (!fileCheck.equals(file)) {
                    dataWriter.write(file);
                    fileCheck = file;
                    dataWriter.close();
                }
                dataWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }


    public static void updateLogs(String s){
        logs = (new StringBuilder()).append(s+"\n").toString();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTimeline = new Timeline(
                new KeyFrame(javafx.util.Duration.seconds(1), event -> {
                    String temp = ServerLogs.readLogs();
                    logsTA.setText(ServerLogs.readLogs());
                    if(!searchBox.getText().equals("")){
                        loadData();
                    }

                })
        );
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }
}
