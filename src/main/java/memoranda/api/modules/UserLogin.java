package memoranda.api.modules;

import memoranda.api.TaigaClient;

import java.io.IOException;

public class UserLogin {
    public void userLogin() {
        try {
            TaigaClient client = new TaigaClient();
            client.authenticate("your_username", "your_password"); // Replace with actual credentials
            System.out.println("Auth Token: " + client.getAuthToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
