package memoranda.api.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.api.models.UserStatsData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONObject;

public class TaigaUserStats {
    private static final String PROJECTS_URL = "https://api.taiga.io/api/v1/users/";
    private final OkHttpClient httpClient;
    private UserStatsData userStatsData;
    private int lastResponseCode;

    public TaigaUserStats(OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
    }

    public void getUserStats(String token, int uid) {
        String id = "Bearer " + token;
        String uidStr = Integer.toString(uid);
        Request request = new Request.Builder()
                .url(PROJECTS_URL+uidStr+"/stats")
                .get()
                .addHeader("Authorization", id)
                .addHeader("Content-Type", "application/json")
                .build();

        try (okhttp3.Response response = httpClient.newCall(request).execute()) {
            lastResponseCode = response.code();
            System.out.println("Response code: " + lastResponseCode);
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                userStatsData = new UserStatsData(
                        jsonResponse.getJSONArray("roles")
                                .toList().stream().map(Object::toString).toList(),
                        jsonResponse.getInt("total_num_closed_userstories"),
                        jsonResponse.getInt("total_num_contacts"),
                        jsonResponse.getInt("total_num_projects")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public UserStatsData getUserStatsData() { return userStatsData; }

    public int getLastResponseCode() {
        return lastResponseCode;
    }
}
