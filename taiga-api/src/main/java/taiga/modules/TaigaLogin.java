package taiga.modules;

import taiga.TaigaClient;

import java.io.IOException;

public class TaigaLogin {
    public void login(String usr_nm, String password) {
        TaigaClient client = new TaigaClient();
        try {
            client.authenticate(usr_nm, password);
            System.out.println("Auth Token: " + client.getAuthToken());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("Last Response Code: " + client.getLastResponseCode());
        }
    }
}
