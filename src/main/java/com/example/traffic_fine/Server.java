package com.example.traffic_fine;

import Cypher.CypherHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server{
    ServerSocket serverSocket = null;
    Socket socket = null;

    Server(){
        try {
            serverSocket = new ServerSocket(1234);
            System.out.println("server is online");
            ServerLogs.addLogs("server is online");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while(true) {
            try {
                socket = serverSocket.accept();

                System.out.println("A client has connected");
                ServerLogs.addLogs("A client has connected");

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

        String name = null,pw=null;

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

                                    name=data[0];
                                    pw=data[1];

                                    bw.write(CypherHandler.encryptor("yes,"+data[0]+","+data[1]+","+data[2]));
                                    bw.newLine();
                                    bw.flush();

                                    ServerLogs.addLogs("Client "+data[0]+" Has logged in");

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
                            ServerLogs.addLogs("A client has closed logged out");
                            System.out.println("A client has closed logged out");
                            break;

                        case "update":
                            for (int i = 0; i < list.size(); i++) {
                                String na[] = list.get(i).split(",");

                                if(na[0].equals(name)){
                                    list.set(i,name+","+pw+","+s[1]+","+s[2]+","+s[3]+","+s[4]+","+s[5]+","+s[6]+",none");
                                }
                            }

                            BufferedWriter dataWriter = new BufferedWriter(new FileWriter("data.txt"));
                            String file=null;
                            for (int i = 0; i < list.size(); i++) {
                                file+=list.get(i)+"\n";
                            }
                            dataWriter.write(file);
                            dataWriter.close();
                            ServerLogs.addLogs("Client "+name+" has updated their information");
                            break;

                        case "fetch":
                            for (int i = 0; i < list.size(); i++) {
                                String na[] = list.get(i).split(",");
                                if(na[0].equals(name)){
                                    bw.write(CypherHandler.encryptor(list.get(i)));
                                    bw.newLine();
                                    bw.flush();
                                }
                            }
                            break;

                        case "payment":
                            for (int i = 0; i < list.size(); i++) {
                                String na[] = list.get(i).split(",");

                                if(na[0].equals(name)){

                                    na[12] = String.valueOf(Integer.parseInt(na[12])-Integer.parseInt(s[1]));

                                    if(Integer.parseInt(na[12])<=Integer.parseInt(s[1])) na[15]="paid";
                                    else na[15] = "unpaid";

                                    na[9]="You have paid the fine";

                                    list.set(i,na[0]+","+na[1]+","+na[2]+","+na[3]+","+na[4]+","+na[5]+","+na[6]+","+na[7]+","+na[8]+","+na[9]+","+na[10]+","+na[11]+","+na[12]+","+na[13]+","+na[14]+","+na[15]);
                                    bw.write(CypherHandler.encryptor(list.get(i)));
                                    bw.newLine();
                                    bw.flush();
                                }
                            }

                            dataWriter = new BufferedWriter(new FileWriter("data.txt"));
                            file=null;
                            for (int i = 0; i < list.size(); i++) {
                                file+=list.get(i)+"\n";
                            }
                            dataWriter.write(file);
                            dataWriter.close();
                            ServerLogs.addLogs("Client "+name+" has paid their fine");
                            break;

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
                e.printStackTrace();
                closeConnection(socket,br,bw);
        }
    }
    public void closeConnection(Socket socket, BufferedReader br, BufferedWriter bw){
        try {
            if(socket!=null)bw.close();
            if(socket!=null)socket.close();
            if(socket!=null)bw.close();
            System.out.println("A client has closed connection");
            ServerLogs.addLogs("A client has closed connection");
            ServerLogs.clearLogs();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
