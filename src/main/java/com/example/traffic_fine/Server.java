package com.example.traffic_fine;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    ArrayList<ClientHandler> clients = new ArrayList<>();

    ServerSocket serverSocket = null;
    Socket socket = null;


    Server(){
        while(true) {
            try {
                serverSocket = new ServerSocket(1234);
                socket = serverSocket.accept();

                clients.add(new ClientHandler(socket));

                System.out.println("A client has connected");
                Thread thread = new Thread(new ClientHandler(socket));
                thread.run();

            } catch (IOException e) {
                throw new RuntimeException(e);

            }
        }
    }

    public static void main(String[] args) {
        new Server();
    }

}

class ClientHandler implements Runnable{
    InputStreamReader in = null;
    OutputStreamWriter out = null;
    BufferedWriter bw = null;
    BufferedReader br = null;
    String query;
    String sentData;
    Socket socket;
    ClientHandler(Socket socket){
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
        while (true){
            try {
                //complete the code here
                query = br.readLine();
                sentData=query;
                bw.write("From server: "+sentData);
                bw.newLine();
                bw.flush();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
