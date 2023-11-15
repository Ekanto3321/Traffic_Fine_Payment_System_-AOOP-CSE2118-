package com.example.traffic_fine;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client{


    Socket socket = null;
    InputStreamReader isr = null;
    OutputStreamWriter osw = null;
    BufferedReader br = null;
    BufferedWriter bw = null;
    String data = "0";

    Client(){

        try {
            socket = new Socket("localhost", 1234);
            osw = new OutputStreamWriter(socket.getOutputStream());
            bw = new BufferedWriter(osw);
            isr = new InputStreamReader(socket.getInputStream());
            br = new BufferedReader(isr);
            String receivedCoordinates;
            Scanner sc = new Scanner(System.in);
            while (true) {

                receivedCoordinates = sc.nextLine();
                bw.write(receivedCoordinates);
                bw.newLine();
                bw.flush();

                System.out.println(br.readLine());

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static void main(String args[]) {
        new Client();
    }
}
