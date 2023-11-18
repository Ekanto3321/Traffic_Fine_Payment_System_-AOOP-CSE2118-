package com.example.traffic_fine;

import Cypher.CypherHandler;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client2{


    Socket socket = null;
    InputStreamReader in = null;
    OutputStreamWriter out = null;
    BufferedReader br = null;
    BufferedWriter bw = null;
    String data = "0";
    CypherHandler cp = new CypherHandler();

    Client2(){


        try {
            socket = new Socket("localhost", 1234);
            out = new OutputStreamWriter(socket.getOutputStream());
            bw = new BufferedWriter(out);
            in = new InputStreamReader(socket.getInputStream());
            br = new BufferedReader(in);
            String dataToSend;
            String receivedData = " ";
            String decryptedReceivedData=" ";
            Scanner sc = new Scanner(System.in);

            /*
            Data sending and reception part
             */
            while (true) {
                dataToSend = sc.nextLine();

                bw.write(cp.encryptor(dataToSend));
                bw.newLine();
                bw.flush();
                System.out.println(cp.encryptor(dataToSend));//test
                if(dataToSend.equals("exit")){
                    System.out.println("connection closed");
                    break;
                }
                decryptedReceivedData = cp.decryptor(br.readLine());
                System.out.println("From server: " + decryptedReceivedData);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            bw.close();
            br.close();
        } catch (IOException e) {
            closeConnection(socket,br,bw);
        }
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

    public static void main(String args[]) {
        new Client();
    }
}

