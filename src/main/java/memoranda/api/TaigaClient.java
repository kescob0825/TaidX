package memoranda.api;

import com.google.inject.Inject;
import memoranda.api.modules.TaigaAuthenticate;
import memoranda.api.modules.TaigaProject;
import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;


public class TaigaClient {
    private static final String BASE_URL = "https://api.taiga.io/api/v1/";
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final TaigaAuthenticate authenticator;
    private final TaigaProject projects;

    protected int lastResponseCode;

    @Inject
    public TaigaClient() {
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();

        //initiate modules
        this.authenticator = new TaigaAuthenticate(httpClient, objectMapper);
        this.projects = new TaigaProject(httpClient, objectMapper);
    }

    public void authenticateClient(String username, String password) throws IOException {
        authenticator.authenticate(username, password);
        setLastResponseCode(authenticator.getLastResponseCode());
    }

    public void getProjects(int uid) {
        projects.getProjects(this.authenticator.getUserProfile().getUid());
    }

    /**
     * Refreshes the authentication token and refresh token.
     * @throws IOException if an I/O error occurs during the refresh process
     */
    public void refreshAuthClient() throws IOException {
        authenticator.refreshAuth();
        setLastResponseCode(authenticator.getLastResponseCode());
    }

    /**
     * Returns the authentication token.
     * @return the authentication token
     * @throws IOException if an I/O error occurs during the token retrieval process
     */
    public String getAuthToken() throws IOException {
        return authenticator.getAuthAndRefreshToken().getAuthToken();
    }

    /**
     * Returns the refresh token.
     * @return the refresh token
     * @throws IOException if an I/O error occurs during the token retrieval process
     */
    public String getRefreshToken() throws IOException {
        return authenticator.getAuthAndRefreshToken().getRefreshToken();
    }

    /**
     * Returns the last response code.
     * @return the last response code
     */
    public void setLastResponseCode(int lastResponseCode) {
        this.lastResponseCode = lastResponseCode;
    }

    /**
     * Returns the last response code.
     * @return the last response code
     */
    public int getLastResponseCode() {
        return lastResponseCode;
    }

}
