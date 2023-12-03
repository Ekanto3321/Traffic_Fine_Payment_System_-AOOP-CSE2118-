package com.example.traffic_fine;

import Cypher.CypherHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket serverSocket = null;
    Socket socket = null;




    Server(){
        try {
            serverSocket = new ServerSocket(1234);
            System.out.println("server is online");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while(true) {
            try {
                socket = serverSocket.accept();

                System.out.println("A client has connected");

                ClientHandlerAPI clientHandlerAPI = new ClientHandlerAPI(socket);

                //new Thread for every client
                Thread thread = new Thread(clientHandlerAPI);
                thread.start();

            } catch (IOException e) {
                throw new RuntimeException(e);

            }
        }
    }

    public static void main(String[] args) {
        new Server();
    }

}

class ClientHandlerAPI implements Runnable{
    InputStreamReader in = null;
    OutputStreamWriter out = null;
    BufferedWriter bw = null;
    BufferedReader br = null;
    String query;
    String sentData;
    Socket socket;
    ClientHandlerAPI(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {

        CypherHandler cp = new CypherHandler();
        try {
            in = new InputStreamReader(socket.getInputStream());
            out = new OutputStreamWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        br = new BufferedReader(in);
        bw = new BufferedWriter(out);

        /*
        Data is sent and received here
         */
        try {
            while (true) {

                query = cp.decryptor(br.readLine());
                if(query.equals("exit")){
                    System.out.println("A client has closed connection");
                    break;
                }
                    sentData = query;
                    bw.write(cp.encryptor(sentData));
                    bw.newLine();
                    bw.flush();

            }
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


}
