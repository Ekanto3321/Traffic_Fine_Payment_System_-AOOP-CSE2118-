package com.example.traffic_fine;

import java.io.*;
import java.util.ArrayList;

public class ServerLogs {
    public static void addLogs(String s){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("logs.txt",true));
            bw.append(s+"\n");
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String readLogs(){
        ArrayList<String> list = new ArrayList<>();
        BufferedReader dataReader = null;
        try {
            String st;
            String ret="";
            dataReader = new BufferedReader(new FileReader("logs.txt"));
            while((st=dataReader.readLine())!=null){
                list.add(st);
            }
            for (int i = 0; i < list.size(); i++) {
                ret = list.get(i)+"\n";
            }

            return ret;

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
