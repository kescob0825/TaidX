package memoranda.api.modules;


import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.io.IOException;


public class TaigaCreateProject {
    private static final String PROJECTS_URL = "https://api.taiga.io/api/v1/projects";
    private final OkHttpClient httpClient;
    private int lastResponseCode;

    public TaigaCreateProject(OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
    }

    //post
    public void createProject(String token, JSONObject newProjectBody) throws IOException {
        MediaType mediaType = MediaType.get("application/json");
        String id = "Bearer " + token;
        String jsonBody = newProjectBody.toString();
        RequestBody body = RequestBody.create(jsonBody, mediaType);

        Request request = new Request.Builder()
                .url(PROJECTS_URL)
                .post(body)
                .addHeader("Authorization", id)
                .addHeader("Content-Type", "application/json")
                .build();
        try (okhttp3.Response response = httpClient.newCall(request).execute()) {
            lastResponseCode = response.code();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                String project_name = jsonResponse.getString("name");
            }
            else {
                System.out.println("Create Project Failed. Response code: " + lastResponseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getLastResponseCode() {
        return lastResponseCode;
    }
}
