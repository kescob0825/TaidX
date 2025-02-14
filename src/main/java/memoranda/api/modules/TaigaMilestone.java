package memoranda.api.modules;

import memoranda.api.models.MilestoneData;

import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.api.models.UserStoryNode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TaigaMilestone {
    private static final String USER_STORY_URL = "https://api.taiga.io/api/v1/milestones?project=";
    private final OkHttpClient httpClient;
    private MilestoneData sprintData;
    private List<MilestoneData> sprintDataList;
    private List<UserStoryNode> userStoryNodes;
    private int lastResponseCode;

    public TaigaMilestone(OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.sprintDataList = new ArrayList<>();
        this.userStoryNodes = new ArrayList<>();
    }

    public void getMilestones(String token, int uid) throws IOException {
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
                if (responseBody.isEmpty()) {
                    sprintDataList = new ArrayList<>();
                    return;
                }
                Object json = new JSONTokener(responseBody).nextValue();
                JSONArray jsonArray;
                if (json instanceof JSONObject) {
                    jsonArray = new JSONArray();
                    jsonArray.put(json);
                } else if (json instanceof JSONArray) {
                    jsonArray = (JSONArray) json;
                } else {
                    throw new IllegalStateException("Unexpected response type: " + json.getClass());
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonResponse = jsonArray.getJSONObject(i);
                    JSONArray userStoriesArray = jsonResponse.getJSONArray("user_stories");
                    int userStoriesCount = userStoriesArray.length();
                    int total_points = jsonResponse.optInt("total_points", 0);
                    int closed_points = jsonResponse.optInt("closed_points", 0);
                    int points_left = total_points - closed_points;
                    sprintData = new MilestoneData(
                            jsonResponse.optInt("id"),
                            jsonResponse.optString("name"),
                            jsonResponse.optInt("project"),
                            userStoriesCount,
                            jsonResponse.optString("estimated_start"),
                            jsonResponse.optString("estimated_finish"),
                            jsonResponse.optInt("total_points", 0),
                            points_left,
                            jsonResponse.optInt("closed_points", 0),
                            jsonResponse.optBoolean("closed"),
                            null
                    );
                    sprintDataList.add(sprintData);
                }
            }
            else {
                System.out.println("Get Milestones Failed. Response code: " + lastResponseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<MilestoneData> getSprintDataList() {
        return sprintDataList;
    }
    public int getLastResponseCode() {
        return lastResponseCode;
    }
    public void clearUserStoryNodes() {
        sprintDataList.clear();
    }
}

