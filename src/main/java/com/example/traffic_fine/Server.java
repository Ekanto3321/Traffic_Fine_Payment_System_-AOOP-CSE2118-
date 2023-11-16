package com.example.traffic_fine;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    ArrayList<ClientHandlerAPI> clients = new ArrayList<>();

    ServerSocket serverSocket = null;
    Socket socket = null;


    Server(){
        try {
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while(true) {
            try {
                socket = serverSocket.accept();

                clients.add(new ClientHandlerAPI(socket));

                System.out.println("A client has connected");
                ClientHandlerAPI clientHandlerAPI = new ClientHandlerAPI(socket);
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
        try {
            in = new InputStreamReader(socket.getInputStream());
            out = new OutputStreamWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        br = new BufferedReader(in);
        bw = new BufferedWriter(out);

        try {
            while (true) {
                //complete the code here
                    query = br.readLine();
                if(query.equals("exit")){
                    System.out.println("client has closed connection");
                    break;
                }
                    sentData = query;
                    bw.write("From server: " + sentData);
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
