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

    public int getLastResponseCode() {
        return lastResponseCode;
    }
}
