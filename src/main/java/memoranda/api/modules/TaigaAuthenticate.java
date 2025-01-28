package memoranda.api.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.api.TaigaClient;
import memoranda.api.models.AuthToken;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.io.IOException;

public class TaigaAuthenticate {
    private static final String AUTH_URL = "https://api.taiga.io/api/v1/auth";
    private final OkHttpClient httpClient;

    private AuthToken authToken;
    private int lastResponseCode;

    // User fields if authentication is successful
    private String username;
    private String email;
    private String name;
    private int totalProjects;

    public TaigaAuthenticate(OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;

    }

    public void authenticate(String username, String password) throws IOException {
        MediaType mediaType = MediaType.get("application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "normal");
        jsonObject.put("username", username);
        jsonObject.put("password", password);

        String jsonBody = jsonObject.toString();
        RequestBody body = RequestBody.create(jsonBody, mediaType);
        Request request = new Request.Builder()
                .url(AUTH_URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (okhttp3.Response response = httpClient.newCall(request).execute()) {
            lastResponseCode = response.code();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                String token = jsonResponse.getString("auth_token");
                authToken = new AuthToken(token);
                this.username = jsonResponse.getString("username");
                this.email = jsonResponse.getString("email");
                this.name = jsonResponse.getString("full_name");
                // add private and public projects
                String public_projects = jsonResponse.getString("total_public_projects");
                String private_projects = jsonResponse.getString("total_private_projects");
                this.totalProjects = (Integer.parseInt(public_projects) + Integer.parseInt(private_projects));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAuthToken() throws IOException {
        if (authToken == null || authToken.isEmpty()) {
            throw new IOException("Authentication failed");
        }
        return authToken.getToken();
    }
    public String getUsername() throws IOException {
        if (authToken == null || authToken.isEmpty()) {
            throw new IOException("Authentication failed");
        }
        return this.username;
    }
    public String getEmail() throws IOException {
        if (authToken == null || authToken.isEmpty()) {
            throw new IOException("Authentication failed");
        }
        return this.email;
    }
    public String getName() throws IOException {
        if (authToken == null || authToken.isEmpty()) {
            throw new IOException("Authentication failed");
        }
        return this.name;
    }
    public int getTotalProjects() throws IOException {
        if (authToken == null || authToken.isEmpty()) {
            throw new IOException("Authentication failed");
        }
        return this.totalProjects;
    }

    public int getLastResponseCode() {
        return lastResponseCode;
    }
}
