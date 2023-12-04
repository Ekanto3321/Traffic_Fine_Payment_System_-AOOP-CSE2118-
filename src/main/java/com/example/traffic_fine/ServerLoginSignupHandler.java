package com.example.traffic_fine;

import Cypher.CypherHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ServerLoginSignupHandler{

    public static void signUp(String s){
        try {
//            CypherHandler cp = new CypherHandler();
            BufferedWriter bw = new BufferedWriter(new FileWriter("data.txt",true));
            bw.append(s+"\n");
            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
