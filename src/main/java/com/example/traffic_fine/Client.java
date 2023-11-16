package com.example.traffic_fine;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client{


    Socket socket = null;
    InputStreamReader in = null;
    OutputStreamWriter out = null;
    BufferedReader br = null;
    BufferedWriter bw = null;
    String data = "0";

    Client(){

        try {
            socket = new Socket("localhost", 1234);
            out = new OutputStreamWriter(socket.getOutputStream());
            bw = new BufferedWriter(out);
            in = new InputStreamReader(socket.getInputStream());
            br = new BufferedReader(in);
            String dataToSend;
            Scanner sc = new Scanner(System.in);
            while (true) {
                dataToSend = sc.nextLine();

                bw.write(dataToSend);
                bw.newLine();
                bw.flush();
                if(dataToSend.equals("exit")){
                    System.out.println("connection closed");
                    break;
                }
                System.out.println(br.readLine());


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
