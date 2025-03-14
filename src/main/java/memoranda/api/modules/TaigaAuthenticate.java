package memoranda.api.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.api.models.AuthAndRefreshToken;
import memoranda.api.models.UserProfile;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;

public class TaigaAuthenticate {
    private static final String AUTH_URL = "https://api.taiga.io/api/v1/auth";
    private static final String AUTH_REFRESH_URL = "https://api.taiga.io/api/v1/auth/refresh";
    private final OkHttpClient httpClient;
    private AuthAndRefreshToken authAndRefreshToken;
    private int lastResponseCode;
    private UserProfile userProfile;

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
                String refreshToken = jsonResponse.getString("refresh");
                authAndRefreshToken = new AuthAndRefreshToken(token, refreshToken);
                if(authAndRefreshToken.getAuthToken() != null) {
                    authAndRefreshToken.setLoggedIn(true);
                }
                //Create a new UserProfile object
                userProfile = new UserProfile(
                        jsonResponse.optString("full_name_display", jsonResponse.getString("username")),
                        jsonResponse.optString("username"),
                        jsonResponse.optString("email"),
                        jsonResponse.optString("lang", "N/A"),
                        jsonResponse.optString("timezone", "N/A"),
                        jsonResponse.optString("bio", "N/A"),
                        jsonResponse.getJSONArray("roles")
                                .toList().stream().map(Object::toString).toList(),
                        jsonResponse.getInt("id")
                );
            }
            else {
                System.out.println("Authentication failed. Response code: " + lastResponseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
    public void refreshAuth() throws IOException {
        MediaType mediaType = MediaType.get("application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("refresh", getRefreshToken());

        String jsonBody = jsonObject.toString();
        RequestBody body = RequestBody.create(jsonBody, mediaType);
        Request request = new Request.Builder()
                .url(AUTH_REFRESH_URL)
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
            else {
                System.out.println("Refresh failed. Response code: " + lastResponseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAuthToken() throws IOException {
        if (getAuthAndRefreshToken() == null || getAuthAndRefreshToken().isAuthEmpty()) {
            throw new IOException("Authentication failed");
        }
        return getAuthAndRefreshToken().getAuthToken();
    }
    public String getRefreshToken() throws IOException {
        if (getAuthAndRefreshToken() == null || getAuthAndRefreshToken().isRefreshEmpty()) {
            throw new IOException("Authentication failed");
        }
        return getAuthAndRefreshToken().getRefreshToken();
    }

    public AuthAndRefreshToken getAuthAndRefreshToken() throws IOException {
        if (authAndRefreshToken == null || authAndRefreshToken.isAuthEmpty()) {
            throw new IOException("Authentication failed");
        }
        return authAndRefreshToken;
    }
    public int getLastResponseCode() {
        return lastResponseCode;
    }
}