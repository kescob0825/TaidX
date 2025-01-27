package memoranda.api;

import com.google.inject.Inject;
import memoranda.api.modules.TaigaAuthenticate;
import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;


public class TaigaClient {
    private static final String BASE_URL = "https://api.taiga.io/api/v1/";
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final TaigaAuthenticate authenticator;
    private int lastResponseCode;

    @Inject
    public TaigaClient() {
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();

        //initiate modules
        this.authenticator = new TaigaAuthenticate(httpClient, objectMapper);
    }

    public void authenticateClient(String username, String password) throws IOException {
        authenticator.authenticate(username, password);
        lastResponseCode = authenticator.getLastResponseCode();
    }

    public String getAuthToken() throws IOException {
        return authenticator.getAuthToken();
    }

    public int getLastResponseCode() {
        return lastResponseCode;
    }
}
