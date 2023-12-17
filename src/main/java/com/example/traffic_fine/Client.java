package com.example.traffic_fine;

import Cypher.CypherHandler;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client{


    Socket socket = null;
    InputStreamReader in = null;
    OutputStreamWriter out = null;
    static BufferedReader br = null;
    static BufferedWriter bw = null;
    String data = "0";

    static String message;
    Scanner sc;
    CypherHandler cp = new CypherHandler();

    Client(){
        try {

            socket = new Socket("localhost", 1234);
            out = new OutputStreamWriter(socket.getOutputStream());
            bw = new BufferedWriter(out);
            in = new InputStreamReader(socket.getInputStream());
            br = new BufferedReader(in);
            sc = new Scanner(System.in);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void sendText(String s){
        try {
            bw.write(CypherHandler.encryptor(s));
            bw.newLine();
            bw.flush();
            System.out.println("Client sent: "+ s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static String receiveText(){
        try {
            String s = CypherHandler.decryptor(br.readLine());
            System.out.println("Client received: "+ s);
            return s;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void main(String args[]) {
        new Client();
    }

    public void closeConnection(Socket socket, BufferedReader br, BufferedWriter bw){
        try {
            if(socket!=null)bw.close();
            if(socket!=null)socket.close();
            if(socket!=null)bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
