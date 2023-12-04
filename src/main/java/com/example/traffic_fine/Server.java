package com.example.traffic_fine;

import Cypher.CypherHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
    String queryChecker=" ";
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


        /*
        Data is sent and received here
         */
        try {
            while (true) {

                query = CypherHandler.decryptor(br.readLine());

                String s[] = query.split(",");

                if(!queryChecker.equals(query)){
                    switch (s[0]){
                        case "signup":
                            ServerLoginSignupHandler.signUp(s[1]+","+s[2]+","+s[3]);
                            break;

                        case "login":
                            Boolean toggle = false;
                            for (int i = 0; i < list.size(); i++) {
                                String data[] = list.get(i).split(",");

                                if(data[0].equals(s[1])&&data[1].equals(s[2])){
                                    System.out.println("Data: "+data[0]+" "+data[1]+" S: "+s[1]+" "+s[2]);
                                    bw.write(CypherHandler.encryptor("yes,"+data[0]+","+data[1]+","+data[2]));
                                    bw.newLine();
                                    bw.flush();
                                    toggle=true;
                                    break;
                                }
                            }
                            if(toggle==false){
                                bw.write(CypherHandler.encryptor("no,0,0,0"));
                                bw.newLine();
                                bw.flush();
                                break;
                            }
                            break;

                        case "exit":
                            System.out.println("A client has closed logged out");
                            break;

//                        case "message":
//                            bw.write(cp.encryptor(s[1]));
//                            bw.newLine();
//                            bw.flush();
//                            break;
                        }

                    queryChecker = query;
                }
                else {
                    bw.write(CypherHandler.encryptor("no,0,0,0"));
                    bw.newLine();
                    bw.flush();
                }

            }
        } catch (Exception e) {
                closeConnection(socket,br,bw);
        }
    }
    public void closeConnection(Socket socket, BufferedReader br, BufferedWriter bw){
        try {
            if(socket!=null)bw.close();
            if(socket!=null)socket.close();
            if(socket!=null)bw.close();
            System.out.println("A client has closed connection");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
