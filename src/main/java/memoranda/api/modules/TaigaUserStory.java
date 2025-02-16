package memoranda.api.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.api.models.UserStoryNode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

public class TaigaUserStory {
    private static final String USER_STORY_URL = "https://api.taiga.io/api/v1/userstories?project=";

    private final OkHttpClient httpClient;
    private UserStoryNode userStoryNode;
    private List<UserStoryNode> userStoryNodes = new ArrayList<>();
    private int lastResponseCode;

    public TaigaUserStory(OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
    }

    public void getUserStories(String token, int uid) throws IOException {
        String id = "Bearer " + token;
        String uidStr = Integer.toString(uid);
        Request request = new Request.Builder()
                .url(USER_STORY_URL+uidStr)
                .get()
                .addHeader("Authorization", id)
                .addHeader("Content-Type", "application/json")
                .build();

        try (okhttp3.Response response = httpClient.newCall(request).execute()) {
            lastResponseCode = response.code();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONArray jsonArray = new JSONArray(responseBody);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonResponse = jsonArray.getJSONObject(i);
                    JSONObject ownerObj = jsonResponse.getJSONObject("owner_extra_info");
                    JSONObject statusObj = jsonResponse.getJSONObject("status_extra_info");
                    JSONObject projectInfoObj = jsonResponse.getJSONObject("project_extra_info");

                    //Create a new UserProfile object
                    userStoryNode = new UserStoryNode(
                            jsonResponse.getInt("id"),
                            jsonResponse.getInt("ref"),
                            jsonResponse.optString("subject"),
                            jsonResponse.getInt("total_points"),
                            jsonResponse.optString("created_date"),
                            jsonResponse.optString("modified_date"),
                            jsonResponse.optString("finish_date", "null"),
                            statusObj.getBoolean("is_closed"),
                            ownerObj.getInt("id"),
                            ownerObj.getString("username"),
                            statusObj.getString("name"),
                            jsonResponse.getInt("project"),
                            projectInfoObj.optString("name"),
                            jsonResponse.optString("milestone_name")
                    );
                    userStoryNodes.add(userStoryNode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<UserStoryNode> getUserStoryNodes() {
        return userStoryNodes;
    }
    public int getLastResponseCode() {
        return lastResponseCode;
    }
}
