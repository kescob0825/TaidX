package taiga.apitest;

import taiga.TaigaClient;

import java.io.IOException;


public class ApiTest {
    public static void main(String[] args) {
        try {
            TaigaClient client = new TaigaClient();
            client.authenticate("jdlafond@asu.edu", "qt!fXT!En65RCLY"); // Replace with actual credentials
            System.out.println("Auth Token: " + client.getAuthToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
