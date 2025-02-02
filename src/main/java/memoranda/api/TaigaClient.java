package memoranda.api;

import com.google.inject.Inject;
import memoranda.api.models.ProjectData;
import memoranda.api.models.UserProfile;
import memoranda.api.models.UserStoryNode;
import memoranda.api.modules.TaigaAuthenticate;
import memoranda.api.modules.TaigaProject;
import memoranda.api.modules.TaigaUserStory;
import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;


public class TaigaClient {
    private static final String BASE_URL = "https://api.taiga.io/api/v1/";
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final TaigaAuthenticate authenticator;
    private final TaigaProject projects;
    private final TaigaUserStory userStory;

    protected int lastResponseCode;

    @Inject
    public TaigaClient() {
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();

        //initiate modules
        this.authenticator = new TaigaAuthenticate(httpClient, objectMapper);
        this.projects = new TaigaProject(httpClient, objectMapper);
        this.userStory = new TaigaUserStory(httpClient, objectMapper);
    }

    /**
     * Authenticates the client with the provided username and password.
     * @param username the username
     * @param password the password
     * @throws IOException if an I/O error occurs during the authentication process
     */
    public void authenticateClient(String username, String password) throws IOException {
        authenticator.authenticate(username, password);
        setLastResponseCode(authenticator.getLastResponseCode());
        initLoadProjectAndUserStoryData();
    }

    public void initLoadProjectAndUserStoryData() throws IOException {
        projects.getProjects(this.authenticator.getAuthToken(), this.authenticator.getUserProfile().getUid());
        userStory.getUserStories(this.authenticator.getAuthToken(), this.projects.getProjectData().get(0).getProjectID());
    }

    public boolean isClientLoggedIn() throws IOException {
        return authenticator.getAuthAndRefreshToken().isLoggedIn();
    }
    /**
     * Retrieves the projects for the authenticated user.
     */
    public List<ProjectData> getProjectsList() throws IOException {
        return projects.getProjectData();
    }

    /**
     * Retrieves the user stories for the authenticated user.
     * @throws IOException if an I/O error occurs during the user story retrieval process
     */
    public List<UserStoryNode> getUserStories() throws IOException {
        return userStory.getUserStoryNodes();
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

    public void logoutTaiga()  {
        try{
            authenticator.getAuthAndRefreshToken().logout();
        }catch(Exception e){
            e.printStackTrace();
        }

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

    public UserProfile getUserProfile() throws IOException {
        return authenticator.getUserProfile();
    }

}
