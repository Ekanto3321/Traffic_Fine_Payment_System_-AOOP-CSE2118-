package Cypher;

import java.util.Random;

public class CypherHandler {
    Random random = new Random();


    public String encryptor(String received){
        char s[] = received.toCharArray();
        for (int i = 0; i < s.length; i++) {
            s[i]+=7;
        }
        String sentData = String.valueOf(s);

        return sentData;
    }
    public String decryptor(String sent){
        char s[] = sent.toCharArray();
        for (int i = 0; i < s.length; i++) {
            s[i]-=7;
        }
        String receivedData = String.valueOf(s);
        return receivedData;
    }

}
