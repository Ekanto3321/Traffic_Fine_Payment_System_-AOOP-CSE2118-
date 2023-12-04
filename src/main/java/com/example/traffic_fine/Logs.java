package com.example.traffic_fine;

import java.io.*;
import java.util.ArrayList;

public class Logs{
    public static void addLogs(String s){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("logs.txt"));
            bw.append(s);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String readLogs(){
        ArrayList<String> list = new ArrayList<>();
        BufferedReader dataReader = null;
        try {
            dataReader = new BufferedReader(new FileReader("logs.txt"));

            String st = dataReader.readLine();
            return st+"\n";

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void clearLogs(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("logs.txt"));
            bw.write("");
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
