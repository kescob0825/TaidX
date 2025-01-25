package memoranda.api;

import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.json.JSONObject;

public class TaigaClient {
    private static final String BASE_URL = "https://api.taiga.io/api/v1/";
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private String authToken;

    public TaigaClient() {
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public void authenticate(String username, String password) throws IOException {
        MediaType mediaType = MediaType.get("application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "normal");
        jsonObject.put("username", username);
        jsonObject.put("password", password);

        //Convert to string
        String jsonBody = jsonObject.toString();
        RequestBody body = RequestBody.create(jsonBody, mediaType);
        Request request = new Request.Builder()
                .url(BASE_URL + "auth")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful() && response.body() != null) {
            String responseBody = response.body().string();
            authToken = objectMapper.readTree(responseBody).get("auth_token").asText();
        } else {
            throw new IOException("Authentication failed");
        }
    }
    public String getAuthToken() throws IOException {
        if (authToken == null || authToken.isEmpty()) {
            throw new IllegalStateException("Auth token is not available. Please authenticate first.");
        }
        return authToken;
    }
}
