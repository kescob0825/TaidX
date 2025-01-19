package taiga.apitest;

import taiga.TaigaClient;

import java.io.IOException;


public class ApiTest {
    public static void main(String[] args) {
        try {
            TaigaClient client = new TaigaClient();
<<<<<<< HEAD
            client.authenticate("your_username", "your_password"); // Replace with actual credentials
=======
            client.authenticate("jdlafond@asu.edu", "qt!fXT!En65RCLY"); // Replace with actual credentials
>>>>>>> 2a4cc15 (jdlafond Taiga-api subproject commit)
            System.out.println("Auth Token: " + client.getAuthToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
