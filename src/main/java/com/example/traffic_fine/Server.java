package com.example.traffic_fine;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket serverSocket = null;
    Socket socket = null;


    Server(){

        try {
            while(true) {
                serverSocket = new ServerSocket(1234);
                socket = serverSocket.accept();
                new Thread(new ClientHandler(socket)).start();
                System.out.println("A client has connected");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        while (true){
            try {
                in = new InputStreamReader(socket.getInputStream());
                out = new OutputStreamWriter(socket.getOutputStream());
                br = new BufferedReader(in);
                bw = new BufferedWriter(out);


//                //complete the code here
//                query = br.readLine();
//                sentData=query;
//                bw.write("From server: "+sentData);
//                bw.newLine();
//                bw.flush();


                if(br.readLine()=="exit"){
                    break;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            finally{
                try {
                    in.close();
                    out.close();
                    bw.close();
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
