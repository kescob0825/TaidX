package memoranda.api.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.api.models.AuthAndRefreshToken;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.io.IOException;

import static memoranda.api.modules.TaigaAuthenticate.getAuthAndRefreshToken;

/**
 * If response code is 401 unauthorized attempt a refresh
 */
public class TaigaRefreshAuth {
    private static final String AUTH_URL = "http://localhost:8000/api/v1/auth/refresh";
    private final OkHttpClient httpClient;

    private int lastResponseCode;

    public TaigaRefreshAuth(OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
    }

    public void refreshAuth() throws IOException {
        MediaType mediaType = MediaType.get("application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("refresh", getRefreshToken());

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
                String refreshToken = jsonResponse.getString("refresh");
                getAuthAndRefreshToken().setAuthToken(token);
                getAuthAndRefreshToken().setRefreshToken(refreshToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRefreshToken() throws IOException {
        if (getAuthAndRefreshToken() == null || getAuthAndRefreshToken().isRefreshEmpty()) {
            throw new IOException("Authentication failed");
        }
        return getAuthAndRefreshToken().getRefreshToken();
    }

    public int getLastResponseCode() {
        return lastResponseCode;
    }
}
