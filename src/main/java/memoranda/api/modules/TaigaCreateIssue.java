package memoranda.api.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.io.IOException;


public class TaigaCreateIssue {
    private static final String PROJECTS_URL = "https://api.taiga.io/api/v1/issues";
    private final OkHttpClient httpClient;
    private int lastResponseCode;

    public TaigaCreateIssue(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    //post
    public void createIssue(String token, JSONObject newProjectBody) throws IOException {
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
            if (!response.isSuccessful() && response.body() == null) {
                System.out.println("Create Issue Failed. Response code: " + lastResponseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getLastResponseCode() {
        return lastResponseCode;
    }
}
